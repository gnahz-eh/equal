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

package com.github.jxcel.processor.selector;

import com.github.jxcel.exception.ExceptionUtils;
import com.github.jxcel.exception.JxcelException;
import com.github.jxcel.utils.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Selector<T> {

    private File sourceFile;
    private InputStream inputStream;
    private String tableName;
    private Class<T> clazz;
    private int tableIndex = 0;
    private int rowStartIndex = 2;
    private int rowEndIndex = -1;
    private Stream<T> returnStream;

    /**
     * Construction method
     */
    public Selector(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static <T> Selector select(Class<T> clazz) {
        return new Selector<>(clazz);
    }

    public Selector<T> from(File sourceFile) {
        if (sourceFile == null || !sourceFile.exists()) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.SOURCE_FILE_IS_NULL));
        }
        this.sourceFile = sourceFile;
        return this;
    }

    public Selector<T> from(File sourceFile, int tableIndex) {
        if (sourceFile == null || !sourceFile.exists()) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.SOURCE_FILE_IS_NULL));
        }
        if (tableIndex < 0) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.TABLE_INDEX_IS_LESS_THAN_0));
        }
        this.sourceFile = sourceFile;
        this.tableIndex = tableIndex;
        return this;
    }

    public Selector<T> from(File sourceFile, String tableName) {
        if (sourceFile == null || !sourceFile.exists()) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.SOURCE_FILE_IS_NULL));
        }
        if (StringUtils.isEmpty(tableName)) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.TABLE_NAME_IS_NULL));
        }
        this.sourceFile = sourceFile;
        this.tableName = tableName;
        return this;
    }

    public Selector<T> from(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    public Selector<T> from(InputStream inputStream, int tableIndex) {
        if (tableIndex < 0) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.TABLE_INDEX_IS_LESS_THAN_0));
        }
        this.inputStream = inputStream;
        return this;
    }

    public Selector<T> from(InputStream inputStream, String tableName) {
        if (StringUtils.isEmpty(tableName)) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.TABLE_NAME_IS_NULL));
        }
        this.inputStream = inputStream;
        this.tableName = tableName;
        return this;
    }

    public Selector<T> where() {
        this.rowStartIndex = 2;
        this.rowEndIndex = -1;
        return this;
    }

    public Selector<T> where(int rowStartIndex) {
        if (rowStartIndex < 2) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.ROW_START_INDEX_IS_LESS_THAN_2));
        }
        this.rowStartIndex = rowStartIndex;
        this.rowEndIndex = -1;
        return this;
    }

    public Selector<T> where(int rowStartIndex, int rowEndIndex) {
        if (rowStartIndex < 2) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.ROW_START_INDEX_IS_LESS_THAN_2));
        }
        if (rowEndIndex < 0 && rowEndIndex != -1) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.ROW_END_INDEX_IS_LESS_THAN_0));
        }
        if (rowEndIndex < rowStartIndex) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.ROW_END_INDEX_IS_LESS_THAN_ROW_START_INDEX));
        }

        this.rowStartIndex = rowStartIndex;
        this.rowEndIndex = rowEndIndex;
        return this;
    }

    private Stream<T> toStream() {
        if (clazz == null) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.CLAZZ_IS_NULL));
        }

        if (sourceFile == null && inputStream == null) {
            throw new IllegalArgumentException(ExceptionUtils.EXCEPTION_MAP.get(ExceptionUtils.SOURCE_FILE_IS_NULL));
        }

        if (sourceFile != null) {
            if (this.returnStream == null) {
                this.returnStream = SelectorContext.selectFromFile(this);
            }
            return returnStream;
        } else {
            if (this.returnStream == null) {
                this.returnStream = SelectorContext.selectFromStream(this);
            }
            return returnStream;
        }
    }

    public List<T> executeQuery() throws JxcelException {
        toStream();
        return this.returnStream.collect(Collectors.toList());
    }

    public Selector<T> where(Predicate<? super T> predicate) {
        this.returnStream = this.toStream().filter(predicate);
        return this;
    }
    /**
     * Getters
     */
    public String getTableName() {
        return tableName;
    }

    public int getTableIndex() {
        return tableIndex;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public int getRowStartIndex() {
        return rowStartIndex;
    }

    public int getRowEndIndex() {
        return rowEndIndex;
    }
}
