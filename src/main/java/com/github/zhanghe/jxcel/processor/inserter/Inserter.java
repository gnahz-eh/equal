package com.github.zhanghe.jxcel.processor.inserter;

import com.github.zhanghe.jxcel.enums.FileType;
import com.github.zhanghe.jxcel.exception.ExceptionUtils;
import com.github.zhanghe.jxcel.exception.JxcelException;
import com.github.zhanghe.jxcel.utils.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class Inserter {

    private Collection<?> data;
    private FileType fileType;
    private int rowStartIndex = 1;
    private File sourceFile;
    private int tableIndex = 0;
    private String tableName = "Default";

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
        this.data = data;
        return this;
    }

    public void fromRow(int rowStartIndex) {
        if (rowStartIndex < 1) {
            throw new IllegalArgumentException(ExceptionUtils.exceptionMap.get(ExceptionUtils.ROW_START_INDEX_IS_LESS_THAN_1));
        }
        this.rowStartIndex = rowStartIndex;
    }

    public Inserter into(File sourceFile) {
        assertNullSourceFile(sourceFile);
        this.sourceFile = sourceFile;
        return this;
    }

    public Inserter into(File sourceFile, int tableIndex) {
        assertNullSourceFile(sourceFile);
        assertTableIndexLessThanZero(tableIndex);
        this.sourceFile = sourceFile;
        this.tableIndex = tableIndex;
        return this;
    }

    public Inserter into(File sourceFile, String tableName) {
        assertNullSourceFile(sourceFile);
        assertNullTableName(tableName);
        this.sourceFile = sourceFile;
        this.tableName = tableName;
        return this;
    }

    public Inserter into(File sourceFile, int tableIndex, String tableName) {
        assertNullSourceFile(sourceFile);
        assertTableIndexLessThanZero(tableIndex);
        assertNullTableName(tableName);
        this.sourceFile = sourceFile;
        this.tableIndex = tableIndex;
        this.tableName = tableName;
        return this;
    }

    private void assertNullSourceFile(File sourceFile) {
        if (sourceFile == null) {
            throw new IllegalArgumentException(ExceptionUtils.exceptionMap.get(ExceptionUtils.SOURCE_FILE_IS_NULL));
        }
    }

    private void assertTableIndexLessThanZero(int tableIndex) {
        if (tableIndex < 0) {
            throw new IllegalArgumentException(ExceptionUtils.exceptionMap.get(ExceptionUtils.TABLE_INDEX_IS_LESS_THAN_0));
        }
    }

    private void assertNullTableName(String tableName) {
        if (StringUtils.isEmpty(tableName)) {
            throw new IllegalArgumentException(ExceptionUtils.exceptionMap.get(ExceptionUtils.TABLE_NAME_IS_NULL));
        }
    }

    public void flush() throws JxcelException {
        if (data == null || data.isEmpty()) {
            throw new JxcelException(ExceptionUtils.INSERT_DATA_IS_NULL);
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(this.sourceFile);
        } catch (FileNotFoundException e) {
            throw new JxcelException(e);
        }
        new FileInserter(fileOutputStream).insertTable(this);
    }
}
