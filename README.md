# EQUAL

[![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg)]()
[![Build Status](https://github.com/gnahZ-eH/equal/workflows/Java%20CI/badge.svg)](https://github.com/gnahZ-eH/equal/actions)

**READING <font color='green' size='5'>E</font>XCEL(CSV) FILES IN A S<font color='green' size='5'>Q</font>L- STYLE AND RET<font color='green' size='5'>U</font>RN <font color='green' size='5'>A</font>N OBJECT <font color='green' size='5'>L</font>IST AS RESULT.**

## What is EQUAL ?
EQUAL is a small lib that can operate excel files in a sql-style. It includes the basic operations like read, write, update and delete. And for the file title, it follows this rule:

Source File              | Column Name
-------------------------|-----------------------
Source File Exist        | Not Insert Column Name
Source File Do Not Exist | Insert Column Name

## How to use ?

## Examples
We need a bean class which is a model of excel or csv files. For example, if we want to operate an csv file that includes students message, we need a Student class with some annotations, the index should start from 0 and should match the sort in excel/csv files. Like:
```java
public class Student {

    @Column(name = "name", index = 0)
    private String name;

    @Column(name = "sex", index = 1)
    private String sex;

    @Column(name="classNumber", index = 2)
    private int classNumber;

    @Column(name="hobby", index = 3)
    private String hobby;

    @Column(name = "birth", index = 4)
    private LocalDate birthDate;

    public Student() { }

    public Student(String name, String sex, int classNumber, String hobby, LocalDate birthDate) {
        this.name = name;
        this.sex = sex;
        this.classNumber = classNumber;
        this.hobby = hobby;
        this.birthDate = birthDate;
    }
}
```
The CSV file like:
```csv
name,sex,classNumber,hobby,birth
a,male,3965,reading,2019/01/01
b,male,5330,hiking,2019/01/02
c,male,338,sports,2019/01/03
d,male,8102,play,2019/01/04
e,famale,87,others,2019/01/05
f,famale,94,others,2019/01/06
g,famale,784,others,2019/01/07
h,famale,176,others,2019/01/08
```

### READ
```java
List<Student> students = Selector
      .select(Student.class)
      .from(new File(pkgName + "/Student2.csv"))
      .where()
      .executeQuery();
```
