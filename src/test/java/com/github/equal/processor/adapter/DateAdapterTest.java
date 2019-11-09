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
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class DateAdapterTest {

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
}