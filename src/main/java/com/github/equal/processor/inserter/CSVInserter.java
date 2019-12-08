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

package com.github.equal.processor.inserter;

import com.github.equal.annotation.Column;
import com.github.equal.exception.EqualException;
import com.github.equal.processor.adapter.Adapter;
import com.github.equal.utils.ExceptionUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CSVInserter extends FileInserter {

    private List<Field> fields;
    private InputStream inputStream;

    public CSVInserter(Inserter inserter) {
        super(inserter);
        try {
            this.outputStream = new FileOutputStream(this.inserter.getSourceFile());
        } catch (FileNotFoundException e) {
            throw new EqualException(e);
        }
    }

    @Override
    public void insertIntoFile() {
        initCSVFile();

        Collection<?> data = inserter.getData();
        List<String> csvRowData = this.parseData(data);

        try (OutputStreamWriter osw = new OutputStreamWriter(this.outputStream, inserter.getCharset())) {
            osw.write('\ufeff');

            int rowIndex = inserter.getRowStartIndex() - 1;

            // insert blank line
            while (rowIndex-- > 1) osw.write("\n");

            // insert columns names
            if (insertColumnNames) {
                osw.write(parseColumnNames());
            }

            // insert row
            for (String row : csvRowData) {
                osw.write(row);
            }
        } catch (Exception e) {
            throw new EqualException(e);
        }
    }

    private List<String> parseData(Collection<?> data) {
        List<String> csvRowData = new ArrayList<>(data.size());

        for (Object obj : data) {
            try {
                String row = parseRow(obj);
                csvRowData.add(row);
            } catch (Exception e) {
                throw new EqualException(e);
            }
        }
        return csvRowData;
    }

    private String parseRow(Object obj) throws IllegalArgumentException, IllegalAccessException {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < fields.size(); i++) {

            Field field = fields.get(i);
            Object val = field.get(obj);
            Adapter adapter = fieldAdapters.get(field);
            sb.append(val == null ? "" : adapter.toString(val));
            if (i < fields.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    private String parseColumnNames() throws IOException {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            Column column = fields.get(i).getAnnotation(Column.class);
            sb.append(column.name());
            if (i < fields.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    private void initCSVFile() {
        init();
        this.fields = fieldIndexes
                .keySet()
                .stream()
                .sorted()
                .map(fieldIndexes::get)
                .collect(toList());

        this.insertColumnNames = false;
        if (!inserter.isSourceFileExist()) {
            this.insertColumnNames = inserter.getRowStartIndex() > 1;
        } else {
            try {
                this.inputStream = new FileInputStream(inserter.getSourceFile());
            } catch (FileNotFoundException e) {
                throw new EqualException(ExceptionUtils.FILE_NOT_FOUND, inserter.getSourceFile().getName(), e);
            }
        }
    }
}
