package net.byte2data.consept.designpatterns.structural.decorator.window.decorators;

import net.byte2data.consept.designpatterns.structural.decorator.window.IWindow;

public class BorderedWindow extends WindowDecorator {

    public BorderedWindow(IWindow window, String desc) {
        super(window, desc);
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }
}
