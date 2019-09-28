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
import com.github.zhanghe.jxcel.exception.ExceptionUtils;
import com.github.zhanghe.jxcel.exception.JxcelException;
import com.github.zhanghe.jxcel.utils.StringUtils;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.util.stream.Stream;

public class SelectorContext {

    private static FileSelector fileSelector;

    public static <T> Stream<T> selectFromFile(Selector selector) throws JxcelException {
        FileType fileType = getFileTypeBySourceFile(selector.getSourceFile());
        switch (fileType) {
            case XLS:
                fileSelector = new FileSelectorXLS(getWorkbook(selector.getSourceFile()));
                break;
            case XLSX:
                fileSelector = new FileSelectorXLSX(getWorkbook(selector.getSourceFile()));
                break;
            case CSV:
                try {
                    fileSelector = new FileSelectorCSV(new FileInputStream(selector.getSourceFile()));
                } catch (FileNotFoundException e) {
                    throw new JxcelException(ExceptionUtils.FILE_NOT_FOUND, selector.getSourceFile().getName(), e);
                }
                break;
            default:
                throw new JxcelException(ExceptionUtils.UNSUPPORTED_FILE_TYPE);
        }
        return fileSelector.selectFromFile(selector);
    }

    public static <T> Stream<T> selectFromStream(Selector selector) throws JxcelException {
        byte[] bytes = null;
        try {
            bytes = toByteArray(selector.getInputStream());
        } catch (IOException e) {
            throw new JxcelException(e);
        }
        FileType fileType = getFileTypeByInputStream(selector.getInputStream());
        switch (fileType) {
            case XLS:
                fileSelector = new FileSelectorXLS(getWorkbook(new ByteArrayInputStream(bytes)));
                break;
            case XLSX:
                fileSelector = new FileSelectorXLSX(getWorkbook(new ByteArrayInputStream(bytes)));
                break;
            case CSV:
                fileSelector = new FileSelectorCSV(new ByteArrayInputStream(bytes));
                break;
            default:
                throw new JxcelException(ExceptionUtils.UNSUPPORTED_FILE_TYPE);
        }
        return fileSelector.selectFromFile(selector);
    }

    public static FileType getFileTypeBySourceFile(File sourceFile) {
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

    /**
     * Reference from WorkbookFactory.create(InputStream inp, String password)
     * @param inputStream
     * @return
     */
    public static FileType getFileTypeByInputStream(InputStream inputStream) {
        if (inputStream == null) {
            return FileType.NONE;
        }
        InputStream is = FileMagic.prepareToCheckMagic(inputStream);
        FileMagic fm = null;
        try {
            fm = FileMagic.valueOf(is);
        } catch (IOException e) {
            throw new JxcelException(e);
        }
        switch (fm) {
            case OLE2:
                return FileType.XLS;
            case OOXML:
                return FileType.XLSX;
            default:
                return FileType.CSV;
        }
    }

    public static Workbook getWorkbook(File file) throws JxcelException {
        try {
            return WorkbookFactory.create(file);
        } catch (IOException e) {
            throw new JxcelException(e);
        }
    }

    public static Workbook getWorkbook(InputStream inputStream) throws JxcelException {
        try {
            return WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            throw new JxcelException(e);
        }
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while (-1 != (n = inputStream.read(buffer))) {
            outputStream.write(buffer, 0, n);
        }
        outputStream.flush();
        return outputStream.toByteArray();
    }
}
