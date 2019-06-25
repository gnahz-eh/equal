package com.github.zhanghe.jxcel;

import java.io.File;
import java.io.InputStream;
import java.util.stream.Stream;

/**
 * Jxcel Selector
 * Read Excel Sheets
 * <p>
 * Created by zhanghe on 2019/6/12.
 */
public class Selector<T> {

    /**
     * sheet name to be read
     */
    private String sheetName;

    /**
     * The index of the sheet to be read, default is 0
     */
    private int sheetIndex;

    /**
     * Java entity type to which the Excel row is mapped
     */
    private Class<T> clazz;

    /**
     * Read data from a file
     */
    private File file;

    /**
     * Read data from a InputStream
     */
    private InputStream inputStream;

    /**
     * The start index of the sheet to be read, default is 0
     */
    private int rowStartIndex;

    /**
     * The end index of the sheet to be read, default is -1
     */
    private int rowEndIndex;


    /**
     * Construction method
     */
    public Selector(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static <T> Selector<T> select(Class<T> clazz) {
        return new Selector<T>(clazz);
    }

    public static <T> Selector<T> select(Class<T> clazz, File file) {
        return new Selector<T>(clazz).from(file);
    }

    public static <T> Selector<T> select(Class<T> clazz, InputStream inputStream) {
        return new Selector<T>(clazz).from(inputStream);
    }

    /**
     * Read data from an Excel file
     *
     * @param file excel file object
     * @return Selector
     */
    public Selector<T> from(File file) {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File is not exist!");
        }
        this.file = file;
        return this;
    }

    /**
     * Read data from an InputStream
     *
     * @param inputStream excel InputStream
     * @return Selector
     */
    public Selector<T> from(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    /**
     * Set the index of start line, default is 0
     * @return Selector
     */
    public Selector<T> where() {
        this.rowStartIndex = 0;
        this.rowEndIndex = -1;
        return this;
    }

    /**
     * Set the index of start line, default is 0
     *
     * @param rowStartIndex start row index
     * @return Selector
     */
    public Selector<T> where(int rowStartIndex) {
        if (rowStartIndex < 0) {
            throw new IllegalArgumentException("rowStartIndex should be more than 0");
        }
        this.rowStartIndex = rowStartIndex;
        this.rowEndIndex = -1;
        return this;
    }

    /**
     * Set the index of start line, default is 0,
     * Set the index of end line, default is -1,
     *
     * @param rowStartIndex start row index
     * @param rowEndIndex end row index
     * @return Selector
     */
    public Selector<T> where(int rowStartIndex, int rowEndIndex) {
        if (rowStartIndex < 0) {
            throw new IllegalArgumentException("rowStartIndex should be more than 0");
        }
        if (rowEndIndex < 0) {
            throw new IllegalArgumentException("rowEndIndex should be more than 0");
        }
        if (rowEndIndex < rowStartIndex) {
            throw new IllegalArgumentException("rowEndIndex should be more than or equal to rowStartIndex");
        }

        this.rowStartIndex = rowStartIndex;
        this.rowEndIndex = rowEndIndex;
        return this;
    }

    /**
     * Return the result as a Stream
     *
     * @return Stream
     */
    public Stream<T> toStream() {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz can be not null");
        }

        if (file == null && inputStream == null) {
            throw new IllegalArgumentException("the excel can not be null");
        }

        if (file == null) {
            return null;
        } else {
            return null;
        }
    }

    /**
     * Getters and Setters
     */
    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public int getRowStartIndex() {
        return rowStartIndex;
    }

    public void setRowStartIndex(int rowStartIndex) {
        this.rowStartIndex = rowStartIndex;
    }

    public int getRowEndIndex() {
        return rowEndIndex;
    }

    public void setRowEndIndex(int rowEndIndex) {
        this.rowEndIndex = rowEndIndex;
    }

}
