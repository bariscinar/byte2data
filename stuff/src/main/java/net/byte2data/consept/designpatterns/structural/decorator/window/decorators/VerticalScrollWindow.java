package net.byte2data.consept.designpatterns.structural.decorator.window.decorators;

import net.byte2data.consept.designpatterns.structural.decorator.window.IWindow;

public class VerticalScrollWindow extends WindowDecorator {

    private String desc;

    public VerticalScrollWindow(IWindow window, String desc) {
        super(window, desc);
        this.desc=desc;
    }

    @Override
    public void draw() {
        super.draw();
        drawVertical();
    }

    private void drawVertical(){
        System.out.print(" VERTICAL");
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " - " + this.desc;

    }
}
