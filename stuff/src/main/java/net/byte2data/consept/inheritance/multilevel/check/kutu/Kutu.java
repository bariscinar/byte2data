package net.byte2data.consept.inheritance.multilevel.check.kutu;

public class Kutu {

    private int yukseklik;
    private int en;
    private int boy;

    public Kutu(){
        System.out.println("kutu -> default constructor");
    }

    public Kutu(int cube){
        System.out.println("kutu -> küp");
        this.boy=cube;
        this.en=cube;
        this.yukseklik=cube;
    }

    public Kutu(int en, int boy, int yukseklik){
        System.out.println("kutu -> diktörtgen prizma");
        this.en=en;
        this.boy=boy;
        this.yukseklik=yukseklik;
    }

    public Kutu(Kutu baskaKutu){
        System.out.println("kutu -> baska kutu");
        this.en=baskaKutu.en;
        this.boy=baskaKutu.boy;
        this.yukseklik=baskaKutu.yukseklik;
    }

    public int hacim(){
        return this.en*this.boy*this.yukseklik;
    }

}
