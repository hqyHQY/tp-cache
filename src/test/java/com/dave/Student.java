package com.dave;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Auther: hequnayu
 * @Date: 2019/4/10 15:23
 * @Description:
 */
@Data
public class Student {

    private String name;

    private Integer age;

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Student() {
    }

    public static final String LOANMEMBER_PUSH_ORDER = "loan_member_push_order:%s";

    public static void main(String[] args) {
//        List<Student> list = new ArrayList<Student>();
//        Student student1 = new Student("a",7);
//        Student student2 = new Student("b",5);
//        Student student3 = new Student("c",null);
//        Student student4 = new Student();
////        System.out.println(student4.getAge() > 9);
//        Integer i = 10;
//        if(10 < student3.getAge()){
//            System.out.println("ssssssssssssssssssssssssssss");
//        }
//        list.add(student1);
//        list.add(student2);
//        list.add(student3);
//        List<Student> collect1 = list.stream().limit(2).collect(Collectors.toList());
//        System.out.println(collect1.size());
//        List<Student> collect = list.stream().sorted(Comparator.comparing(Student::getAge).reversed()).collect(Collectors.toList());
//        for (Student s :collect) {
//            System.out.println(s.getAge());
//        }
//
        List<String> list2 = new ArrayList<String>();
        list2.add("1");
        list2.add("2");
        list2.add("3");
//        System.out.println(JSON.toJSONString(list2));
//
        List<String> strings = JSON.parseArray(JSON.toJSONString(list2), String.class);
        System.out.println(strings.contains("8000"));
        System.out.println(LOANMEMBER_PUSH_ORDER);
        System.out.println(String.format(LOANMEMBER_PUSH_ORDER,""));

        System.out.println("loan_member_push_order:081904121042176386327test".startsWith(String.format(LOANMEMBER_PUSH_ORDER,"")));

        String title = "";
        try{
//            int i = 1/0;
            title = "正确值";
        }catch (Exception e){
            title = "错误复制";
        }

        System.out.println(title);
    }
}
