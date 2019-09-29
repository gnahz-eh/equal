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

package com.github.jxcel.processor.adapter;

import com.github.jxcel.annotation.Column;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AdapterFactory {

    private static final Map<Class, Adapter> ADAPTER_MAP = new HashMap<>();

    static {
        ADAPTER_MAP.put(String.class, new StringAdapter());
    }

    public static Adapter getInstance(Field field) throws Exception {
        if (field == null) return null;
        Class fieldType = field.getType();
        Column column = field.getAnnotation(Column.class);

        if (null != column && !NullAdapter.class.equals(column.adapter())) {
            return column.adapter().newInstance();
        }
        return ADAPTER_MAP.get(fieldType);
    }
}
