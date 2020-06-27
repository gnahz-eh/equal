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

import com.github.equal.enums.ExceptionType;
import com.github.equal.exception.AdapterException;
import com.github.equal.utils.StringUtils;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateAdapter implements Adapter<String, LocalDate> {

    private final DateTimeFormatter dateTimeFormatter;

    public DateAdapter(String datePattern) {
        if (StringUtils.isNotEmpty(datePattern)) {
            this.dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        } else {
            this.dateTimeFormatter = DateTimeFormatter.ofPattern(StringUtils.DATE_PATTERN);
        }
    }

    @Override
    public LocalDate fromString(String str) throws AdapterException {
        try {
            if (str == null) {
                return null;
            }
            return LocalDate.parse(str, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            try {
                YearMonth yearMonth = YearMonth.parse(str, dateTimeFormatter);
                return yearMonth.atDay(1);
            } catch (DateTimeParseException e1) {
                try {
                    Year year = Year.parse(str, dateTimeFormatter);
                    return year.atMonth(Month.JANUARY).atDay(1);
                } catch (DateTimeParseException e2) {
                    throw new AdapterException(ExceptionType.ADAPT_DATE_ERROR, e2.getMessage());
                }
            }
        } catch (AdapterException e) {
            throw new AdapterException(e);
        }
    }

    @Override
    public String toString(Object date) throws AdapterException {
        if (date == null) {
            return null;
        }
        return ((LocalDate) date).format(dateTimeFormatter);
    }
}
