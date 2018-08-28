package edu.unh.cs.cs619.bulletzone.fire;

import java.util.Timer;
import java.util.TimerTask;

import edu.unh.cs.cs619.bulletzone.model.Bullet;
import edu.unh.cs.cs619.bulletzone.model.Controllable;
import edu.unh.cs.cs619.bulletzone.model.DebrisField;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.Missile;
import edu.unh.cs.cs619.bulletzone.model.RockAndDirt;
import edu.unh.cs.cs619.bulletzone.model.Soldier;
import edu.unh.cs.cs619.bulletzone.model.Tank;
import edu.unh.cs.cs619.bulletzone.model.Wall;
import edu.unh.cs.cs619.bulletzone.repository.Monitor;

/**
 * Handles aspects of animating a bullet across the game.
 *
 * @author Tyler Currier
 * @version 2.0
 * @since 5/6/2018
 */

public class FireBullet {

    private static final int BULLET_PERIOD = 200;
    private final Monitor monitor = Monitor.getInstance();
    private final Timer timer = new Timer();

    /**
     * Default Constructor.
     */
    public FireBullet()
    {

    }

    /**
     * public version of fire that encapsulates the private worker version
     * inside a thread.
     *
     * @param game Current game
     * @param controllable Tank or Soldier
     * @param bullet Tank or Soldier's bullet
     */
    public void fire(Game game, Controllable controllable, Bullet bullet)
    {
        Runnable runnable = () -> fireBullet(game, controllable, bullet);
        new Thread( runnable ).start();
    }

