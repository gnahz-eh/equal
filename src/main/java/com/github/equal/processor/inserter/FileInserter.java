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

import com.github.equal.annotation.Column;
import com.github.equal.exception.EqualException;
import com.github.equal.processor.adapter.Adapter;
import com.github.equal.processor.adapter.AdapterFactory;
import com.github.equal.utils.ConstantUtils;
import com.github.equal.utils.ExceptionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

public abstract class FileInserter {

    protected Map<Integer, Field> fieldIndexes;
    protected Map<Field, Adapter<String, ?>> fieldAdapters;
    protected OutputStream outputStream;
    protected List<Column> columns;
    protected Sheet table;
    protected Workbook workbook;

    public abstract void insertIntoFile(Inserter inserter);

    protected void insertData(Inserter inserter) throws EqualException {

        int tableIndex = inserter.getTableIndex();
        String tableName = inserter.getTableName();
        boolean insertColumnNames = false;
        Sheet table = null;
        if (tableIndex != -1) {
            try {
                table = workbook.getSheetAt(tableIndex);
            } catch (Exception e) {
                throw new EqualException(ExceptionUtils.INVALID_TABLE_INDEX, String.valueOf(tableIndex));
            }
        } else {
            table = workbook.getSheet(tableName);
        }

        if (table == null) {
            table = workbook.createSheet(inserter.getTableName());
            insertColumnNames = true;
            if (inserter.getRowStartIndex() == 1) {
                insertColumnNames = false;
            }
        }
        this.table = table;

        Collection<?> data = inserter.getData();
        Field[] fields = data.iterator().next().getClass().getDeclaredFields();

        init(fields);

        int rowIndex = inserter.getRowStartIndex() - 1;

        if (insertColumnNames) {
            this.insertColumnNames(rowIndex - 1);
        }

        int numberOfRows = inserter.getNumberOfRows();
        Iterator<?> iterator = data.iterator();
        int i = 0;

        try {
            for (; i < numberOfRows; i++) {
                this.insertRow(iterator.next(), rowIndex++);
            }
        } catch (Exception e) {
            throw new EqualException(ExceptionUtils.INSERT_DATA_ERROR, String.valueOf(i));
        }

        flushData(inserter);
    }

    private void insertColumnNames(int rowIndex) {
        Row head = table.createRow(rowIndex);
        for (Column column : columns) {
            Cell cell = head.createCell(column.index());
            cell.setCellValue(column.name());
            table.setColumnWidth(column.index(), ConstantUtils.DEFAULT_COLUMN_WIDTH);
        }
    }

    private void insertRow(Object obj, int index) throws Exception {
        Row row = table.createRow(index);

        for (Integer colIndex : fieldIndexes.keySet()) {
            Field field = fieldIndexes.get(colIndex);

            if (field == null) {
                continue;
            }
            Object val = field.get(obj);

            if (val == null) {
                continue;
            }

            Cell cell = row.createCell(colIndex);
            Adapter adapter = fieldAdapters.get(field);
            String formatedValue = adapter.toString(val);
            cell.setCellValue(formatedValue);
        }
    }

    private void init(Field[] fields) {
        this.fieldIndexes = new HashMap<>(fields.length);
        this.fieldAdapters = new HashMap<>();
        this.columns = new ArrayList<>();

        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);

            if (column != null) {
                field.setAccessible(true);
                fieldIndexes.put(column.index(), field);

                columns.add(column);

                Adapter adapter = null;
                adapter = AdapterFactory.getInstance(field);
                if (adapter != null) {
                    fieldAdapters.put(field, adapter);
                }
            }
        }
    }

    private void flushData(Inserter inserter) throws EqualException {
        try {
            this.outputStream = new FileOutputStream(inserter.getSourceFile());
            workbook.write(this.outputStream);
            this.outputStream.close();
        } catch (FileNotFoundException e) {
            throw new EqualException(e);
        } catch (IOException e) {
            throw new EqualException(ExceptionUtils.INSERT_DATA_ERROR);
        }
    }
}
