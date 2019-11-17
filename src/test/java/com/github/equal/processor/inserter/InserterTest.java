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

package com.github.equal.processor.inserter;

import com.github.equal.FunctionUtils;
import com.github.equal.bean.Student;
import com.github.equal.enums.FileType;
import com.github.equal.exception.EqualException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InserterTest {

    List<Student> students;
    int numOfRows = 10;
    public static String pkgName = "src/test/resources/insert_pkg";

    @BeforeEach
    void init() {
        students = new ArrayList<>();
        for (int i = 0; i < numOfRows; i++) {
            Student student = new Student();
            student.setName("joe:" + System.currentTimeMillis());
            student.setHobby("hobby");
            student.setSex("man");
            student.setClassNumber(i);
            student.setBirthDate(LocalDate.now());

            students.add(student);
        }
        File file = new File(pkgName);
        file.mkdir();
    }

    // todo
    @Test
    void insertXLS_NON_EXIST_PKG() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/new/Student.xls"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    // todo
    @Test
    void insertXLS_NON_EXIST_PKG2() throws EqualException {
        int rowStartIndex = 4;
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/new/Student2.xls"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    // todo
    @Test
    void insertXLS_NON_EXIST_PKG3() throws EqualException {
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/new/Student3.xls"))
                .values(students)
                .range()
                .flush();
    }

    // todo
    @Test
    void insertXLS_NON_EXIST_PKG4() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/new/Student4.xls"))
                .values(students)
                .range(rowStartIndex, 2)
                .flush();
    }

    // todo
    @Test
    void insertXLS_NON_EXIST_PKG5() throws EqualException {
        int rowStartIndex = 1;
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/new/Student5.xls"))
                .values(students)
                .range(rowStartIndex, 2)
                .flush();
    }

    // todo
    @Test
    void insertXLS_NON_EXIST_PKG6() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/new/Student6.xls"), "table1")
                .values(students)
                .range(rowStartIndex, 4)
                .flush();
    }

    // todo
    @Test
    void insertXLS_EXIST_FILE() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/Student.xls"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    // todo
    @Test
    void insertXLS_EXIST_FILE2() throws EqualException {
        int rowStartIndex = 13;
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/Student.xls"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

//    @Test
//    void insertXLS_EXIST_FILE_WITH_TABLE_IDX() throws EqualException {
//        int rowStartIndex = 2;
//        Inserter.insert(FileType.XLS)
//                .into(new File(pkgName + "/new/Student2.xls"), 0)
//                .values(students)
//                .range(rowStartIndex)
//                .flush();
//    }


    public static void main(String[] args) {
        FunctionUtils.delFile(new File(pkgName + "/new"));
    }
}