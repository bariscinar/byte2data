package net.byte2data.consept.inheritance.basics.box;

class Box {

    private double height;
    double width;
    protected double depth;

    public Box(){
        this.depth=-1.0;
        this.height=-1.0;
        this.width=-1.0;
    }

    public Box(double h, double w, double d){
        this.height=h;
        this.width=w;
        this.depth=d;
    }

    public Box(Box anOther){
        this.depth=anOther.depth;
        this.width=anOther.width;
        this.height=anOther.height;
    }

    double volume(){
        return this.height*this.width*this.depth;
    }
}

class BoxWithHeight extends Box {

    private double weight;

    public BoxWithHeight(){
        super();
        this.weight=-1;
    }

    public BoxWithHeight(double h, double w, double d, double we){
        super(h,w,d);
        this.weight=we;
    }

    public BoxWithHeight(BoxWithHeight otherBoxWithHeight){
        super(otherBoxWithHeight);
        this.weight=otherBoxWithHeight.weight;
    }

    public void displayBoxInfo(){
        //System.out.println("this.height: " +this.height);
        System.out.println("this.width: " +this.width);
        System.out.println("this.depth: " +this.depth);
        System.out.println("this.weight: " +this.weight);
        System.out.println("this.volume: " +this.volume());
    }
}
