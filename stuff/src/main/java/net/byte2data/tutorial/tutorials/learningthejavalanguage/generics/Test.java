package net.byte2data.tutorial.tutorials.learningthejavalanguage.generics;

public class Test {
    public static void main(String[] args) throws Exception // Just for simplicity!
    {
        Test test = new Test();
        Generic<Bar> x = test.new Generic<Bar>(Bar.class);
        Bar y = x.buildOne();
    }


    public class Generic<T> {
        Class clazz;
        public Generic(Class clazz) {
            this.clazz = clazz;
        }

        public T buildOne() throws InstantiationException,
                IllegalAccessException {
            return (T) clazz.getConstructors()[0];
        }
    }

    public class Bar {
        public Bar() {
            System.out.println("Constructing");
        }
    }

}