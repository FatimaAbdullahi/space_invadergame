package spaceinv.model.ships;

import spaceinv.model.AbstractShootable;
import static spaceinv.model.SI.*;

public class AbstractSpaceship extends AbstractShootable {
    private int points;

    public AbstractSpaceship(double x, double y, int points) {
        super(x, y,SHIP_WIDTH ,SHIP_HEIGHT, 2, 10);
        this.points = points;
    }

    public int getPoints(){return points;}
}
