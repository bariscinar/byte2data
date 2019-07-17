package net.byte2data.tutorial.tutorials.learningthejavalanguage.classesandobjects;

/**
 * Created by barisci on 08.09.2017.
 */
public class Test {

    private String name;
    private int id;

    public Test(String name, int id){
        this.id=id;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void checkItOut(String par1, String par2){
        StringBuffer sb = new StringBuffer(par1);
        par1.concat(par2);
        System.out.println("param1:"+par1);
    }

    public void play(Test test){
        test.name="play";
        test.id=2;
        test=null;
    }

    public static void main(String... args){

        String[] students = new String[5];
        String studentName = "Barış Çınar";
        students[0] = studentName;
        studentName = "büş";
        System.out.println(students[0]);

        int x=12;
        int y=x;
        x=15;
        System.out.println(y);

        Test test = new Test("test1",1);
        test.play(test);
        System.out.println(test.getName());
        System.out.println(test.getId());

        test.checkItOut(studentName,"ra");
        System.out.println("studentName:"+studentName);

    }

}
