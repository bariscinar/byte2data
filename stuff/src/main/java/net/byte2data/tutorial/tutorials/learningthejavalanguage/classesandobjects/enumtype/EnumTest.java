package net.byte2data.tutorial.tutorials.learningthejavalanguage.classesandobjects.enumtype;

/**
 * Created by barisci on 14.09.2017.
 */
public class EnumTest {

    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
    }

    //private static Day today=Day.MONDAY;
    private Day today=Day.MONDAY;

    private EnumTest(){
    }

    EnumTest(String var){
        for(Day day : Day.values()){
            System.out.println(day.name());
            if(day.name().equals(var)) {
                //EnumTest.today = day;
                this.today = day;
                return;
            }
        }
    }

    protected EnumTest(int var){
        for(Day day : Day.values()){
            if (day.ordinal()==var){
                //EnumTest.today=day;
                this.today = day;
                return;
            }
        }
    }

    public EnumTest(Day toDay){
        //EnumTest.today=toDay;
        this.today = toDay;
    }

    public void testDay(){
        //switch (EnumTest.today){
        switch (this.today){
            case MONDAY:
                System.out.println("Mondays are sucks");
                break;
            case FRIDAY:
                System.out.println("Every fridays are a new hope");
                break;
            case SATURDAY:
                System.out.println("Saturday mornings are amazing!");
                break;
            case SUNDAY:
                System.out.println("Sundays are cool but there is some desperation at the end of the day");
                break;
            default:
                System.out.println("Just survive at midweek days...");
        }
    }
    public static void main(String... args){
        Day day = Day.FRIDAY;
        EnumTest enumTest = new EnumTest();
        enumTest.testDay();
    }

}
