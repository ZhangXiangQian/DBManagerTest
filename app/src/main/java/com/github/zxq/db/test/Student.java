package com.github.zxq.db.test;

import org.android.zxq.db.annotation.Column;
import org.android.zxq.db.annotation.Table;

/**
 * Created by zhang on 2016/7/7.
 */
@Table(name = "Student")
public class Student {
    @Column(name = "name")
    private String name;
    @Column(name = "age",isId = true)
    private String age;
    @Column(name = "sex")
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
