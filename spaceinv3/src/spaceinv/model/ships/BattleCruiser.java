package spaceinv.model.ships;

import spaceinv.model.AbstractShootable;

/*
 *   Type of space ship
 */
public class BattleCruiser extends AbstractSpaceship {

    // Default value
    public static final int BATTLE_CRUISER_POINTS = 100;

    public BattleCruiser(double x, double y) {
        super(x, y,100);
    }
}
