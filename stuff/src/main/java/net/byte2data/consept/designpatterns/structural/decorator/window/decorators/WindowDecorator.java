package net.byte2data.consept.designpatterns.structural.decorator.window.decorators;

import net.byte2data.consept.designpatterns.structural.decorator.window.IWindow;

public abstract class WindowDecorator implements IWindow {

    private IWindow window;
    private String desc;

    protected WindowDecorator(IWindow window, String desc){
        this.window=window;
        this.desc=desc;
    }

    @Override
    public void draw() {
        this.window.draw();
    }

    @Override
    public String getDescription() {
        return this.window.getDescription();
    }
}