    /**
     * Private version of fire that does the work on a separate thread
     *
     * @param game Current game
     * @param controllable Tank or Soldier
     * @param bullet Tank or Soldier's bullet
     */
    private void fireBullet(Game game, Controllable controllable, Bullet bullet)
    {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {


                FieldHolder currentField = bullet.getParent();

                if(!currentField.isTankPresent()) {
                    adjustControllable(controllable, bullet);
                    cancel();
                }

                Direction direction = bullet.getDirection();
                FieldHolder nextField = decideNextField(bullet, currentField, direction);

                // Is the bullet visible on the field?
                boolean isVisible = currentField.isTankPresent()
                        && (currentField.getTankItem() == bullet);

                boolean isForest = isForest(nextField);

                if(bullet.isCleared()) {
                    explodeBullet(controllable, bullet, isVisible);
                    cancel();
                }
                else if(bullet.isExploding())
                {
                    if(bullet.isMissile())
                        calculateSplashDamage((Missile)bullet, nextField, game, false);
                    cancel();
                }
                else {
                    synchronized (monitor) {
                        if (shouldBulletHit(nextField, isForest)) {
                            // Something is there, hit it
                            decideHit(nextField, bullet.getDamage(), game, isForest);

                            explodeBullet(controllable, bullet, isVisible);
                            if(bullet.isMissile())
                                calculateSplashDamage((Missile)bullet, nextField, game, true);
                            cancel();

                        } else {
                            if (isVisible) {
                                // Remove bullet to place in neighbor
                                currentField.clearTankItem();
                            }

                            nextField.setTankEntity(bullet);
                            bullet.setParent(nextField);
                        }
                    }
                }

            }
        }, 0, BULLET_PERIOD);

    }


    /**
     * Decide whether a bullet should hit in the next holder or if it
     * should keep moving
     *
     * @param nextField Next place bullet wants to go
     * @param isForest If next field is forest
     * @return boolean
     */
    private boolean shouldBulletHit(FieldHolder nextField, boolean isForest)
    {
        if(nextField.isTankPresent()) {
            return(!(nextField.getTankItem() instanceof Bullet) || nextField.getTankItem().getIntValue() > 100);
        }
        if(nextField.isPresent())
        {
            if(nextField.getEntity() instanceof DebrisField)
                return isForest;
            if(nextField.getEntity() instanceof Wall) {
                if(!((Wall)nextField.getEntity()).canEnter())
                    return true;
            }
            if(nextField.getEntity() instanceof RockAndDirt)
            {
                if(!((RockAndDirt) nextField.getEntity()).isOpen())
                    return true;
            }
        }
        return false;
    }

    /**
     * Determine whether the field is a forest
     *
     * @param nextField field to analyze
     * @return boolean
     */
    private boolean isForest(FieldHolder nextField)
    {
        if(nextField.isPresent() && nextField.getEntity() instanceof DebrisField)
        {
            return((DebrisField)nextField.getEntity()).isForest();
        }
        return false;
    }

    /**
     * Decide if the next field is straight ahead or if it is shifted to the right
     * or left (applicable when the bullet is a missile)
     *
     * @param bullet Bullet that is traveling
     * @param currentField Current location
     * @param direction direction of travel
     * @return FieldHolder
     */
    private FieldHolder decideNextField(Bullet bullet, FieldHolder currentField, Direction direction)
    {
        if(bullet.isMissile())
        {
            Direction shift = ((Missile) bullet).getShift();
            if(shift != null)
            {
                return currentField.getNeighbor(direction).getNeighbor(shift);
            }
        }
        return currentField.getNeighbor(direction);
    }

    /**
     * The bullet is hitting something. Decide what it is hitting and how
     * it should react.
     *
     * @param nextField The next holder that the bullet wants to go
     * @param damage The damage of current bullet
     * @param game The current game
     * @param isForest Marks if the nextField is a forest
     */
    private void decideHit(FieldHolder nextField, int damage, Game game, boolean isForest)
    {
        if (nextField.isPresent() && nextField.getEntity() instanceof DebrisField && isForest) {
            nextField.getEntity().hit(damage);
        } else if (nextField.isTankPresent()) {
            nextField.getTankItem().hit(damage);

            //Hit a tank, kill if dead
            if (nextField.getTankItem() instanceof Tank) {
                Tank t = (Tank) nextField.getTankItem();
                System.out.println("tank is hit, tank life: " + t.getLife());
                if (t.getLife() <= 0) {
                    //if soldier in tank, player is dead
                    if (!t.isSoldierOut())
                        t.setIsAlive();

                    t.catchFire();
                }
            } else if (nextField.getTankItem() instanceof Soldier) //Hit a soldier, kill if dead
            {
                Soldier s = (Soldier) nextField.getTankItem();
                System.out.println("soldier is hit, soldier life: " + s.getLife());
                if (s.getLife() <= 0) {
                    //if soldier dies, player is dead
                    game.getTanks().get(s.getId()).setIsAlive();

                    s.die();
                }
            } else if (nextField.getTankItem() instanceof Bullet) {
                ((Bullet) nextField.getTankItem()).explode();
            }
        } else if (nextField.isPresent()) {
            nextField.getEntity().hit(damage);

            if (nextField.getEntity() instanceof Wall) {
                Wall w = (Wall) nextField.getEntity();

                //if destructible wall, remove from field
                if (w.getIntValue() <= 1000 && w.isDestructible()) {
                    game.getHolderGrid().get(w.getPos()).clearField();
                }
            }
        }
    }

    /**
     * The Bullet has hit something and thus needs to explode
     *
     * @param controllable Tank or Soldier
     * @param bullet Tank or Soldier's bullet
     * @param isVisible whether the player can see the bullet
     */
    private void explodeBullet(Controllable controllable, Bullet bullet, boolean isVisible)
    {
        if (isVisible) {
            // Remove bullet from field
            bullet.explode();
        }
        adjustControllable(controllable, bullet);
    }

    /**
     * For missiles, calculate the splash damage resulting from an explosion
     *
     * @param missile Missile whose splash damage to calculate
     * @param nextField Field ahead
     * @param game Reference to the Game
     * @param hit Whether the missile exploded as the result of a hit
     */
    private void calculateSplashDamage(Missile missile, FieldHolder nextField, Game game, boolean hit)
    {
        FieldHolder parent = missile.getParent();
        Direction relativeUp = missile.getDirection();
        byte rU = Direction.toByte(relativeUp);
        Direction relativeRight = Direction.fromByte((byte)((rU + 2) % 8));
        Direction relativeDown = Direction.fromByte((byte)((rU + 4) % 8));
        Direction relativeLeft = Direction.fromByte((byte)((rU + 6) % 8));
        FieldHolder splash;
        boolean isForest;

        if(!hit)
        {
            splash = nextField;
            isForest = isForest(splash);
            decideHit(splash, missile.getDamage(), game, isForest);
            if(!shouldBulletHit(splash, isForest))
            {
                splash = nextField.getNeighbor(relativeUp);
                decideHit(splash, missile.getSplashDamage1(), game, isForest(splash));
            }
        }

        splash = nextField.getNeighbor(relativeLeft);
        isForest = isForest(splash);
        decideHit(splash, missile.getSplashDamage2(), game, isForest);
        if(!shouldBulletHit(splash, isForest))
        {
            splash = splash.getNeighbor(relativeUp);
            decideHit(splash, missile.getSplashDamage2(), game, isForest(splash));
        }

        splash = nextField.getNeighbor(relativeRight);
        isForest = isForest(splash);
        decideHit(splash, missile.getSplashDamage2(), game, isForest);
        if(!shouldBulletHit(splash, isForest))
        {
            splash = splash.getNeighbor(relativeUp);
            decideHit(splash, missile.getSplashDamage2(), game, isForest(splash));
        }

        splash = parent;
        decideHit(splash, missile.getSplashDamage1(), game, isForest(splash));

        splash = parent.getNeighbor(relativeRight);
        decideHit(splash, missile.getSplashDamage1(), game, isForest(splash));

        splash = parent.getNeighbor(relativeLeft);
        decideHit(splash, missile.getSplashDamage1(), game, isForest(splash));

        splash = parent.getNeighbor(relativeDown);
        decideHit(splash, missile.getSplashDamage1(), game, isForest(splash));
        decideHit(splash.getNeighbor(relativeLeft), missile.getSplashDamage2(), game,
                isForest(splash.getNeighbor(relativeLeft)));
        decideHit(splash.getNeighbor(relativeRight), missile.getSplashDamage2(), game,
                isForest(splash.getNeighbor(relativeRight)));

    }

    /**
     * The bullet is gone, so the owner of the bullet needs to be updated
     *
     * @param controllable Tank or Soldier
     * @param bullet Tank or Soldier's bullet
     */
    private void adjustControllable(Controllable controllable, Bullet bullet)
    {
        if(bullet.isMissile())
        {
            ((Tank)controllable).explodeMissile();
        }
        else {
            controllable.setNumberOfBullets(controllable.getNumberOfBullets() - 1);
            controllable.deleteBulletReference(bullet.getSoldierBulletId());
        }
    }

}
