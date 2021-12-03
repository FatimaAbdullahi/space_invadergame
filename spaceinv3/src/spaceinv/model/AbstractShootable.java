package spaceinv.model;

public class AbstractShootable extends AbstractPositionable implements Shootable {
    private double dx;
    private int dy;

    public AbstractShootable(double x, double y, double width, double height,double dx, int dy) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;

    }

    public void setDy(int dy){this.dy = dy;}

    public void moveY(){setY(getY()+getDY());}

    public void move(){
        setX(getX()+getDx());
    }

    public void stop(){
        dx = 0;
    }

    public void setDx(double dx){this.dx = dx;}

    public double getDx(){return dx;}

    public int getDY(){return dy;}


    @Override
    public Projectile fire() {
        return Shooter.fire(this,dy);
    }
}
