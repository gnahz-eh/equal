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
import com.github.equal.exception.InserterException;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

public class ExceptionUtils {

    private ExceptionUtils() { }

    public static void assertValidSourceFile(File sourceFile) {
        assertNotNullSourceFile(sourceFile);
        assertSourceFileExists(sourceFile);
        assertSourceFileIsNotDir(sourceFile);
    }

    public static void assertNotNullSourceFile(File sourceFile) {
        if (sourceFile == null) {
            throw new IllegalArgumentException(ExceptionType.SOURCE_FILE_IS_NULL.getExceptionMessage());
        }
    }

    public static void assertSourceFileIsNotDir(File sourceFile) {
        if (!sourceFile.isFile()) {
            throw new IllegalArgumentException(ExceptionType.SOURCE_FILE_IS_NOT_A_FILE.getExceptionMessage());
        }
    }

    public static void assertSourceFileExists(File sourceFile) {
        if (!sourceFile.exists()) {
            throw new IllegalArgumentException(ExceptionType.SOURCE_FILE_DOES_NOT_EXIST.getExceptionMessage());
        }
    }

    public static void assertTableIndexBigThanZero(int tableIndex) {
        if (tableIndex < 0) {
            throw new IllegalArgumentException(ExceptionType.TABLE_INDEX_IS_LESS_THAN_0.getExceptionMessage());
        }
    }

    public static void assertRowStartIndexBigThan2(int rowStartIndex) {
        if (rowStartIndex < ConstantUtils.ROW_START_INDEX) {
            throw new IllegalArgumentException(ExceptionType.ROW_START_INDEX_IS_LESS_THAN_2.getExceptionMessage());
        }
    }

    public static void assertNotNullTableName(String tableName) {
        if (StringUtils.isEmpty(tableName)) {
            throw new IllegalArgumentException(ExceptionType.TABLE_NAME_IS_NULL.getExceptionMessage());
        }
    }

    public static void assertNumberOfRowsIsLessThan(FileType fileType, int numberOfRows) {
        switch (fileType) {
            case XLS:
                if (numberOfRows > ConstantUtils.MAX_ROWS_IN_XLS) {
                    throw new InserterException(ExceptionType.ROWS_OVER_FLOW, StringUtils.XLS);
                }
                break;
            case XLSX:
                if (numberOfRows > ConstantUtils.MAX_ROWS_IN_XLSX) {
                    throw new InserterException(ExceptionType.ROWS_OVER_FLOW, StringUtils.XLSX);
                }
                break;
            default:
                break;
        }
    }

    public static void assertInsertDataIsNotNull(Collection<?> data) {
        if (data == null) {
            throw new IllegalArgumentException(ExceptionType.INSERT_DATA_IS_NULL.getExceptionMessage());
        }
    }

    public static void assertValidRowStartIndex(int rowStartIndex) {
        if (rowStartIndex < 1) {
            throw new IllegalArgumentException(ExceptionType.ROW_START_INDEX_IS_LESS_THAN_1.getExceptionMessage());
        }
    }

    public static void assertNumberOfRowsIsNotLessThan0(int numberOfRows) {
        if (numberOfRows < 0) {
            throw new IllegalArgumentException(ExceptionType.NUMBER_OF_ROW_IS_LESS_THAN_0.getExceptionMessage());
        }
    }

    public static void assertClazzIsNotNull(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException(ExceptionType.CLAZZ_IS_NULL.getExceptionMessage());
        }
    }

    public static void assertSourceIsNotNull(File sourceFile, InputStream inputStream) {
        if (sourceFile == null && inputStream == null) {
            throw new IllegalArgumentException(ExceptionType.SOURCE_FILE_IS_NULL.getExceptionMessage());
        }
    }

    public static void assertIsTargetFileType(FileType actual, FileType expect) {
        if (actual != expect) {
            throw new IllegalArgumentException(ExceptionType.FILE_TYPES_ARE_INCONSISTENT.getExceptionMessage());
        }
    }

    public static void assertIsTargetClassType(Class<?> actual, Class<?> expect) {
        if (!actual.equals(expect)) {
            throw new IllegalArgumentException(ExceptionType.INCONSISTENT_CLASS_TYPE.getExceptionMessage());
        }
    }

    public static void assertIndexesNotNull(Set<Integer> indexes) {
        if (indexes == null || indexes.isEmpty()) {
            throw new IllegalArgumentException(ExceptionType.INDEXES_SET_IS_NULL.getExceptionMessage());
        }
    }
}
