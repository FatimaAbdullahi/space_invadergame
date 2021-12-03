package spaceinv.model;

public class AbstractPositionable implements Positionable{

    private double x;
    private double y;
    private final double width;
    private final double height;

    public AbstractPositionable(double x, double y, double width,double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public double getMaxX() {
        return x + width;
    }

    public double getMaxY() {
        return y + height;
    }

    public double setY(double y){return this.y = y;}

    public double setX(double x){return this.x = x;}

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }
}
