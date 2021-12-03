package spaceinv.model;

import static spaceinv.model.SI.*;

/*
    Used to check if projectiles from gun have left our world
    Non visible class
 */
public class OuterSpace extends AbstractPositionable {
    public OuterSpace(){
        super(0,0,GAME_WIDTH,OUTER_SPACE_HEIGHT);
    }

}
