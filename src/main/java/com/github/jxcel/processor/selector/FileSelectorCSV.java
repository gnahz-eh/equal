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

package com.github.jxcel.processor.selector;

import com.github.jxcel.annotation.Column;
import com.github.jxcel.exception.JxcelException;
import com.github.jxcel.processor.adapter.Adapter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.stream.Stream;

public class FileSelectorCSV extends FileSelector {

    private InputStream inputStream;

    public FileSelectorCSV(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public <T> Stream<T> selectFromFile(Selector selector) throws JxcelException {
        Stream.Builder<T> builder = Stream.builder();
        Class clazz = selector.getClazz();
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, selector.getCharset()))) {

            init(clazz.getDeclaredFields());
            int rowStartIndex = selector.getRowStartIndex();
            int numberOfRows = selector.getNumberOfRows();
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                if (rowStartIndex-- > 1) {
                    continue;
                }
                Object obj = clazz.newInstance();
                String[] items = line.split(",");
                for (Field field : fieldIndexes.values()) {
                    setField(field, items, obj);
                }
                builder.add((T) obj);
                numberOfRows--;
                if (numberOfRows == 0) break;
            }
            return builder.build();
        } catch (Exception e) {
            throw new JxcelException(e);
        }
    }

    public void setField(Field field, String[] items, Object obj) throws JxcelException {
        Column column = field.getAnnotation(Column.class);
        try {
            Object value = items[column.index()];
            Adapter<String, ?> adapter = fieldAdapters.get(field);
            if (adapter != null) {
                value = adapter.fromString((String) value);
            }
            field.set(obj, value);
        } catch (Exception e) {
            throw new JxcelException(e);
        }
    }
}
