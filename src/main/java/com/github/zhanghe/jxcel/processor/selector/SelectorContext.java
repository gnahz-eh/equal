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

package com.github.zhanghe.jxcel.processor.selector;

import com.github.zhanghe.jxcel.enums.FileType;
import com.github.zhanghe.jxcel.utils.StringUtils;

import java.io.File;
import java.util.stream.Stream;

public class SelectorContext {

    public static <T> Stream<T> selectFromFile(Selector selector) {
        FileType fileType = getSourceFileType(selector.getSourceFile());
        // todo
        return null;
    }

    private static FileType getSourceFileType(File sourceFile) {
        if (sourceFile == null || !sourceFile.exists()) {
            return FileType.NONE;
        }
        String fileNameExt = getFileExtension(sourceFile.getName());
        if (StringUtils.isEmpty(fileNameExt)) {
            return FileType.OTHERS;
        }
        FileType fileType = FileType.fileTypeMap.get(fileNameExt.toUpperCase());
        if (fileType == null) {
            return FileType.OTHERS;
        }
        return fileType;
    }

    public static String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return fileName.substring(lastIndexOf + 1);
    }
}
