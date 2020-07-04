/*
 * MIT License
 *
 * Copyright (c) [2019] [He Zhang]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished
 *  to do so, subject to the following conditions:
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

package com.github.equal.utils;

import com.github.equal.enums.ExceptionType;
import com.github.equal.enums.FileType;
import com.github.equal.exception.EqualException;
import com.github.equal.exception.SelectorException;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

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
            return StringUtils.BLINK_STRING;
        }
        return fileName.substring(lastIndexOf + 1);
    }

    /**
     * Reference from WorkbookFactory.create(InputStream inp, String password)
     */
    public static FileType getFileTypeByInputStream(InputStream inputStream) {
        if (inputStream == null) {
            return FileType.NONE;
        }
        InputStream is = FileMagic.prepareToCheckMagic(inputStream);
        FileMagic fm;
        try {
            fm = FileMagic.valueOf(is);
        } catch (IOException e) {
            throw new SelectorException(e);
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

    public static Workbook getWorkbook(File file) throws SelectorException {
        try {
            return WorkbookFactory.create(file);
        } catch (IOException e) {
            throw new SelectorException(e);
        }
    }

    public static Workbook getWorkbook(InputStream inputStream) throws SelectorException {
        try {
            return WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            throw new SelectorException(e);
        }
    }

    public static Sheet getTable(Workbook workbook, int tableIndex, String tableName) {

        if (tableIndex != ConstantUtils.DEFAULT_TABLE_INDEX) {
            try {
                return workbook.getSheetAt(tableIndex);
            } catch (Exception e) {
                throw new EqualException(ExceptionType.INVALID_TABLE_INDEX, String.valueOf(tableIndex));
            }
        } else {
            return workbook.getSheet(tableName);
        }
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n;
        while (-1 != (n = inputStream.read(buffer))) {
            outputStream.write(buffer, 0, n);
        }
        outputStream.flush();
        closeIO(outputStream);
        return outputStream.toByteArray();
    }

    public static boolean isExcelFile(FileType fileType) {
        return fileType == FileType.XLS || fileType == FileType.XLSX;
    }

    public static void closeIO(Closeable io) {
        try {
            io.close();
        } catch (IOException e) {
            throw new EqualException(e);
        }
    }

    public static void flushData(Workbook workbook, OutputStream outputStream) throws EqualException {
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new EqualException(ExceptionType.FLUSH_DATA_ERROR);
        } finally {
            closeIO(workbook);
            closeIO(outputStream);
        }
    }

    public static List<String> readCSVData(InputStream inputStream, Charset charset) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset));
        List<String> csvRowData = new ArrayList<>();
        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                csvRowData.add(line + StringUtils.NEW_LINE);
            }
        } catch (IOException e) {
            throw new EqualException(e);
        } finally {
            FileUtils.closeIO(bufferedReader);
            FileUtils.closeIO(inputStream);
        }
        return csvRowData;
    }
}
