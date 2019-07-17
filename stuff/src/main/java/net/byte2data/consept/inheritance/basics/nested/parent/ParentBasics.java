package net.byte2data.consept.inheritance.basics.nested.parent;

 class Basics {

    private int instanceBasicInt;
    private static int classBasicInt;

    static int packagePrivateClassInt;
    int packagePrivateInstanceInt;

    protected static int protectedClassInt;
    protected int protectedInstanceInt;

    public static int publicClassInt;
    public int publicInstanceInt;


    private int getInstanceVar(){
        return this.instanceBasicInt;
    }
    private static int getClassVar(){
        return classBasicInt;
    }


    public  class SubBasics extends Basics{
        public void doIt(){
            System.out.println(instanceBasicInt);
        }
    }

    public static class StaticSubBasics extends Basics{
        public void doit(){
            System.out.println(classBasicInt);
        }
    }
}

class Test{
    public static void main(String... args){
        Basics basics = new Basics();

        Basics.SubBasics subBasics = basics.new SubBasics();
        subBasics.doIt();

        Basics.StaticSubBasics staticSubBasics = new Basics.StaticSubBasics();
        staticSubBasics.doit();

    }
}
