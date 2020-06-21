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

package com.github.equal.processor.adapter;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdapterTest {

    @Test
    void bigDecimalFromStringTest() {
        BigDecimalAdapter adapter = new BigDecimalAdapter();
        String str = "23,431.1234445677894";
        BigDecimal res = adapter.fromString(str);
        String adaptedStr = res.toString();
        str = adapter.cleanComma(str);
        assertEquals(adaptedStr, str);
    }

    @Test
    void bigIntegerFromStringTest() {
        BigIntegerAdapter bigIntegerAdapter = new BigIntegerAdapter();
        String str = "23,431";

        BigInteger res = bigIntegerAdapter.fromString(str);
        String adaptedStr = res.toString();
        str = bigIntegerAdapter.cleanComma(str);
        assertEquals(adaptedStr, str);
    }

    @Test
    void booleanFromStringTest() {
        BooleanAdapter adapter = new BooleanAdapter();
        String str = "TRUE";
        Boolean res = adapter.fromString(str);
        assertEquals(res.toString(), str.toLowerCase());
    }

    @Test
    void byteFromStringTest() {
        ByteAdapter adapter = new ByteAdapter();
        String str = "23";
        Byte res = adapter.fromString(str);
        String adaptedStr = res.toString();
        assertEquals(adaptedStr, str);
    }

    @Test
    void fromStringYMD() {
        String datePattern = "yyyy/MM/dd";
        String date = "2019/02/01";
        DateAdapter dateAdapter = new DateAdapter(datePattern);
        LocalDate localDate = dateAdapter.fromString(date);

        assertEquals(localDate.getYear(), 2019);
        assertEquals(localDate.getMonth(), Month.FEBRUARY);
        assertEquals(localDate.getDayOfMonth(), 1);

        datePattern = "yyyy.MM.dd";
        date = "2019.02.01";
        dateAdapter = new DateAdapter(datePattern);
        localDate = dateAdapter.fromString(date);

        assertEquals(localDate.getYear(), 2019);
        assertEquals(localDate.getMonth(), Month.FEBRUARY);
        assertEquals(localDate.getDayOfMonth(), 1);
    }

    @Test
    void fromStringYM() {
        String datePattern = "yyyy/MM";
        String date = "2019/02";
        DateAdapter dateAdapter = new DateAdapter(datePattern);
        LocalDate localDate = dateAdapter.fromString(date);

        assertEquals(localDate.getYear(), 2019);
        assertEquals(localDate.getMonth(), Month.FEBRUARY);
        assertEquals(localDate.getDayOfMonth(), 1);

        datePattern = "yyyy.MM";
        date = "2019.02";
        dateAdapter = new DateAdapter(datePattern);
        localDate = dateAdapter.fromString(date);

        assertEquals(localDate.getYear(), 2019);
        assertEquals(localDate.getMonth(), Month.FEBRUARY);
        assertEquals(localDate.getDayOfMonth(), 1);
    }

    @Test
    void fromStringY() {
        String datePattern = "yyyy";
        String date = "2019";
        DateAdapter dateAdapter = new DateAdapter(datePattern);
        LocalDate localDate = dateAdapter.fromString(date);

        assertEquals(localDate.getYear(), 2019);
        assertEquals(localDate.getMonth(), Month.JANUARY);
        assertEquals(localDate.getDayOfMonth(), 1);

        datePattern = "yyyy";
        date = "2019";
        dateAdapter = new DateAdapter(datePattern);
        localDate = dateAdapter.fromString(date);

        assertEquals(localDate.getYear(), 2019);
        assertEquals(localDate.getMonth(), Month.JANUARY);
        assertEquals(localDate.getDayOfMonth(), 1);
    }

    @Test
    void fromStringYMD_HMS() {
        String dateTimePattern = "yyyy/MM/dd HH:mm:ss";
        String dateTime = "2019/02/01 14:05:03";
        DateTimeAdapter dateTimeAdapter = new DateTimeAdapter(dateTimePattern);
        LocalDateTime localDateTime = dateTimeAdapter.fromString(dateTime);

        assertEquals(localDateTime.getYear(), 2019);
        assertEquals(localDateTime.getMonth(), Month.FEBRUARY);
        assertEquals(localDateTime.getDayOfMonth(), 1);
        assertEquals(localDateTime.getHour(), 14);
        assertEquals(localDateTime.getMinute(), 5);
        assertEquals(localDateTime.getSecond(), 3);
    }

    @Test
    void fromStringYMD_HM() {
        String dateTimePattern = "yyyy/MM/dd HH:mm";
        String dateTime = "2019/02/01 14:05";
        DateTimeAdapter dateTimeAdapter = new DateTimeAdapter(dateTimePattern);
        LocalDateTime localDateTime = dateTimeAdapter.fromString(dateTime);

        assertEquals(localDateTime.getYear(), 2019);
        assertEquals(localDateTime.getMonth(), Month.FEBRUARY);
        assertEquals(localDateTime.getDayOfMonth(), 1);
        assertEquals(localDateTime.getHour(), 14);
        assertEquals(localDateTime.getMinute(), 5);
        assertEquals(localDateTime.getSecond(), 0);
    }

    @Test
    void fromStringYMD_H() {
        String dateTimePattern = "yyyy/MM/dd HH";
        String dateTime = "2019/02/01 14";
        DateTimeAdapter dateTimeAdapter = new DateTimeAdapter(dateTimePattern);
        LocalDateTime localDateTime = dateTimeAdapter.fromString(dateTime);

        assertEquals(localDateTime.getYear(), 2019);
        assertEquals(localDateTime.getMonth(), Month.FEBRUARY);
        assertEquals(localDateTime.getDayOfMonth(), 1);
        assertEquals(localDateTime.getHour(), 14);
        assertEquals(localDateTime.getMinute(), 0);
        assertEquals(localDateTime.getSecond(), 0);
    }

    @Test
    void doubleFromStringTest() {
        DoubleAdapter adapter = new DoubleAdapter();
        String str = "23,431.0";
        Double res = adapter.fromString(str);
        String adaptedStr = res.toString();
        str = adapter.cleanComma(str);
        assertEquals(adapter.cleanComma(adaptedStr), str);
    }

    @Test
    void fromStringHMS() {
        String timePattern = "HH:mm:ss";
        String time = "13:02:04";
        TimeAdapter timeAdapter = new TimeAdapter(timePattern);
        LocalTime localTime = timeAdapter.fromString(time);

        assertEquals(localTime.getHour(), 13);
        assertEquals(localTime.getMinute(), 2);
        assertEquals(localTime.getSecond(), 4);
    }

    @Test
    void fromStringHM() {
        String timePattern = "HH:mm";
        String time = "13:02";
        TimeAdapter timeAdapter = new TimeAdapter(timePattern);
        LocalTime localTime = timeAdapter.fromString(time);

        assertEquals(localTime.getHour(), 13);
        assertEquals(localTime.getMinute(), 2);
        assertEquals(localTime.getSecond(), 0);
    }

    @Test
    void fromStringH() {
        String timePattern = "HH";
        String time = "13";
        TimeAdapter timeAdapter = new TimeAdapter(timePattern);
        LocalTime localTime = timeAdapter.fromString(time);

        assertEquals(localTime.getHour(), 13);
        assertEquals(localTime.getMinute(), 0);
        assertEquals(localTime.getSecond(), 0);
    }

    @Test
    void floatFromStringTest() {
        FloatAdapter adapter = new FloatAdapter();
        String str = "23,431.0";
        Float res = adapter.fromString(str);
        String adaptedStr = res.toString();
        str = adapter.cleanComma(str);
        assertEquals(adapter.cleanComma(adaptedStr), str);
    }

    @Test
    void integerFromStringTest() {
        IntegerAdapter adapter = new IntegerAdapter();
        String str = "23,431";
        Integer res = adapter.fromString(str);
        str = adapter.cleanComma(str);
        assertEquals(res.toString(), str);

    }

    @Test
    void longFromStringTest() {
        LongAdapter adapter = new LongAdapter();
        String str = "23,431";
        Long res = adapter.fromString(str);
        str = adapter.cleanComma(str);
        assertEquals(res.toString(), str);

    }

    @Test
    void nullFromStringTest() {
        NullAdapter adapter = new NullAdapter();
        String str = "null";
        Object res = adapter.fromString(str);
        assertEquals(res, null);

    }

    @Test
    void shortFromStringTest() {
        ShortAdapter adapter = new ShortAdapter();
        String str = "12";
        Short res = adapter.fromString(str);
        assertEquals(res.toString(), str);

    }

    @Test
    void stringFromStringTest() {
        String str = "abc";
        StringAdapter adapter = new StringAdapter();
        assertEquals(adapter.fromString(str), str);
    }
}
