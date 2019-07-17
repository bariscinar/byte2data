package net.byte2data.consept.designpatterns.structural.decorator.window;

import net.byte2data.consept.designpatterns.structural.decorator.window.decorators.HorizantalScrollWindow;
import net.byte2data.consept.designpatterns.structural.decorator.window.decorators.VerticalScrollWindow;

public class WindowTest {

    public static void main(String... args){
        IWindow window = new VerticalScrollWindow(new HorizantalScrollWindow(new StaticWindow("window"),"hori"),"verti");
        System.out.println(window.getDescription());
        window.draw();
    }

}
