package net.byte2data.consept.concurrency.concept.threadobjects.producerandconsumer;

public class Box {
    private long height,width,length;
    private Box box;

    public Box(long height, long width, long length) {
        this.height = height;
        this.width = width;
        this.length = length;
        box=this;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }
}
