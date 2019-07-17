package net.byte2data.tutorial.tutorials.essentialjavaclasses.exceptions.school;

/**
 * Created by barisci on 25.09.2017.
 */
public enum Lesson {
    ENGLISH(376), MATH(895), PHYSICS(423), CHEMISTRY(547), BILOGY(779);
    public int lessonId;
    Lesson(int val){
        this.lessonId=val;
    }
}
