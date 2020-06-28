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

package com.github.equal.enums;

public enum ExceptionType {

    SOURCE_FILE_IS_NULL                       (100, "Source file is null"),
    ROW_START_INDEX_IS_LESS_THAN_2            (101, "Row start row index is less than 2"),
    NUMBER_OF_ROW_IS_LESS_THAN_0              (102, "Number of row is less than 0"),
    ROW_END_INDEX_IS_LESS_THAN_ROW_START_INDEX(103, "Row end index is less than row start index"),
    TABLE_INDEX_IS_LESS_THAN_0                (104, "Table index is less than 0"),
    TABLE_NAME_IS_NULL                        (105, "Table name is null"),
    CLAZZ_IS_NULL                             (106, "Clazz is null"),
    ROW_START_INDEX_IS_LESS_THAN_1            (107, "Row start row index is less than 1"),
    UNSUPPORTED_FILE_TYPE                     (109, "Unsupported file type"),
    FILE_NOT_FOUND                            (110, "File not found"),
    DID_NOT_FIND_THE_TABLE                    (111, "Did find the table, please check the param tableName"),

    INSERT_DATA_IS_NULL                       (201, "Insert data is null"),
    FILE_OPEN_ERROR                           (202, "File open error"),
    SOURCE_FILE_DOES_NOT_EXIST                (203, "Source file does not exist"),
    SOURCE_FILE_IS_NOT_A_FILE                 (204, "Source file is not a file"),
    ROWS_OVER_FLOW                            (205, "Rows over flow"),
    INVALID_TABLE_INDEX                       (206, "Invalid table index"),
    INSERT_DATA_ERROR                         (207, "Insert data error"),
    FILE_TYPES_ARE_INCONSISTENT               (208, "File types are inconsistent"),
    INCONSISTENT_CLASS_TYPE                   (209, "Inconsistent class type"),

    DELETE_DATA_ERROR                         (307, "Delete data error"),

    ADAPT_VALUE_ERROR                         (501, "Adapt value error"),
    ADAPT_DATE_ERROR                          (502, "Adapt date error"),
    ADAPT_TIME_ERROR                          (503, "Adapt time error"),
    ADAPT_DATE_TIME_ERROR                     (504, "Adapt date time error"),

    FLUSH_DATA_ERROR                          (601, "Flush data error");

    private final int exceptionCode;
    private final String exceptionMessage;

    ExceptionType(int exceptionCode, String exceptionMessage) {
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
    }

    public int getExceptionCode() {
        return exceptionCode;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
