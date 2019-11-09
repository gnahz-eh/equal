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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeAdapterTest {

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
}