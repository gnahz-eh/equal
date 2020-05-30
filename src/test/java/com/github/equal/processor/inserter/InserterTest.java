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

    @Test
    void insertXLS_NON_EXIST_PKG() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/new/Student.xls"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertXLS_NON_EXIST_PKG2() throws EqualException {
        int rowStartIndex = 4;
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/new/Student2.xls"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertXLS_NON_EXIST_PKG3() throws EqualException {
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/new/Student3.xls"))
                .values(students)
                .range()
                .flush();
    }

    @Test
    void insertXLS_NON_EXIST_PKG4() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/new/Student4.xls"))
                .values(students)
                .range(rowStartIndex, 2)
                .flush();
    }

    @Test
    void insertXLS_NON_EXIST_PKG5() throws EqualException {
        int rowStartIndex = 1;
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/new/Student5.xls"))
                .values(students)
                .range(rowStartIndex, 2)
                .flush();
    }

    @Test
    void insertXLS_NON_EXIST_PKG6() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/new/Student6.xls"), "table1")
                .values(students)
                .range(rowStartIndex, 4)
                .flush();
    }

    @Test
    void insertXLS_EXIST_FILE() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/Student.xls"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertXLS_EXIST_FILE2() throws EqualException {
        int rowStartIndex = 13;
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/Student.xls"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertXLS_EXIST_FILE_WITH_TABLE_IDX() throws EqualException {
        int rowStartIndex = 26;
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/Student.xls"), 0)
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertXLS_EXIST_FILE_WITH_TABLE_NAME() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/Student.xls"), "Table2")
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertXLS_EXIST_FILE_WITH_TABLE_NAME2() throws EqualException {
        int rowStartIndex = 1;
        Inserter.insert(FileType.XLS)
                .into(new File(pkgName + "/Student.xls"), "Table4")
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

//|------------------------------------------------------------------------------------------------------------|

    @Test
    void insertXLSX_NON_EXIST_PKG() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.XLSX)
                .into(new File(pkgName + "/new/Student.xlsx"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertXLSX_NON_EXIST_PKG2() throws EqualException {
        int rowStartIndex = 4;
        Inserter.insert(FileType.XLSX)
                .into(new File(pkgName + "/new/Student2.xlsx"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertXLSX_NON_EXIST_PKG3() throws EqualException {
        Inserter.insert(FileType.XLSX)
                .into(new File(pkgName + "/new/Student3.xlsx"))
                .values(students)
                .range()
                .flush();
    }

    @Test
    void insertXLSX_NON_EXIST_PKG4() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.XLSX)
                .into(new File(pkgName + "/new/Student4.xlsx"))
                .values(students)
                .range(rowStartIndex, 2)
                .flush();
    }

    @Test
    void insertXLSX_NON_EXIST_PKG5() throws EqualException {
        int rowStartIndex = 1;
        Inserter.insert(FileType.XLSX)
                .into(new File(pkgName + "/new/Student5.xlsx"))
                .values(students)
                .range(rowStartIndex, 2)
                .flush();
    }

    @Test
    void insertXLSX_NON_EXIST_PKG6() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.XLSX)
                .into(new File(pkgName + "/new/Student6.xlsx"), "table1")
                .values(students)
                .range(rowStartIndex, 4)
                .flush();
    }

    @Test
    void insertXLSX_EXIST_FILE() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.XLSX)
                .into(new File(pkgName + "/Student.xlsx"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertXLSX_EXIST_FILE2() throws EqualException {
        int rowStartIndex = 13;
        Inserter.insert(FileType.XLSX)
                .into(new File(pkgName + "/Student.xlsx"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertXLSX_EXIST_FILE_WITH_TABLE_IDX() throws EqualException {
        int rowStartIndex = 36;
        Inserter.insert(FileType.XLSX)
                .into(new File(pkgName + "/Student.xlsx"), 0)
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertXLSX_EXIST_FILE_WITH_TABLE_NAME() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.XLSX)
                .into(new File(pkgName + "/Student.xlsx"), "Table2")
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertXLSX_EXIST_FILE_WITH_TABLE_NAME2() throws EqualException {
        int rowStartIndex = 1;
        Inserter.insert(FileType.XLSX)
                .into(new File(pkgName + "/Student.xlsx"), "Table4")
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

//|------------------------------------------------------------------------------------------------------------|

    @Test
    void insertCSV_NON_EXIST_PKG() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/new/Student.csv"))
                .values(students)
                .range(rowStartIndex)
                .flush();

        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/new/Student_1.csv"))
                .values(students)
                .range()
                .flush();

        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/new/Student_2.csv"))
                .values(students)
                .range(rowStartIndex, 2)
                .flush();

        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/new/Student_3.csv"))
                .values(students)
                .flush();
    }


    @Test
    void insertCSV_NON_EXIST_PKG2() throws EqualException {
        int rowStartIndex = 4;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/new/Student2.csv"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertCSV_NON_EXIST_PKG3() throws EqualException {
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/new/Student3.csv"))
                .values(students)
                .range()
                .flush();
    }

    @Test
    void insertCSV_NON_EXIST_PKG4() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/new/Student4.csv"))
                .values(students)
                .range(rowStartIndex, 3)
                .flush();
    }

    @Test
    void insertCSV_NON_EXIST_PKG5() throws EqualException {
        int rowStartIndex = 1;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/new/Student5.csv"))
                .values(students)
                .range(rowStartIndex, 2)
                .flush();
    }

    @Test
    void insertCSV_NON_EXIST_PKG6() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/new/Student6.csv"))
                .values(students)
                .range(rowStartIndex, 4)
                .flush();
    }

    @Test
    void insertCSV_NON_EXIST_PKG7() throws EqualException {
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/new/Student7.csv"))
                .values(students)
                .flush();
    }

    @Test
    void insertCSV_NON_EXIST_PKG8() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/new/Student8.csv"))
                .values(students)
                .range(rowStartIndex, 0)
                .flush();
    }

    @Test
    void insertCSV_NON_EXIST_PKG9() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/new/Student9.csv"))
                .values(students)
                .range(rowStartIndex, 100)
                .flush();
    }

//------------------------------------------------------------------------------

    @Test
    void insertCSV_EXIST_FILE() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/Student.csv"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertCSV_EXIST_FILE2() throws EqualException {
        int rowStartIndex = 3;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/Student.csv"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertCSV_EXIST_FILE3() throws EqualException {
        int rowStartIndex = 5;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/Student.csv"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertCSV_EXIST_FILE4() throws EqualException {
        int rowStartIndex = 6;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/Student.csv"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertCSV_EXIST_FILE7() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/Student2.csv"))
                .values(students)
                .range(rowStartIndex, 2)
                .flush();
    }

    @Test
    void insertCSV_EXIST_FILE8() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/Student2.csv"))
                .values(students)
                .range(rowStartIndex, 3)
                .flush();
    }

    @Test
    void insertCSV_EXIST_FILE9() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/Student2.csv"))
                .values(students)
                .range(rowStartIndex, 5)
                .flush();
    }

    @Test
    void insertCSV_EXIST_FILE10() throws EqualException {
        int rowStartIndex = 2;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/Student2.csv"))
                .values(students)
                .range(rowStartIndex, 8)
                .flush();
    }

    @Test
    void insertCSV_EXIST_FILE11() throws EqualException {
        int rowStartIndex = 9;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/Student2.csv"))
                .values(students)
                .range(rowStartIndex, 2)
                .flush();
    }

    @Test
    void insertCSV_EXIST_FILE12() throws EqualException {
        int rowStartIndex = 11;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/Student2.csv"))
                .values(students)
                .range(rowStartIndex, 2)
                .flush();
    }

    @Test
    void insertCSV_EXIST_FILE13() throws EqualException {
        int rowStartIndex = 15;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/Student2.csv"))
                .values(students)
                .range(rowStartIndex)
                .flush();
    }

    @Test
    void insertCSV_EXIST_FILE14() throws EqualException {
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/Student2.csv"))
                .values(students)
                .flush();
    }

    @Test
    void insertCSV_EXIST_FILE15() throws EqualException {
        int rowStartIndex = 9;
        Inserter.insert(FileType.CSV)
                .into(new File(pkgName + "/Student2.csv"))
                .values(students)
                .range(rowStartIndex, 0)
                .flush();
    }

    public static void main(String[] args) {
        FunctionUtils.delFile(new File(pkgName + "/new"));
    }
}