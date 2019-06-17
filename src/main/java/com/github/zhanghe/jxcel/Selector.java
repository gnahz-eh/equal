package com.github.zhanghe.jxcel;

import java.io.File;
import java.io.InputStream;

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
     * getters and setters
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
