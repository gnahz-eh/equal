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

import com.github.equal.enums.FileType;
import com.github.equal.exception.EqualException;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ExceptionUtils {

    // Selector
    public static final int SOURCE_FILE_IS_NULL = 100;
    public static final int ROW_START_INDEX_IS_LESS_THAN_2 = 101;
    public static final int NUMBER_OF_ROW_IS_LESS_THAN_0 = 102;
    public static final int ROW_END_INDEX_IS_LESS_THAN_ROW_START_INDEX = 103;
    public static final int TABLE_INDEX_IS_LESS_THAN_0 = 104;
    public static final int TABLE_NAME_IS_NULL = 105;
    public static final int CLAZZ_IS_NULL = 106;
    public static final int ROW_START_INDEX_IS_LESS_THAN_1 = 107;
    public static final int INSERT_DATA_IS_NULL = 108;
    public static final int UNSUPPORTED_FILE_TYPE = 109;
    public static final int FILE_NOT_FOUND = 110;
    public static final int ADAPT_VALUE_ERROR = 111;
    public static final int ADAPT_DATE_ERROR = 112;
    public static final int ADAPT_TIME_ERROR = 113;
    public static final int ADAPT_DATE_TIME_ERROR = 114;
    public static final int FILE_OPEN_ERROR = 115;

    // Inserter
    public static final int SOURCE_FILE_DOES_NOT_EXIST = 200;
    public static final int SOURCE_FILE_IS_NOT_A_FILE = 201;
    public static final int ROWS_OVER_FLOW = 202;
    public static final int INVALID_TABLE_INDEX = 203;
    public static final int INSERT_DATA_ERROR = 204;
    public static final int FILE_TYPES_ARE_INCONSISTENT = 205;

    public static final Map<Integer, String> EXCEPTION_MAP = new HashMap<>();

    static {
        // Selector
        EXCEPTION_MAP.put(SOURCE_FILE_IS_NULL, "Source file is null");
        EXCEPTION_MAP.put(ROW_START_INDEX_IS_LESS_THAN_2, "Row start row index is less than 2");
        EXCEPTION_MAP.put(NUMBER_OF_ROW_IS_LESS_THAN_0, "Number of row is less than 0");
        EXCEPTION_MAP.put(ROW_END_INDEX_IS_LESS_THAN_ROW_START_INDEX, "Row end index is less than row start index");
        EXCEPTION_MAP.put(TABLE_INDEX_IS_LESS_THAN_0, "Table index is less than 0");
        EXCEPTION_MAP.put(TABLE_NAME_IS_NULL, "Table name is null");
        EXCEPTION_MAP.put(CLAZZ_IS_NULL, "Clazz is null");
        EXCEPTION_MAP.put(ROW_START_INDEX_IS_LESS_THAN_1, "Row start row index is less than 1");
        EXCEPTION_MAP.put(INSERT_DATA_IS_NULL, "Insert data is null");
        EXCEPTION_MAP.put(UNSUPPORTED_FILE_TYPE, "Unsupported file type");
        EXCEPTION_MAP.put(FILE_NOT_FOUND, "File not found");
        EXCEPTION_MAP.put(ADAPT_VALUE_ERROR, "Adapt value error");
        EXCEPTION_MAP.put(ADAPT_DATE_ERROR, "Adapt date error");
        EXCEPTION_MAP.put(ADAPT_TIME_ERROR, "Adapt time error");
        EXCEPTION_MAP.put(ADAPT_DATE_TIME_ERROR, "Adapt date time error");
        EXCEPTION_MAP.put(FILE_OPEN_ERROR, "File open error");

        // Inserter
        EXCEPTION_MAP.put(SOURCE_FILE_DOES_NOT_EXIST, "Source file does not exist");
        EXCEPTION_MAP.put(SOURCE_FILE_IS_NOT_A_FILE, "Source file is not a file");
        EXCEPTION_MAP.put(ROWS_OVER_FLOW, "Rows over flow");
        EXCEPTION_MAP.put(INVALID_TABLE_INDEX, "Invalid table index");
        EXCEPTION_MAP.put(INSERT_DATA_ERROR, "Insert data error");
        EXCEPTION_MAP.put(FILE_TYPES_ARE_INCONSISTENT, "File types are inconsistent");
    }

    public static void assertValidSourceFile(File sourceFile) {
        assertNotNullSourceFile(sourceFile);
        assertSourceFileExists(sourceFile);
        assertSourceFileIsNotDir(sourceFile);
    }

    public static void assertNotNullSourceFile(File sourceFile) {
        if (sourceFile == null) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.SOURCE_FILE_IS_NULL));
        }
    }

    public static void assertSourceFileIsNotDir(File sourceFile) {
        if (!sourceFile.isFile()) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.SOURCE_FILE_IS_NOT_A_FILE));
        }
    }

    public static void assertSourceFileExists(File sourceFile) {
        if (!sourceFile.exists()) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.SOURCE_FILE_DOES_NOT_EXIST));
        }
    }

    public static void assertTableIndexBigThanZero(int tableIndex) {
        if (tableIndex < 0) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.TABLE_INDEX_IS_LESS_THAN_0));
        }
    }

    public static void assertRowStartIndexBigThan2(int rowStartIndex) {
        if (rowStartIndex < ConstantUtils.ROW_START_INDEX) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.ROW_START_INDEX_IS_LESS_THAN_2));
        }
    }

    public static void assertNotNullTableName(String tableName) {
        if (StringUtils.isEmpty(tableName)) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.TABLE_NAME_IS_NULL));
        }
    }

    public static void assertNumberOfRowsIsLessThan(FileType fileType, int numberOfRows) {
        switch (fileType) {
            case XLS:
                if (numberOfRows > ConstantUtils.MAX_ROWS_IN_XLS) {
                    throw new EqualException(ExceptionUtils.ROWS_OVER_FLOW, StringUtils.XLS);
                }
                break;
            case XLSX:
                if (numberOfRows > ConstantUtils.MAX_ROWS_IN_XLSX) {
                    throw new EqualException(ExceptionUtils.ROWS_OVER_FLOW, StringUtils.XLSX);
                }
                break;
            default:
                break;
        }
    }

    public static void assertInsertDataIsNotNull(Collection<?> data) {
        if (data == null) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.INSERT_DATA_IS_NULL));
        }
    }

    public static void assertValidRowStartIndex(int rowStartIndex) {
        if (rowStartIndex < 1) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.ROW_START_INDEX_IS_LESS_THAN_1));
        }
    }

    public static void assertNumberOfRowsIsNotLessThan0(int numberOfRows) {
        if (numberOfRows < 0) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.NUMBER_OF_ROW_IS_LESS_THAN_0));
        }
    }

    public static void assertClazzIsNotNull(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.CLAZZ_IS_NULL));
        }
    }

    public static void assertSourceIsNotNull(File sourceFile, InputStream inputStream) {
        if (sourceFile == null && inputStream == null) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.SOURCE_FILE_IS_NULL));
        }
    }

    public static void assertIsTargetFileType(FileType actual, FileType expect) {
        if (actual != expect) {
            throw new EqualException(ExceptionUtils.FILE_TYPES_ARE_INCONSISTENT);
        }
    }
}
