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

package com.github.equal.processor.inserter;

import com.github.equal.exception.EqualException;
import com.github.equal.enums.FileType;
import com.github.equal.utils.ConstantUtils;
import com.github.equal.utils.ExceptionUtils;
import com.github.equal.utils.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class Inserter {

    private Collection<?> data;
    private FileType fileType;
    private int rowStartIndex = ConstantUtils.ROW_START_INDEX; // first row is title
    private int numberOfRows;
    private File sourceFile;
    private int tableIndex = 0;
    private String tableName = StringUtils.DEFAULT;
    private boolean dataInitFlag = false;
    private boolean appendFlag = false;
    private Charset charset = StandardCharsets.UTF_8;

    public Inserter(FileType fileType) {
        this.fileType = fileType;
    }

    public static Inserter insert() {
        return new Inserter(FileType.XLSX);
    }

    public static Inserter insert(FileType fileType) {
        return new Inserter(fileType);
    }

    public Inserter values(Collection<?> data) {
        ExceptionUtils.assertInsertDataIsNotNull(data);
        this.data = data;
        this.dataInitFlag = true;
        return this;
    }

    public Inserter range() {
        this.rowStartIndex = ConstantUtils.ROW_START_INDEX;
        if (!this.dataInitFlag) {
            throw new RuntimeException("Insert data have not been init initialized");
        }
        int n = this.data.size();
        ExceptionUtils.assertNumberOfRowsIsLessThan(this.fileType, n);
        this.numberOfRows = n;
        this.appendFlag = true;
        return this;
    }

    public Inserter range(int rowStartIndex) {
        ExceptionUtils.assertValidRowStartIndex(rowStartIndex);
        range();
        this.appendFlag = false;
        this.rowStartIndex = rowStartIndex;
        return this;
    }

    public Inserter range(int rowStartIndex, int numberOfRows) {
        ExceptionUtils.assertValidRowStartIndex(rowStartIndex);
        this.range();
        this.rowStartIndex = rowStartIndex;
        this.numberOfRows = Math.min(this.numberOfRows, numberOfRows);
        this.appendFlag = false;
        return this;
    }

    // sourceFile cannot be a dir
    public Inserter into(File sourceFile) {
        ExceptionUtils.assertNotNullSourceFile(sourceFile);
        if (sourceFile.exists()) {
            ExceptionUtils.assertSourceFileIsNotDir(sourceFile);
        } else {
            try {
                sourceFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.sourceFile = sourceFile;
        return this;
    }

    // sourceFile must be exist
    public Inserter into(File sourceFile, int tableIndex) {
        ExceptionUtils.assertValidSourceFile(sourceFile);
        ExceptionUtils.assertTableIndexBigThanZero(tableIndex);
        this.sourceFile = sourceFile;
        this.tableIndex = tableIndex;
        return this;
    }

    public Inserter into(File sourceFile, String tableName) {
        ExceptionUtils.assertValidSourceFile(sourceFile);
        ExceptionUtils.assertNotNullTableName(tableName);
        this.sourceFile = sourceFile;
        this.tableName = tableName;
        return this;
    }

    public Inserter into(File sourceFile, int tableIndex, String tableName) {
        ExceptionUtils.assertValidSourceFile(sourceFile);
        ExceptionUtils.assertTableIndexBigThanZero(tableIndex);
        ExceptionUtils.assertNotNullTableName(tableName);
        this.sourceFile = sourceFile;
        this.tableIndex = tableIndex;
        this.tableName = tableName;
        return this;
    }

    public void flush() throws EqualException {
        if (data == null || data.isEmpty()) {
            throw new EqualException(ExceptionUtils.INSERT_DATA_IS_NULL);
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(this.sourceFile);
        } catch (FileNotFoundException e) {
            throw new EqualException(e);
        }
        new FileInserter(fileOutputStream).insertTable(this);
    }
}
