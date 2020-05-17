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

import com.github.equal.exception.EqualException;
import com.github.equal.utils.ConstantUtils;
import com.github.equal.utils.ExceptionUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
    private int rowStartIndex = ConstantUtils.ROW_START_INDEX;
    private int numberOfRows = 0;
    private Stream<T> returnStream;
    private Charset charset = StandardCharsets.UTF_8;;

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
        ExceptionUtils.assertValidSourceFile(sourceFile);
        this.sourceFile = sourceFile;
        return this;
    }

    public Selector<T> from(File sourceFile, int tableIndex) {
        ExceptionUtils.assertValidSourceFile(sourceFile);
        ExceptionUtils.assertTableIndexBigThanZero(tableIndex);
        this.sourceFile = sourceFile;
        this.tableIndex = tableIndex;
        return this;
    }

    public Selector<T> from(File sourceFile, String tableName) {
        ExceptionUtils.assertValidSourceFile(sourceFile);
        ExceptionUtils.assertNotNullTableName(tableName);
        this.sourceFile = sourceFile;
        this.tableName = tableName;
        return this;
    }

    public Selector<T> from(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    public Selector<T> from(InputStream inputStream, int tableIndex) {
        ExceptionUtils.assertTableIndexBigThanZero(tableIndex);
        this.inputStream = inputStream;
        return this;
    }

    public Selector<T> from(InputStream inputStream, String tableName) {
        ExceptionUtils.assertNotNullTableName(tableName);
        this.inputStream = inputStream;
        this.tableName = tableName;
        return this;
    }

    public Selector<T> where() {
        this.rowStartIndex = ConstantUtils.ROW_START_INDEX;
        this.numberOfRows = ConstantUtils.DEFAULT_NUMBER_OF_ROW;
        return this;
    }

    public Selector<T> where(int rowStartIndex) {
        ExceptionUtils.assertRowStartIndexBigThan2(rowStartIndex);
        this.rowStartIndex = rowStartIndex;
        this.numberOfRows = ConstantUtils.DEFAULT_NUMBER_OF_ROW;
        return this;
    }

    public Selector<T> where(int rowStartIndex, int numberOfRows) {
        ExceptionUtils.assertRowStartIndexBigThan2(rowStartIndex);
        ExceptionUtils.assertNumberOfRowsIsNotLessThan0(numberOfRows);
        this.rowStartIndex = rowStartIndex;
        this.numberOfRows = numberOfRows;
        return this;
    }

    private Stream<T> toStream() {
        ExceptionUtils.assertClazzIsNotNull(clazz);
        ExceptionUtils.assertSourceIsNotNull(sourceFile, inputStream);

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

    public List<T> executeQuery() throws EqualException {
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

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
