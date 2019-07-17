package net.byte2data.consept.inheritance.multilevel.check.koli;

import net.byte2data.consept.inheritance.multilevel.check.kutu.Kutu;

public class Koli extends Kutu {
    private int kutuAdeti;

    /*
    public Koli(int... veriler) {
        int lengthOfFormalParameters = veriler.length;
        switch (lengthOfFormalParameters){
            case 0:
                //super();
                System.out.println("koli -> default constructor");
                break;
            case 1:
                //super(veriler[0]);
                System.out.println("koli -> tek küplü koli");
                this.kutuAdeti=1;
                break;
            case 2:
                //super(veriler[0], veriler[1]);
                System.out.println("koli -> çok küplü koli");
                this.kutuAdeti=veriler[1];
                break;
            case 3:
                //super(veriler[0], veriler[1],veriler[2]);
                System.out.println("koli -> tek diktörtgen prizmalı koli");
                this.kutuAdeti=1;
                break;
            case 4:
                //super(veriler[0], veriler[1],veriler[2]);
                System.out.println("koli -> çok diktörtgen prizmalı koli");
                this.kutuAdeti=veriler[3];
                break;
            default:
                System.out.println("koli -> garbage");
                break;
        }
    }
    */
    public Koli(){
        super();
        System.out.println("koli -> default constructor");
    }

    public Koli(int cube){
        super(cube);
        System.out.println("koli -> tek küplü koli");
        this.kutuAdeti=1;
    }

    public Koli(int cube, int icindekiKutuAdeti){
        super(cube);
        System.out.println("koli -> çok küplü koli");
        this.kutuAdeti=icindekiKutuAdeti;
    }

    public Koli(int en, int boy,  int yukseklik){
        super(en,boy,yukseklik);
        System.out.println("koli -> tek diktörtgen prizmalı koli");
        this.kutuAdeti=1;
    }

    public Koli(int en, int boy,  int yukseklik, int kutuAdeti){
        super(en,boy,yukseklik);
        System.out.println("koli -> çok diktörtgen prizmalı koli");
        this.kutuAdeti=kutuAdeti;
    }

    public int hacim(){
        return super.hacim()*this.kutuAdeti;
    }
}
