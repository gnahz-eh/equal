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
import com.github.equal.enums.ExceptionType;
import com.github.equal.exception.InserterException;
import com.github.equal.processor.adapter.Adapter;
import com.github.equal.utils.ConstantUtils;
import com.github.equal.utils.InserterUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CSVInserter extends FileInserter {

    private List<Field> fields;
    private FileWriter fileWriter;
    private InputStream inputStream;

    public CSVInserter(Inserter inserter) {
        super(inserter);
    }

    @Override
    public void insertIntoFile() {
        initCSVFile();

        Collection<?> data = inserter.getData();
        int numberOfRows = inserter.getNumberOfRows();
        int rowStartIndex = inserter.getRowStartIndex();
        boolean isSourceFileExist = inserter.isSourceFileExist();
        List<String> csvRowData = this.parseData(data, numberOfRows, rowStartIndex, isSourceFileExist);

        try (FileWriter fw = this.fileWriter) {
            fw.write('\ufeff');

            // insert row
            for (String row : csvRowData) {
                fw.write(row);
            }
            fw.flush();
        } catch (IOException e) {
            throw new InserterException(e);
        }
    }

    private List<String> parseData(Collection<?> data, int numberOfRows, int rowStartIndex, boolean isSourceFileExist) {
        List<String> csvRowData = null;

        // source exist not exist
        if (!isSourceFileExist) {
            csvRowData = new ArrayList<>(rowStartIndex + numberOfRows - 1);
            int rowIndex = rowStartIndex - 1;

            // insert blank line
            while (rowIndex-- > 1) csvRowData.add(ConstantUtils.NEW_LINE);
            // insert columns names
            if (this.insertColumnNames) {
                csvRowData.add(parseColumnNames());
            }

            for (Object obj : data) {
                if (0 == numberOfRows--) break;
                try {
                    if (obj == null) {
                        csvRowData.add(ConstantUtils.NEW_LINE);
                    } else {
                        String row = parseRow(obj);
                        csvRowData.add(row);
                    }
                } catch (Exception e) {
                    throw new InserterException(e);
                }
            }
        }
        // source exist, not insert column names
        else {

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, inserter.getCharset()));
            csvRowData = new ArrayList<>();
            String line = null;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    csvRowData.add(line + ConstantUtils.NEW_LINE);
                }
            } catch (IOException e) {
                throw new InserterException(e);
            }

            // create a new file
            try {
                fileWriter = new FileWriter(inserter.getSourceFile(), false);
            } catch (IOException e) {
                throw new InserterException(e);
            }

            int numberOfInitialData = csvRowData.size();

            // add blank column if rowStartIndex > csvRowData.size()
            int numberOfBlankLine = rowStartIndex - (numberOfInitialData + 1);
            while (numberOfBlankLine > 0) {
                csvRowData.add(ConstantUtils.NEW_LINE);
                numberOfBlankLine--;
            }

            for (Object obj : data) {
                if (0 == numberOfRows--) break;
                try {
                    String row = null;
                    if (obj != null) {
                        row = parseRow(obj);
                    } else {
                        row = ConstantUtils.NEW_LINE;
                    }
                    if (rowStartIndex > numberOfInitialData) {
                        csvRowData.add(row);
                    } else {
                        csvRowData.set(rowStartIndex - 1, row);
                    }
                    rowStartIndex++;
                } catch (Exception e) {
                    throw new InserterException(e);
                }
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
            sb.append(val == null ? ConstantUtils.BLINK_STRING : adapter.toString(val));
            if (i < fields.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(ConstantUtils.NEW_LINE);
        return sb.toString();
    }

    private String parseColumnNames() {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            Column column = fields.get(i).getAnnotation(Column.class);
            sb.append(column.name());
            if (i < fields.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(ConstantUtils.NEW_LINE);
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
            this.insertColumnNames = InserterUtils.doesInsertColumnNames(inserter.getRowStartIndex(), inserter.getNumberOfRows());
            try {
                fileWriter = new FileWriter(inserter.getSourceFile(), inserter.isSourceFileExist());
            } catch (IOException e) {
                throw new InserterException(e);
            }
        } else {
            // source file exist, need to read the source file data, do not insert columns names
            try {
                this.inputStream = new FileInputStream(inserter.getSourceFile());
            } catch (FileNotFoundException e) {
                throw new InserterException(ExceptionType.FILE_OPEN_ERROR, inserter.getSourceFile().getName(), e);
            }
        }
    }
}
