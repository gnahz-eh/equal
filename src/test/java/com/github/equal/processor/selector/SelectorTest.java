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

import com.github.equal.bean.Student;
import com.github.equal.bean.Student2;
import com.github.equal.bean.Student3;
import com.github.equal.bean.Student4;
import com.github.equal.exception.EqualException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SelectorTest {

    @Test
    void selectXLS() throws EqualException {
        int rowStartIndex = 4;
        int numOfRows = 2;
        List<Student> students = Selector
                                .select(Student.class)
                                .from(new File("src/test/resources/Student.xls"))
                                .where(rowStartIndex, numOfRows)
                                .executeQuery();
        assertEquals(students.size(), numOfRows);
    }

    @Test
    void selectCSV() throws EqualException {
        int rowStartIndex = 2;
        int numOfRows = 5;
        List<Student> students = Selector
                .select(Student.class)
                .from(new File("src/test/resources/Student2.csv"))
                .where(rowStartIndex, numOfRows)
                .executeQuery();
        assertEquals(students.size(), numOfRows);
    }

    @Test
    void selectXLSX() throws EqualException {
        int rowStartIndex = 2;
        int numOfRows = 2;
        List<Student> students = Selector
                .select(Student.class)
                .from(new File("src/test/resources/Student.xlsx"))
                .where(rowStartIndex, numOfRows)
                .executeQuery();
        assertEquals(students.size(), numOfRows);
    }

    @Test
    void selectCSVWithDatePattern() throws EqualException {
        int rowStartIndex = 2;
        int numOfRows = 5;
        List<Student2> students = Selector
                .select(Student2.class)
                .from(new File("src/test/resources/Student2.csv"))
                .where(rowStartIndex, numOfRows)
                .executeQuery();
        assertEquals(students.size(), numOfRows);
    }

    @Test
    void selectXLSWithDatePattern() throws EqualException {
        int rowStartIndex = 2;
        int numOfRows = 5;
        List<Student2> students = Selector
                .select(Student2.class)
                .from(new File("src/test/resources/Student2.xls"))
                .where(rowStartIndex, numOfRows)
                .executeQuery();
        assertEquals(students.size(), numOfRows);
    }

    @Test
    void selectXLSXWithDatePattern() throws EqualException {
        int rowStartIndex = 2;
        int numOfRows = 5;
        List<Student2> students = Selector
                .select(Student2.class)
                .from(new File("src/test/resources/Student2.xlsx"))
                .where(rowStartIndex, numOfRows)
                .executeQuery();
        assertEquals(students.size(), numOfRows);
    }

    @Test
    void selectCSVWithTimePattern() throws EqualException {
        int rowStartIndex = 2;
        int numOfRows = 5;
        List<Student4> students = Selector
                .select(Student4.class)
                .from(new File("src/test/resources/Student4.csv"))
                .where(rowStartIndex, numOfRows)
                .executeQuery();
        assertEquals(students.size(), numOfRows);
    }

    @Test
    void selectXLSWithTimePattern() throws EqualException {
        int rowStartIndex = 2;
        int numOfRows = 5;
        List<Student4> students = Selector
                .select(Student4.class)
                .from(new File("src/test/resources/Student4.xls"))
                .where(rowStartIndex, numOfRows)
                .executeQuery();
        assertEquals(students.size(), numOfRows);
    }

    @Test
    void selectXLSXWithTimePattern() throws EqualException {
        int rowStartIndex = 2;
        int numOfRows = 5;
        List<Student4> students = Selector
                .select(Student4.class)
                .from(new File("src/test/resources/Student4.xlsx"))
                .where(rowStartIndex, numOfRows)
                .executeQuery();
        assertEquals(students.size(), numOfRows);
    }

//    @Test
//    void selectCSVWithDateAndTimePattern() throws EqualException {
//        int rowStartIndex = 2;
//        int numOfRows = 5;
//        List<Student3> students = Selector
//                .select(Student3.class)
//                .from(new File("src/test/resources/Student3.csv"))
//                .where(rowStartIndex, numOfRows)
//                .executeQuery();
//        assertEquals(students.size(), numOfRows);
//    }
//
//    @Test
//    void selectXLSWithDateAndTimePattern() throws EqualException {
//        int rowStartIndex = 2;
//        int numOfRows = 5;
//        List<Student3> students = Selector
//                .select(Student3.class)
//                .from(new File("src/test/resources/Student3.xls"))
//                .where(rowStartIndex, numOfRows)
//                .executeQuery();
//        assertEquals(students.size(), numOfRows);
//    }
//
//    @Test
//    void selectXLSXWithDateAndTimePattern() throws EqualException {
//        int rowStartIndex = 2;
//        int numOfRows = 5;
//        List<Student3> students = Selector
//                .select(Student3.class)
//                .from(new File("src/test/resources/Student3.xlsx"))
//                .where(rowStartIndex, numOfRows)
//                .executeQuery();
//        assertEquals(students.size(), numOfRows);
//    }
}