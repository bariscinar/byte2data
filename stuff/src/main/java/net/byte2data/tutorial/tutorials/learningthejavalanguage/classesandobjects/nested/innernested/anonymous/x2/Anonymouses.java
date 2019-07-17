package net.byte2data.tutorial.tutorials.learningthejavalanguage.classesandobjects.nested.innernested.anonymous.x2;

/**
 * Created by barisci on 13.09.2017.
 */
public class Anonymouses {

    public void sayHello() {

        class EnglishGreeting implements HelloWorld{
            String name = "world";
            public void greet() {
                greetSomeone("world");
            }
            public void greetSomeone(String someone) {
                name = someone;
                System.out.println("Hello " + name);
            }
        }

        HelloWorld frenchGreeting = new HelloWorld() {
            @Override
            public void greet() {

            }

            @Override
            public void greetSomeone(String someone) {

            }
        };

        HelloWorld englishGreeting = new EnglishGreeting();
        englishGreeting.greet();
        frenchGreeting.greetSomeone("Fred");
    }

    public static void main(String... args) {
        Anonymouses myApp = new Anonymouses();
        myApp.sayHello();
    }

}
