package net.byte2data.tutorial.tutorials.learningthejavalanguage.classesandobjects.stuff;

/**
 * Created by barisci on 17.08.2017.
 */
public class SpecialTandemBike extends TandemBike {

    private int seatHight=0;

    public void setSeatHight(int val){
        this.seatHight=val;
    }

    public int getSeatHight(){
        return this.seatHight;
    }


    public SpecialTandemBike(){
        super();
    }

    public SpecialTandemBike(int seatHignt){
        super();
    }

    public void convertIt(IntBicycle bic){

        bic.setCadence(1000);
        bic.setGear(2000);
        bic.speedUp(3000);

        bic = new SpecialTandemBike();
        System.out.println("converted bic: " + bic.toString());

    }

    public IntBicycle replaceIt(IntBicycle bic){
        bic = new SpecialTandemBike();
        bic.setCadence(1000);
        bic.setGear(2000);
        return bic;
    }

}
