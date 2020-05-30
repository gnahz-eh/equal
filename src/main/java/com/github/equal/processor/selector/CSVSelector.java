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

package com.github.equal.processor.selector;

import com.github.equal.annotation.Column;
import com.github.equal.exception.EqualException;
import com.github.equal.processor.adapter.Adapter;
import com.github.equal.utils.ConstantUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.stream.Stream;

public class CSVSelector extends FileSelector {

    private InputStream inputStream;

    public CSVSelector(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public <T> Stream<T> selectFromFile(Selector selector) throws EqualException {
        Stream.Builder<T> builder = Stream.builder();
        Class clazz = selector.getClazz();
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, selector.getCharset()));
            init(clazz.getDeclaredFields());
            int rowStartIndex = selector.getRowStartIndex();
            int numberOfRows = selector.getNumberOfRows();
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                if (numberOfRows == 0) break;
                if (rowStartIndex-- > 1) continue;

                if (line.equals(ConstantUtils.BLINK_STRING)) {
                    builder.add(null);
                } else {
                    Object obj = clazz.newInstance();
                    String[] items = line.split(",");
                    for (Field field : fieldIndexes.values()) {
                        setField(field, items, obj);
                    }
                    builder.add((T) obj);
                }
                numberOfRows--;
            }

            // if numberRows > rows, use null
            if (numberOfRows > 0) {
                while (0 != numberOfRows--) {
                    builder.add(null);
                }
            }
            return builder.build();
        } catch (IOException e) {
            throw new EqualException(e);
        } catch (InstantiationException e1) {
            throw new EqualException(e1);
        } catch (IllegalAccessException e2) {
            throw new EqualException(e2);
        }
    }

    public void setField(Field field, String[] items, Object obj) {
        Column column = field.getAnnotation(Column.class);
        Object value = items[column.index()];
        Adapter<String, ?> adapter = fieldAdapters.get(field);
        if (adapter != null) {
            value = adapter.fromString((String) value);
        }
        try {
            field.set(obj, value);
        } catch (Exception e) {
            throw new EqualException(e);
        }
    }
}
