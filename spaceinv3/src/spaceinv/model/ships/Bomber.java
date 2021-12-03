package spaceinv.model.ships;

import spaceinv.model.AbstractPositionable;
import spaceinv.model.AbstractShootable;

/*
 *   Type of space ship
 */
public class Bomber extends AbstractSpaceship {

    // Default value
    public static final int BOMBER_POINTS = 200;


    public Bomber(double x, double y) {
        super(x, y,200);
    }
}
