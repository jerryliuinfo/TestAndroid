package com.apache.android;

/**
 * Created by 01370340 on 2018/1/25.
 */

public class Student {
    int sno;
    String name;



    Student(int sno,String name)
    {
        this.sno = sno;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (sno != student.sno) return false;
        return name != null ? name.equals(student.name) : student.name == null;

    }

   /* @Override
    public int hashCode() {
        int result = sno;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }*/
}
