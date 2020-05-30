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
import com.github.equal.processor.adapter.Adapter;
import com.github.equal.processor.adapter.AdapterFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FileInserter {

    Inserter inserter;
    Map<Integer, Field> fieldIndexes;
    Map<Field, Adapter<String, ?>> fieldAdapters;
    List<Column> columns;
    boolean insertColumnNames;

    public FileInserter(Inserter inserter) {
        this.inserter = inserter;
    }

    void init() {

        Field[] fields = inserter.getData().iterator().next().getClass().getDeclaredFields();
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

    public abstract void insertIntoFile();
}
