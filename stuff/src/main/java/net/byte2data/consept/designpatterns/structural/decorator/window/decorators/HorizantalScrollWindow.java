package net.byte2data.consept.designpatterns.structural.decorator.window.decorators;


import net.byte2data.consept.designpatterns.structural.decorator.window.IWindow;

public class HorizantalScrollWindow extends WindowDecorator {

    private String desc;
    public HorizantalScrollWindow(IWindow window, String desc) {
        super(window, desc);
        this.desc=desc;
    }

    @Override
    public void draw() {
        super.draw();
        drawHorizantal();
    }

    private void drawHorizantal(){
        System.out.print(" HORIZANTAL ");
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " - " + this.desc;

    }
}
