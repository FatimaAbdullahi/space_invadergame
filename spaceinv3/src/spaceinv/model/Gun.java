package spaceinv.model;


import static spaceinv.model.SI.*;

/*
 *    A Gun for the game
 *    Can only fire one projectile at the time
 */
public class Gun extends  AbstractShootable   {

    public Gun(double x, double y, int dy) {
        super(x,y,GUN_WIDTH,GUN_HEIGHT,0,dy);

    }

}
