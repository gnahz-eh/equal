/*
 * MIT License
 *
 * Copyright (c) [2019] [He Zhang]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.equal.processor.selector;

import com.github.equal.enums.ExceptionType;
import com.github.equal.enums.FileType;
import com.github.equal.exception.SelectorException;
import com.github.equal.utils.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Stream;

public class SelectorContext {

    private SelectorContext() { }

    private static FileSelector fileSelector;

    public static <T> Stream<T> selectFromFile(Selector<T> selector) throws SelectorException {
        FileType fileType = FileUtils.getFileTypeBySourceFile(selector.getSourceFile());
        switch (fileType) {
            case XLSX:
            case XLS:
                fileSelector = new XLSSelector(FileUtils.getWorkbook(selector.getSourceFile()));
                break;
            case CSV:
                try {
                    fileSelector = new CSVSelector(new FileInputStream(selector.getSourceFile()));
                } catch (FileNotFoundException e) {
                    throw new SelectorException(ExceptionType.FILE_NOT_FOUND, selector.getSourceFile().getName(), e);
                }
                break;
            default:
                throw new SelectorException(ExceptionType.UNSUPPORTED_FILE_TYPE);
        }
        return fileSelector.selectFromFile(selector);
    }

    public static <T> Stream<T> selectFromStream(Selector<T> selector) throws SelectorException {
        byte[] bytes;
        try {
            bytes = FileUtils.toByteArray(selector.getInputStream());
        } catch (IOException e) {
            throw new SelectorException(e);
        }
        FileType fileType = FileUtils.getFileTypeByInputStream(selector.getInputStream());
        switch (fileType) {
            case XLSX:
            case XLS:
                fileSelector = new XLSSelector(FileUtils.getWorkbook(new ByteArrayInputStream(bytes)));
                break;
            case CSV:
                fileSelector = new CSVSelector(new ByteArrayInputStream(bytes));
                break;
            default:
                throw new SelectorException(ExceptionType.UNSUPPORTED_FILE_TYPE);
        }
        return fileSelector.selectFromFile(selector);
    }
}
