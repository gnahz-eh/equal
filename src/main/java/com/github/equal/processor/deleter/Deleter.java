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

package com.github.equal.processor.deleter;

import com.github.equal.enums.FileType;
import com.github.equal.exception.DeleterException;
import com.github.equal.utils.ConstantUtils;
import com.github.equal.utils.ExceptionUtils;
import com.github.equal.utils.FileUtils;
import com.github.equal.utils.StringUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Deleter<T> {

    private File sourceFile;
    private FileType fileType;
    private int tableIndex = ConstantUtils.DEFAULT_TABLE_INDEX;
    private String tableName = StringUtils.DEFAULT;
    private int rowStartIndex = ConstantUtils.DEFAULT_DELETE_START_INDEX;
    private int numberOfRows = ConstantUtils.DEFAULT_NUMBER_OF_ROW;
    private List<Integer> indexes = null;
    private Class<T> clazz;
    private final Charset charset = StandardCharsets.UTF_8;

    public Deleter(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static <T> Deleter<T> delete(Class<T> clazz) {
        return new Deleter<>(clazz);
    }

    public Deleter<T> from(File sourceFile) {
        ExceptionUtils.assertValidSourceFile(sourceFile);
        this.sourceFile = sourceFile;
        this.fileType = FileUtils.getFileTypeBySourceFile(sourceFile);
        return this;
    }

    public Deleter<T> from(File sourceFile, int tableIndex) {
        ExceptionUtils.assertValidSourceFile(sourceFile);
        ExceptionUtils.assertTableIndexBigThanZero(tableIndex);
        this.sourceFile = sourceFile;
        this.tableIndex = tableIndex;
        this.fileType = FileUtils.getFileTypeBySourceFile(sourceFile);
        return this;
    }

    public Deleter<T> from(File sourceFile, String tableName) {
        ExceptionUtils.assertValidSourceFile(sourceFile);
        ExceptionUtils.assertNotNullTableName(tableName);
        this.sourceFile = sourceFile;
        this.tableName = tableName;
        this.fileType = FileUtils.getFileTypeBySourceFile(sourceFile);
        return this;
    }

    public Deleter<T> where(int rowStartIndex) {
        ExceptionUtils.assertRowStartIndexBigThan2(rowStartIndex);
        this.rowStartIndex = rowStartIndex;
        this.numberOfRows = ConstantUtils.DEFAULT_NUMBER_OF_ROW;
        this.indexes = null;
        return this;
    }

    public Deleter<T> where(int rowStartIndex, int numberOfRows) {
        ExceptionUtils.assertRowStartIndexBigThan2(rowStartIndex);
        ExceptionUtils.assertNumberOfRowsIsNotLessThan0(numberOfRows);
        this.rowStartIndex = rowStartIndex;
        this.numberOfRows = numberOfRows;
        this.indexes = null;
        return this;
    }

    public Deleter<T> where(Set<Integer> indexes) {
        ExceptionUtils.assertIndexesNotNull(indexes);
        this.indexes = indexes
                .stream()
                .filter(i -> i >= ConstantUtils.ROW_START_INDEX)
                .sorted(Comparator.comparingInt(Integer::intValue))
                .collect(Collectors.toList());
        return this;
    }

    public void execute() throws DeleterException {
        DeleterContext.deleteFromFile(this);
    }

    public FileType getFileType() {
        return fileType;
    }

    public String getTableName() {
        return tableName;
    }

    public int getTableIndex() {
        return tableIndex;
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public int getRowStartIndex() {
        return rowStartIndex;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public List<Integer> getIndexes() {
        return indexes;
    }

    public Charset getCharset() {
        return charset;
    }
}
