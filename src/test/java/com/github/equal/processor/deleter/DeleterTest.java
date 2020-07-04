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

package com.github.equal.processor.deleter;

import com.github.equal.bean.Student;
import com.github.equal.enums.ExceptionType;
import com.github.equal.exception.EqualException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeleterTest {

    public static String pkgName = "src/test/resources/delete_pkg";

//---------------------------------------------------------------------------------------------

    @Test
    void deleteXLS_FILE() throws EqualException {
        int rowStartIndex = 14;
        Deleter.delete(Student.class)
                .from(new File(pkgName + "/Student.xls"))
                .where(rowStartIndex, 1)
                .execute();
    }

    @Test
    void deleteXLS_FILE2() throws EqualException {
        int rowStartIndex = 15;
        Deleter.delete(Student.class)
                .from(new File(pkgName + "/Student.xls"))
                .where(rowStartIndex, 10)
                .execute();
    }

    @Test
    void deleteXLS_FILE3() throws EqualException {
        int rowStartIndex = 11;
        Deleter.delete(Student.class)
                .from(new File(pkgName + "/Student.xls"))
                .where(rowStartIndex, 3)
                .execute();
    }

    @Test
    void deleteXLS_FILE4() throws EqualException {
        int rowStartIndex = 12;
        Deleter.delete(Student.class)
                .from(new File(pkgName + "/Student.xls"))
                .where(rowStartIndex)
                .execute();
    }

    @Test
    void deleteXLS_FILE5() throws EqualException {
        int rowStartIndex = 17;
        Deleter.delete(Student.class)
                .from(new File(pkgName + "/Student.xls"))
                .where(rowStartIndex, 2)
                .execute();
    }

    @Test
    void deleteXLS_FILE6() throws EqualException {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            int rowStartIndex = 1;
            Deleter.delete(Student.class)
                    .from(new File(pkgName + "/Student.xls"))
                    .where(rowStartIndex, 2)
                    .execute();
        });

        assertEquals(exception.getMessage(), ExceptionType.ROW_START_INDEX_IS_LESS_THAN_2.getExceptionMessage());
    }

    @Test
    void deleteXLS_FILE7() throws EqualException {

        Set<Integer> indexes = Stream.of(-1, 0, 4, 2, 19, 6).collect(Collectors.toSet());

        Deleter.delete(Student.class)
                .from(new File(pkgName + "/Student.xls"))
                .where(indexes)
                .execute();
    }

    @Test
    void deleteXLS_FILE8() throws EqualException {

        Set<Integer> indexes = Stream.of(-1, 0, 4, 2, 19, 6).collect(Collectors.toSet());

        Deleter.delete(Student.class)
                .from(new File(pkgName + "/Student.xls"), "Table4")
                .where(indexes)
                .execute();
    }

    @Test
    void deleteXLS_FILE9() throws EqualException {

        Set<Integer> indexes = Stream.of(-1, 0, 4, 2, 19, 6).collect(Collectors.toSet());

        Deleter.delete(Student.class)
                .from(new File(pkgName + "/Student.xls"), 1)
                .where(indexes)
                .execute();
    }

    @Test
    void deleteXLS_FILE_NON_EXIST() throws EqualException {

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            int rowStartIndex = 14;
            Deleter.delete(Student.class)
                    .from(new File(pkgName + "/AAA.xls"))
                    .where(rowStartIndex, 1)
                    .execute();
        });

        assertEquals(exception.getMessage(), ExceptionType.SOURCE_FILE_DOES_NOT_EXIST.getExceptionMessage());
    }


//---------------------------------------------------------------------------------------------

    @Test
    void deleteXLSX_FILE() throws EqualException {
        int rowStartIndex = 14;
        Deleter.delete(Student.class)
                .from(new File(pkgName + "/Student.xlsx"))
                .where(rowStartIndex, 1)
                .execute();
    }

    @Test
    void deleteXLSX_FILE2() throws EqualException {
        int rowStartIndex = 15;
        Deleter.delete(Student.class)
                .from(new File(pkgName + "/Student.xlsx"))
                .where(rowStartIndex, 10)
                .execute();
    }

    @Test
    void deleteXLSX_FILE3() throws EqualException {
        int rowStartIndex = 14;
        Deleter.delete(Student.class)
                .from(new File(pkgName + "/Student.xlsx"))
                .where(rowStartIndex, 3)
                .execute();
    }

    @Test
    void deleteXLSX_FILE4() throws EqualException {
        int rowStartIndex = 12;
        Deleter.delete(Student.class)
                .from(new File(pkgName + "/Student.xlsx"))
                .where(rowStartIndex)
                .execute();
    }

    @Test
    void deleteXLSX_FILE5() throws EqualException {
        int rowStartIndex = 47;
        Deleter.delete(Student.class)
                .from(new File(pkgName + "/Student.xlsx"))
                .where(rowStartIndex, 2)
                .execute();
    }

    @Test
    void deleteXLSX_FILE6() throws EqualException {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            int rowStartIndex = 1;
            Deleter.delete(Student.class)
                    .from(new File(pkgName + "/Student.xlsx"))
                    .where(rowStartIndex, 2)
                    .execute();
        });

        assertEquals(exception.getMessage(), ExceptionType.ROW_START_INDEX_IS_LESS_THAN_2.getExceptionMessage());
    }

    @Test
    void deleteXLSX_FILE7() throws EqualException {

        Set<Integer> indexes = Stream.of(-1, 0, 4, 2, 19, 6, 100).collect(Collectors.toSet());

        Deleter.delete(Student.class)
                .from(new File(pkgName + "/Student.xlsx"))
                .where(indexes)
                .execute();
    }

    @Test
    void deleteXLSX_FILE8() throws EqualException {

        Set<Integer> indexes = Stream.of(-1, 0, 4, 2, 19, 6).collect(Collectors.toSet());

        Deleter.delete(Student.class)
                .from(new File(pkgName + "/Student.xlsx"), "Table4")
                .where(indexes)
                .execute();
    }

    @Test
    void deleteXLSX_FILE9() throws EqualException {

        Set<Integer> indexes = Stream.of(-1, 0, 4, 2, 19, 6).collect(Collectors.toSet());

        Deleter.delete(Student.class)
                .from(new File(pkgName + "/Student.xlsx"), 1)
                .where(indexes)
                .execute();
    }

    @Test
    void deleteXLSX_FILE_NON_EXIST() throws EqualException {

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            int rowStartIndex = 14;
            Deleter.delete(Student.class)
                    .from(new File(pkgName + "/AAA.xlsx"))
                    .where(rowStartIndex, 1)
                    .execute();
        });

        assertEquals(exception.getMessage(), ExceptionType.SOURCE_FILE_DOES_NOT_EXIST.getExceptionMessage());
    }

}