package net.byte2data.tutorial.tutorials.essentialjavaclasses.exceptions.school;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by barisci on 25.09.2017.
 */
public class Teacher {

    private static List<Teacher> teachers;
    private Teacher teacher;
    private int teacherId;
    private String teacherName;
    private Lesson[] fluentLessons;

    public Teacher(int id, String name, Lesson... lessons){
        this.fluentLessons=lessons;
        this.teacherName=name;
        this.teacherId=id;
        this.teacher=this;
    }

    private static Lesson[] getRandomLessons(){
        Lesson[] lessons = null;
        int randomVal = (int)(10  * Math.random());
        switch (randomVal){
            case 0:
                lessons= new Lesson[]{Lesson.BILOGY,Lesson.CHEMISTRY};
                break;
            case 1:
                lessons= new Lesson[]{Lesson.MATH,Lesson.PHYSICS};
                break;
            case 2:
                lessons= new Lesson[]{Lesson.MATH,Lesson.PHYSICS,Lesson.ENGLISH};
                break;
            case 3:
                lessons= new Lesson[]{Lesson.CHEMISTRY,Lesson.PHYSICS,Lesson.ENGLISH,Lesson.BILOGY};
                break;
            default:
                lessons=new Lesson[]{Lesson.MATH};
        }
        return lessons;
    }

    public static List<Teacher> generateTeachers(int teacherCount){
        Teacher teacher = null;
        teachers = new ArrayList<>();
        for(int index=1; index<=teacherCount; index++){
            teacher=new Teacher(index,"TEACHER".concat(String.valueOf(index)),getRandomLessons());
            teachers.add(teacher);
        }
        return teachers;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Lesson[] getFluentLessons() {
        return fluentLessons;
    }

    public void setFluentLessons(Lesson[] fluentLessons) {
        this.fluentLessons = fluentLessons;
    }

    @Override
    public String toString() {
        String t = "TEACHER -> {" +
                "teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", fluentLessons=" + Arrays.toString(fluentLessons) +
                '}';
        System.out.println(t);
        return t;
    }
}
