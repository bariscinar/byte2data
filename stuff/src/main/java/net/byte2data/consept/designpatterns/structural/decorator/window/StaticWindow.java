package net.byte2data.consept.designpatterns.structural.decorator.window;

public class StaticWindow implements IWindow {

    private String desc;

    public StaticWindow(String desc){
        this.desc=desc;
    }

    @Override
    public void draw() {
        System.out.print("STATIC DRAWING");
    }

    @Override
    public String getDescription() {
        return this.desc;
    }
}
