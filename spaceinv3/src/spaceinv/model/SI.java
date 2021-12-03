package spaceinv.model;



import spaceinv.event.EventBus;
import spaceinv.event.ModelEvent;
import spaceinv.model.ships.AbstractSpaceship;
import spaceinv.model.ships.BattleCruiser;
import spaceinv.model.ships.Bomber;
import spaceinv.model.ships.Frigate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 *  SI (Space Invader) class representing the overall
 *  data and logic of the game
 *  (nothing about the look/rendering here)
 */
public class SI {

    // Default values (not to use directly). Make program adaptable
    // by letting other programmers set values if they wish.
    // If not, set default values (as a service)
    public static final int GAME_WIDTH = 500;
    public static final int GAME_HEIGHT = 500;
    public static final int LEFT_LIMIT = 50;
    public static final int RIGHT_LIMIT = 450;
    public static final int SHIP_WIDTH = 20;
    public static final int SHIP_HEIGHT = 20;
    public static final int SHIP_MAX_DX = 3;
    public static final int SHIP_MAX_DY = 0;
    public static final int GUN_WIDTH = 20;
    public static final int GUN_HEIGHT = 20;
    public static final double GUN_MAX_DX = 2;
    public static final double PROJECTILE_WIDTH = 5;
    public static final double PROJECTILE_HEIGHT = 5;
    public static final int GROUND_HEIGHT = 20;
    public static final int OUTER_SPACE_HEIGHT = 10;




    public static final long ONE_SEC = 1_000_000_000;
    public static final long HALF_SEC = 500_000_000;
    public static final long TENTH_SEC = 100_000_000;

    private static final Random rand = new Random();

    // TODO More references here
    private final Gun gun;

    private final List<Projectile> shipBombs = new ArrayList<>();
    private Projectile gunProjectile;
    private int points;
    private OuterSpace outerS;
    private Ground ground;
    private List<AbstractSpaceship> ships;
    private List<Positionable> toRemove = new ArrayList<>();


    public SI(Gun gun, List<AbstractSpaceship> ships){
        this.gun = gun;
        this.outerS = new OuterSpace();
        this.ground = new Ground();
        this.ships = ships;



    }

    // TODO Constructor here


    // Timing. All timing handled here!
    private long lastTimeForMove;
    private long lastTimeForFire;
    private int shipToMove = 0;

    // ------ Game loop (called by timer) -----------------

    public void update(long now) {


           //movement
          moveGun();
          moveProjectile();
          moveShip();






           // Ships fire
            shipFire(now);





            // Collisions
            gunHitShip();

      if(ships.size() == 0){
          EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.HAS_WON));
      }

    }


    public void shipFire(long now){
        if (now - lastTimeForFire > ONE_SEC) {
            int shipIndex = rand.nextInt(ships.size());
            shipBombs.add(ships.get(shipIndex).fire());
            lastTimeForFire = now;
        }

        for (Projectile bomb : shipBombs) {
            bomb.move2();
            if (intersects(bomb,ground) || intersects(bomb,gun)) {
                toRemove.add(bomb);
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BOMB_HIT_GROUND,bomb));
                if (intersects(bomb,gun)) {
                    points -= 10;
                    EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BOMB_HIT_GUN,gun));
                }
            }
        }
        shipBombs.removeAll(toRemove);
        toRemove.clear();

    }


    public boolean intersectOuter(Positionable outerS,Positionable gunP){
        if(outerS.getY() + outerS.getHeight() > gunP.getY()){
            return true;
        }
        return false;
    }

    public void gunHitShip(){
        if(gunProjectile != null){
        List<AbstractSpaceship> hitShips = new ArrayList<>();
        for(AbstractSpaceship ship : ships){
            if(intersects(gunProjectile,ship)){
                hitShips.add(ship);
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.GUN_HIT_SHIP,gunProjectile));
                points += ship.getPoints();
            }
        }
        if(ships.removeAll(hitShips)){
            gunProjectile = null;
         }
        }


    }

    public boolean intersects(AbstractPositionable a, AbstractPositionable b) {
        boolean above = a.getMaxY() < b.getY();
        boolean below = a.getY() > b.getMaxY();
        boolean leftOf = a.getMaxX() < b.getX();
        boolean rightOf = a.getX() > b.getMaxX();
        return !(above || below || leftOf || rightOf);
    }

    public void moveProjectile(){
        if(gunProjectile != null) {
            if(intersectOuter(outerS,gunProjectile)){
                gunProjectile = null;
            }
            else{gunProjectile.move();}}
    }


    public void moveGun(){
        if((gun.getX() + gun.getDx()  < LEFT_LIMIT) || (gun.getX() + gun.getDx() > RIGHT_LIMIT)){
            gun.stop();
      }
        else{
            gun.move();}

    }

    public void moveShip(){
           if(shipToMove >= ships.size()){
               shipToMove = 0;
           }
           if(shipHitLeftLimit(ships.get(shipToMove)) || shipHitRightLimit(ships.get(shipToMove))){
            changeDirection();
          }
           ships.get(shipToMove).move();
           shipToMove++;
    }


    public void changeDirection(){
        for (AbstractSpaceship s : ships) {
            s.moveY();
            s.setDx(-s.getDx());
                if(s.getDx() < 0 && s.getDx() > -SHIP_MAX_DX){
                    s.setDx(s.getDx() - 0.1);
                }
                else if(s.getDx() > 0 && s.getDx() < SHIP_MAX_DX){
                    s.setDx(s.getDx() + 0.1);
                }
            }

    }

    private boolean shipHitRightLimit(AbstractSpaceship battle) {
        if(battle.getX() + battle.getDx() > RIGHT_LIMIT){
            return true;
        }
        // TODO
        return false;
    }

    private boolean shipHitLeftLimit(AbstractSpaceship battle) {
        if(battle.getX() + battle.getDx() < LEFT_LIMIT){
            return true;
        }
        // TODO
        return false;
    }


    // ---------- Interaction with GUI  -------------------------
    public void stopGun(){
        gun.stop();
    }

    public void fireGun() {
        if(gunProjectile == null){
            gunProjectile = gun.fire();
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.GUN_SHOOT));
        }

        // TODO
    }

    // TODO More methods called by GUI

    public List<Positionable> getPositionables() {
        List<Positionable> ps = new ArrayList<>();
        ps.add(gun);
        ps.add(ground);
        ps.addAll(ships);
        ps.addAll(shipBombs);

        if(gunProjectile != null){
        ps.add(gunProjectile);}
        return ps;

    }

    public void setGunSpeed(double dx){gun.setDx(dx);}

    public int getPoints() {
        return points;
    }




}
