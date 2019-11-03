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

package com.github.equal.bean;

import com.github.equal.annotation.Column;
import lombok.Data;

import java.util.Date;

@Data
public class Student3 {

    @Column(name = "name", index = 0)
    private String name;

    @Column(name = "sex", index = 1)
    private String sex;

    @Column(name="classNumber", index = 2)
    private int classNumber;

    @Column(name="hobby", index = 3)
    private String hobby;

    @Column(name = "birth", index = 4, datePattern = "yyyy/MM/dd HH:mm:ss")
    private Date birthDate;

    public Student3() {

    }

    public Student3(String name, String sex, int classNumber, String hobby, Date birthDate) {
        this.name = name;
        this.sex = sex;
        this.classNumber = classNumber;
        this.hobby = hobby;
        this.birthDate = birthDate;
    }
}