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

import com.github.equal.enums.ExceptionType;
import com.github.equal.exception.DeleterException;
import com.github.equal.utils.ConstantUtils;
import com.github.equal.utils.FileUtils;
import com.github.equal.utils.StringUtils;

import java.io.*;
import java.util.List;

public class CSVDeleter extends FileDeleter {

    private InputStream inputStream;

    public CSVDeleter(Deleter<?> deleter) {
        super(deleter);
    }

    @Override
    public void deleteFromFile() {
        initCSVFile();

        int rowIndex = deleter.getRowStartIndex() - 1;
        int numberOfRows = deleter.getNumberOfRows();
        List<Integer> indexes = deleter.getIndexes();

        List<String> csvRowData = parseData(numberOfRows, rowIndex, indexes);

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(deleter.getSourceFile(), false);
        } catch (IOException e) {
            throw new DeleterException(e);
        }

        FileUtils.flushData(fileWriter, csvRowData);
    }

    private List<String> parseData(int numberOfRows, int rowIndex, List<Integer> indexes) {
        List<String> csvRowData = FileUtils.readCSVData(inputStream, deleter.getCharset());
        int endIndx = csvRowData.size() - 1;

        if (indexes != null) {
            for (Integer index : indexes) {
                if ((index - 1) > endIndx) break;
                csvRowData.set(index - 1, StringUtils.NEW_LINE);
            }
        } else {
            if (rowIndex > 0 && rowIndex <= endIndx) {
                // no numberOfRow condition
                if (numberOfRows == ConstantUtils.DEFAULT_NUMBER_OF_ROW) {
                    numberOfRows = endIndx - rowIndex + 1;
                }
                while (rowIndex <= endIndx && numberOfRows != 0) {
                    csvRowData.set(rowIndex++, StringUtils.NEW_LINE);
                    numberOfRows--;
                }
            }
        }
        return csvRowData;
    }

    private void initCSVFile() {
        try {
            this.inputStream = new FileInputStream(deleter.getSourceFile());
        } catch (FileNotFoundException e) {
            throw new DeleterException(ExceptionType.FILE_OPEN_ERROR, deleter.getSourceFile().getName(), e);
        }
    }
}
