package com.apache.android.bean;

import com.apache.android.download.IDownload;
import com.apache.android.download.IDownloadListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 01370340 on 2018/4/29.
 */

public class Student {
    private String name;
    private ArrayList<String> courses;


    public Student(String name, ArrayList<String> courses) {
        this.name = name;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   /* public ArrayList<String> getCourses() {
        return courses;
    }*/

    public List<String> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"").append(name).append('\"');
        sb.append(",\"courses\":").append(courses);
        sb.append('}');
        return sb.toString();
    }

    public static void main(String[] args) {
        ArrayList<String> courses = new ArrayList<>();
        courses.add("English");
        courses.add("Math");

        Student student = new Student("Tom",courses);
        //student.getCourses().add("语文");
        System.out.println(student);

        List list = new ArrayList();
        list.add(1);
        list.add("one");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));

        }
        List<String> stringList = new ArrayList<>();
        list.add(stringList);


        List<Object> objList = new ArrayList();
        objList.add(stringList);


        Object[] objArray = new Long[1];

        objArray[0] = "I don't fit in";
        Object[] elements = new Object[16];
        int size = 10;
        Arrays.copyOf(elements, 2 * size + 1);
        List<Apple> apple = new ArrayList<>();

        Set<String> set = new HashSet<>(Arrays.asList("tom","jerry"));
        String str = "";

    }


    class Cat implements Serializable,IDownload{

        @Override
        public void setListener(IDownloadListener listener) {

        }

        @Override
        public void startDownload(String url, String localPath, Object contextObject) {

        }
    }
}
