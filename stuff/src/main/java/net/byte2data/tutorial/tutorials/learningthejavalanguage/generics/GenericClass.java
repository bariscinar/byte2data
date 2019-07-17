package net.byte2data.tutorial.tutorials.learningthejavalanguage.generics;

public class GenericClass{

    public class GenericKlass<T> {
        private Class<T> localInstance;

        GenericKlass(Class<T> klas) {
            this.localInstance = klas;
        }

        T generate() throws InstantiationException, IllegalAccessException {
            return localInstance.newInstance();
        }
    }

    public class Box{
        private String name;
        public Box(String boxName){
            this.name=boxName;
        }
        public Box(){
            this.name="generated";
        }

        @Override
        public String toString() {
            return "Box{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    private class Executer{
        public void main() throws Exception{
            GenericKlass<Box> boxGenericClass = new GenericKlass<>(Box.class);
            System.out.println(boxGenericClass.generate().toString());
        }

    }

    public static void main(String... args) throws Exception{
        GenericClass genericClass = new GenericClass();
        Executer executer = genericClass.new Executer();
        executer.main();
    }
}
