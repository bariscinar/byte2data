package net.byte2data.consept.inheritance.multilevel.check.konteynir;

import net.byte2data.consept.inheritance.multilevel.check.koli.Koli;

public class Konteynir extends Koli{
    private int koliAdeti;

    public Konteynir(){
        super();
        System.out.println("konteynir -> default constructor");
    }

    public Konteynir(int cube){
        super(cube);
        System.out.println("konteynir -> tek kolili konteynir");
        this.koliAdeti=1;
    }

    public Konteynir(int cube, int icindekiKoliAdeti){
        super(cube);
        System.out.println("konteynir -> çok kolili konteynir");
        this.koliAdeti=icindekiKoliAdeti;
    }

    public Konteynir(int en, int boy,  int yukseklik){
        super(en,boy,yukseklik);
        System.out.println("konteynir -> tek kolili konteynir");
        this.koliAdeti=1;
    }

    public Konteynir(int en, int boy,  int yukseklik, int kutuAdeti){
        super(en,boy,yukseklik,kutuAdeti);
        System.out.println("konteynir -> tek kolili konteynir");
        this.koliAdeti=1;
    }

    public Konteynir(int en, int boy,  int yukseklik, int kutuAdeti, int koliadeti){
        super(en,boy,yukseklik,kutuAdeti);
        System.out.println("konteynir -> çok kolili");
        this.koliAdeti=koliadeti;
    }

    public int hacim(){
        return super.hacim()*this.koliAdeti;
    }

}
