package net.byte2data.tutorial.tutorials.learningthejavalanguage.classesandobjects.nested.innernested.anonymous.x1;

/**
 * Created by barisci on 12.09.2017.
 */
public class HelloWorldAnonymousClasses {

    private interface HelloWorld {
        public void greet();
        public void greetSomeone(String someone);
    }

    public void sayHello(String saying) {

        final int x = 12;
        String z = null;

        class EnglishGreeting implements HelloWorld {

            private static final String var1 = "world";
            protected String var2="earth";
            public String saying = "barış";
            //z="test";

            public EnglishGreeting(){

            }

            public void greet() {
                //z="test";
                System.out.println(x);
                //System.out.println(z);
                greetSomeone("world");
            }
            public void greetSomeone(String saying) {
                this.saying = saying;
                System.out.println("Hello " + saying);
            }
        }


        HelloWorld frenchGreeting = new HelloWorld() {

            String name = "tout le monde";
            public void greet() {
                greetSomeone("tout le monde");
            }
            public void greetSomeone(String someone) {
                name = someone;
                System.out.println("Salut " + name);
            }
        };

        HelloWorld spanishGreeting = new HelloWorld() {
            String name = "mundo";
            public void greet() {
                greetSomeone("mundo");
            }
            public void greetSomeone(String someone) {
                name = someone;
                System.out.println("Hola, " + name);
            }
        };

        HelloWorld turkishGreeting = new EnglishGreeting(){
            public void greetSomeone(String someone) {
                saying = someone;
                System.out.println("Hello " + saying);
            }
        };

        HelloWorld englishGreeting = new EnglishGreeting();
        englishGreeting.greet();
        frenchGreeting.greetSomeone("Fred");
        spanishGreeting.greet();
        turkishGreeting.greetSomeone("turkey");

    }

    public static void main(String... args) {
        HelloWorldAnonymousClasses myApp = new HelloWorldAnonymousClasses();
        myApp.sayHello("baba");
    }
}
