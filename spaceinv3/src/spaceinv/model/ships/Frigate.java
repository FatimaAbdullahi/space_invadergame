package spaceinv.model.ships;

import spaceinv.model.AbstractShootable;

/*
 *   Type of space ship
 */
public class Frigate extends AbstractSpaceship {

    // Default value
    public static final int FRIGATE_POINTS = 300;


    public Frigate(double x, double y) {
        super(x, y,300);
    }
}
