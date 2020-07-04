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
import com.github.equal.exception.InserterException;
import com.github.equal.utils.ConstantUtils;
import com.github.equal.utils.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public abstract class ExcelFileDeleter extends FileDeleter {

    protected Sheet table;
    protected Workbook workbook;
    protected OutputStream outputStream;

    public ExcelFileDeleter(Deleter<?> deleter) {
        super(deleter);
    }

    private void initExcelFile() {
        int tableIndex = deleter.getTableIndex();
        String tableName = deleter.getTableName();
        Sheet table;

        table = FileUtils.getTable(workbook, tableIndex, tableName);

        if (table == null) {
            throw new DeleterException(ExceptionType.DID_NOT_FIND_THE_TABLE);
        }

        this.table = table;

        try {
            this.outputStream = new FileOutputStream(deleter.getSourceFile());
        } catch (FileNotFoundException e) {
            throw new InserterException(e);
        }
    }

    protected void deleteData() {
        initExcelFile();

        int rowIndex = deleter.getRowStartIndex() - 1;
        int numberOfRows = deleter.getNumberOfRows();
        int lastIndex = table.getLastRowNum();
        List<Integer> indexes = deleter.getIndexes();

        if (indexes != null) {
            for (Integer index : indexes) {
                if ((index - 1) > lastIndex) break;
                table.createRow(index - 1);
            }
        } else {
            if (rowIndex > 0 && rowIndex <= lastIndex) {
                // no numberOfRow condition
                if (numberOfRows == ConstantUtils.DEFAULT_NUMBER_OF_ROW) {
                    numberOfRows = lastIndex - rowIndex + 1;
                }
                while (rowIndex <= lastIndex && numberOfRows != 0) {
                    table.createRow(rowIndex++);
                    numberOfRows--;
                }
            }
        }

        FileUtils.flushData(workbook, outputStream);
    }
}
