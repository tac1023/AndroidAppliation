package edu.unh.cs.cs619.bulletzone.tank_action;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import edu.unh.cs.cs619.bulletzone.model.Bullet;
import edu.unh.cs.cs619.bulletzone.model.Coast;
import edu.unh.cs.cs619.bulletzone.model.DebrisField;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Hill;
import edu.unh.cs.cs619.bulletzone.model.Soldier;
import edu.unh.cs.cs619.bulletzone.model.Tank;
import edu.unh.cs.cs619.bulletzone.model.Tunnel;
import edu.unh.cs.cs619.bulletzone.model.Wall;
import edu.unh.cs.cs619.bulletzone.model.Water;
import edu.unh.cs.cs619.bulletzone.repository.InMemoryGameRepository;
import com.google.common.base.Optional;



import static org.junit.Assert.*;

/**
 * Created by Tyler on 4/18/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class SoldierActionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @InjectMocks
    InMemoryGameRepository repo;
    @InjectMocks
    TankAction tankAction;
    @InjectMocks
    SoldierAction soldierAction;
    @InjectMocks
    Action action;

    private int bulletType = 2;

    @Before
    public void initialize() throws Exception {
        repo.createTest();
        action = new Action();
    }

    //Non-Timed Tests:

    @Test
    public void TurnLeftWhileUpSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));

        Assert.assertTrue(action.turn(tank.getId(), Direction.Left));
        repo.leave(tank.getId());
    }

    @Test
    public void TurnRightWhileUpSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));

        Assert.assertTrue(action.turn(tank.getId(), Direction.Right));
        repo.leave(tank.getId());
    }

    @Test
    public void TurnUpWhileUpSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));

        Assert.assertTrue(action.turn(tank.getId(), Direction.Up));
        repo.leave(tank.getId());
    }

    @Test
    public void TurnDownWhileUpFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));

        Assert.assertFalse(action.turn(tank.getId(), Direction.Down));
        repo.leave(tank.getId());
    }

    @Test
    public void MoveUpWhileUpSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        repo.leave(tank.getId());
    }

    @Test
    public void MoveDownWhileUpSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));

        Assert.assertTrue(action.move(tank.getId(), Direction.Down));
        repo.leave(tank.getId());
    }

    @Test
    public void MoveLeftWhileUpFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));

        Assert.assertFalse(action.move(tank.getId(), Direction.Left));
        repo.leave(tank.getId());
    }

    @Test
    public void MoveRightWhileUpFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));

        Assert.assertFalse(action.move(tank.getId(), Direction.Right));
        repo.leave(tank.getId());
    }

    @Test
    public void FiringMoreThanAllowedNumberOfBulletsFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        Assert.assertTrue(action.turn(tank.getId(), Direction.Left));

        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep(tank.getSoldier().getAllowedFireInterval()+250);
        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep(tank.getSoldier().getAllowedFireInterval()+250);
        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));

        TimeUnit.MILLISECONDS.sleep(tank.getSoldier().getAllowedFireInterval());
        Assert.assertTrue(action.turn(tank.getId(), Direction.Up));
        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        Assert.assertTrue(action.turn(tank.getId(), Direction.Left));

        TimeUnit.MILLISECONDS.sleep(tank.getSoldier().getAllowedFireInterval()+250);
        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep(tank.getSoldier().getAllowedFireInterval()+250);
        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep(tank.getSoldier().getAllowedFireInterval()+250);

        Assert.assertTrue(action.turn(tank.getId(), Direction.Up));
        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        Assert.assertTrue(action.turn(tank.getId(), Direction.Left));

        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep(tank.getSoldier().getAllowedFireInterval()+250);
        Assert.assertFalse(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));

        repo.leave(tank.getId());
    }

    @Test
    public void MovingOntoWallFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        Soldier soldier = tank.getSoldier();
        Assert.assertNotNull(soldier.getParent().getNeighbor(Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).setFieldEntity(new Wall());

        Assert.assertFalse(action.move(tank.getId(), Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).clearField();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingOntoHillSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        Soldier soldier = tank.getSoldier();
        Assert.assertNotNull(soldier.getParent().getNeighbor(Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).setFieldEntity(new Hill());

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).clearField();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingIntoDebrisFieldSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        Soldier soldier = tank.getSoldier();
        Assert.assertNotNull(soldier.getParent().getNeighbor(Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).setFieldEntity(new DebrisField(false));

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).clearField();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingIntoForestSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        Soldier soldier = tank.getSoldier();
        Assert.assertNotNull(soldier.getParent().getNeighbor(Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).setFieldEntity(new DebrisField(true));

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).clearField();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingIntoCoastalWaterSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        Soldier soldier = tank.getSoldier();
        Assert.assertNotNull(soldier.getParent().getNeighbor(Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).setFieldEntity(new Coast());

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).clearField();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingIntoOpenWaterSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        Soldier soldier = tank.getSoldier();
        Assert.assertNotNull(soldier.getParent().getNeighbor(Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).setFieldEntity(new Water());

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).clearField();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingIntoTankFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        Soldier soldier = tank.getSoldier();
        Assert.assertNotNull(soldier.getParent().getNeighbor(Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).setTankEntity(new Tank(1, Direction.Up, "1"));

        Assert.assertFalse(action.move(tank.getId(), Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).clearTankItem();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingIntoBulletFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        Soldier soldier = tank.getSoldier();
        Assert.assertNotNull(soldier.getParent().getNeighbor(Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).setTankEntity(new Bullet(tank.getId(), Direction.Up, 0));

        Assert.assertFalse(action.move(tank.getId(), Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).clearTankItem();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingIntoSoldierFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        tank.setLastEjectTime(tank.getLastEjectTime() - tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        Soldier soldier = tank.getSoldier();
        Assert.assertNotNull(soldier.getParent().getNeighbor(Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).setTankEntity(new Soldier(2, Direction.Up, "2", 25));

        Assert.assertFalse(action.move(tank.getId(), Direction.Up));
        soldier.getParent().getNeighbor(Direction.Up).clearTankItem();
        repo.leave(tank.getId());
    }

    //End Non-Timed Tests

    //Timed Tests:

    @Test
    public void TurnImmediatelyAfterTurningSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));

        Assert.assertTrue(action.turn(tank.getId(), Direction.Left));
        Assert.assertTrue(action.turn(tank.getId(), Direction.Left));
        repo.leave(tank.getId());

    }

    @Test
    public void MoveImmediatelyAfterMoveFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        Assert.assertFalse(action.move(tank.getId(), Direction.Down));
        repo.leave(tank.getId());
    }

    @Test
    public void MoveWaitHalfAllowedMoveIntervalAfterMoveFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        TimeUnit.MILLISECONDS.sleep(tank.getSoldier().getAllowedMoveInterval()/2);
        Assert.assertFalse(action.move(tank.getId(), Direction.Down));
        repo.leave(tank.getId());
    }

    @Test
    public void MoveWaitAllowedMoveIntervalAfterMoveSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        TimeUnit.MILLISECONDS.sleep(tank.getSoldier().getAllowedMoveInterval());
        Assert.assertTrue(action.move(tank.getId(), Direction.Down));
        repo.leave(tank.getId());
    }

    @Test
    public void FireImmediatelyAfterFireFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));

        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        Assert.assertFalse(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        repo.leave(tank.getId());
    }

    @Test
    public void FireWaitHalfAllowedFireIntervalAfterFireFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));

        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep(tank.getSoldier().getAllowedFireInterval()/2);
        Assert.assertFalse(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        repo.leave(tank.getId());
    }

    @Test
    public void FireWaitAllowedFireIntervalAfterFireSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));

        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep(tank.getSoldier().getAllowedFireInterval());
        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        repo.leave(tank.getId());
    }

    @Test
    public void SoldierCanFireInAllDirections() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));

        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep(tank.getSoldier().getAllowedFireInterval());
        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(Direction.Left)));
        TimeUnit.MILLISECONDS.sleep(tank.getSoldier().getAllowedFireInterval());
        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(Direction.Right)));
        TimeUnit.MILLISECONDS.sleep(tank.getSoldier().getAllowedFireInterval());
        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(Direction.Down)));
        repo.leave(tank.getId());
    }


    @Test
    public void SoldierMovingIntoDebrisFieldExperiencesCorrectDelay() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        Assert.assertNotNull(tank.getSoldier().getParent().getNeighbor(Direction.Up));
        tank.getSoldier().getParent().getNeighbor(Direction.Up).setFieldEntity(new DebrisField(false));
        Assert.assertTrue(tank.getSoldier().getParent().getNeighbor(Direction.Up).getEntity() instanceof DebrisField);

        long millis = System.currentTimeMillis();
        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        long millis2 = System.currentTimeMillis();
        millis += tank.getAllowedMoveInterval();

        //Assert that current time is "DelayTime" more than start time
        Assert.assertTrue(millis2 >= millis);
        TimeUnit.MILLISECONDS.sleep(tank.getSoldier().getAllowedMoveInterval());
        Assert.assertTrue(action.move(tank.getId(), Direction.Down));
        repo.leave(tank.getId());
    }

    @Test
    public void SoldierMovingOntoHillHasNoDelay() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        Assert.assertNotNull(tank.getSoldier().getParent().getNeighbor(Direction.Up));
        tank.getSoldier().getParent().getNeighbor(Direction.Up).setFieldEntity(new Hill());
        Assert.assertTrue(tank.getSoldier().getParent().getNeighbor(Direction.Up).getEntity() instanceof Hill);

        long millis = System.currentTimeMillis();
        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        long millis2 = System.currentTimeMillis();
        millis += tank.getAllowedMoveInterval();

        //Assert that current time is "DelayTime" more than start time
        Assert.assertFalse(millis2 >= millis);
        TimeUnit.MILLISECONDS.sleep(tank.getSoldier().getAllowedMoveInterval());
        Assert.assertTrue(action.move(tank.getId(), Direction.Down));
        repo.leave(tank.getId());
    }
    //End Timed Tests

    @Test
    public void SoldierCanMoveThroughHoles() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());

        action.eject(tank.getId());
        Soldier soldier = tank.getSoldier();
        Assert.assertNotNull(soldier);
        Assert.assertNotNull(soldier.getParent());

        Assert.assertFalse(action.drill(soldier.getId()));

        soldier.getParent().setFieldEntity(new Tunnel());
        Assert.assertTrue(action.move(soldier.getId(), Direction.Below));
        TimeUnit.MILLISECONDS.sleep(soldier.getAllowedMoveInterval());
        Assert.assertTrue(action.move(soldier.getId(), Direction.Above));

        repo.leave(tank.getId());
    }

    @Test
    public void SoldierCannotDrillFromTunneler() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        Assert.assertTrue(action.toTunneler(tank.getId()));
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());

        action.eject(tank.getId());
        Soldier soldier = tank.getSoldier();
        Assert.assertNotNull(soldier);

        Assert.assertFalse(action.drill(soldier.getId()));
        repo.leave(tank.getId());
    }

    @Test
    public void SoldierCannotFireInWater() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());

        action.eject(tank.getId());
        Soldier soldier = tank.getSoldier();
        Assert.assertNotNull(soldier);

        soldier.getParent().setFieldEntity(new Water());

        Assert.assertFalse(action.fire(soldier.getId(), bulletType, Direction.toByte(tank.getDirection())));
        repo.leave(tank.getId());
    }
}