package net.byte2data.tutorial.tutorials.learningthejavalanguage.classesandobjects.stuff;

/**
 * Created by barisci on 14.08.2017.
 */
public class TandemBike extends Bicycle{

    private TandemBike tandemBike;
    private int seatCount=0;
    public void setSeatCount(int seat){
        this.seatCount = seat;
    }


    public int getSeatCount(){
        return this.seatCount;
    }

    public void setName(String... seaters) throws Exception{
        if(null== seaters){
            throw new Exception("");
        }else{
            int countOfSeaters = seaters.length;
            for(String seaterName : seaters){
                System.out.println(seaterName);
            }
        }

    }

    public TandemBike(){
        tandemBike = new TandemBike(seatCount);
    }

    public TandemBike(int seatCount){
        this.seatCount=seatCount;
        tandemBike = new TandemBike(seatCount);
    }

    public TandemBike getABic(){
        return tandemBike;
    }




}
