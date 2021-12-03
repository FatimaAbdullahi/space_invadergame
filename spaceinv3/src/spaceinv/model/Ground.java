package spaceinv.model;


import static spaceinv.model.SI.*;

/*
    The ground where the Gun lives.
    Used to check if projectiles from ships have hit the ground
 */
public class Ground extends AbstractPositionable  {
    public Ground(){
        super(0,GAME_HEIGHT-GROUND_HEIGHT,GAME_WIDTH,GROUND_HEIGHT);
    }


}
