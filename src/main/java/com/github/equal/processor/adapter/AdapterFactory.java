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

package com.github.equal.processor.adapter;

import com.github.equal.annotation.Column;
import com.github.equal.exception.AdapterException;
import com.github.equal.utils.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AdapterFactory {

    private static final Map<Class<?>, Adapter<String, ?>> ADAPTER_MAP = new HashMap<>();

    static {
        ADAPTER_MAP.put(String.class, new StringAdapter());
        ADAPTER_MAP.put(Integer.class, new IntegerAdapter());
        ADAPTER_MAP.put(Byte.class, new ByteAdapter());
        ADAPTER_MAP.put(Short.class, new ShortAdapter());
        ADAPTER_MAP.put(Long.class, new LongAdapter());
        ADAPTER_MAP.put(Float.class, new FloatAdapter());
        ADAPTER_MAP.put(Double.class, new DoubleAdapter());
        ADAPTER_MAP.put(int.class, new IntegerAdapter());
        ADAPTER_MAP.put(byte.class, new ByteAdapter());
        ADAPTER_MAP.put(short.class, new ShortAdapter());
        ADAPTER_MAP.put(long.class, new LongAdapter());
        ADAPTER_MAP.put(float.class, new FloatAdapter());
        ADAPTER_MAP.put(double.class, new DoubleAdapter());
        ADAPTER_MAP.put(BigDecimal.class, new BigDecimalAdapter());
        ADAPTER_MAP.put(BigInteger.class, new BigIntegerAdapter());
        ADAPTER_MAP.put(Boolean.class, new BooleanAdapter());
        ADAPTER_MAP.put(LocalDate.class, new DateAdapter(StringUtils.DATE_PATTERN));
        ADAPTER_MAP.put(LocalTime.class, new TimeAdapter(StringUtils.TIME_PATTERN));
        ADAPTER_MAP.put(LocalDateTime.class, new DateTimeAdapter(StringUtils.DATE_TIME_PATTERN));
    }

    public static Adapter<String, ?> getInstance(Field field) throws AdapterException {
        if (field == null) return null;
        Class<?> fieldType = field.getType();
        Column column = field.getAnnotation(Column.class);

        if (fieldType.equals(LocalDate.class)) {
            return new DateAdapter(column.dateTimePattern());
        } else if (fieldType.equals(LocalTime.class)) {
            return new TimeAdapter(column.dateTimePattern());
        } else if (fieldType.equals(LocalDateTime.class)) {
            return new DateTimeAdapter(column.dateTimePattern());
        }
        try {
            if (!NullAdapter.class.equals(column.adapter())) {
                return column.adapter().newInstance();
            }
            return ADAPTER_MAP.get(fieldType);
        } catch (Exception e) {
            throw new AdapterException(e);
        }
    }
}
