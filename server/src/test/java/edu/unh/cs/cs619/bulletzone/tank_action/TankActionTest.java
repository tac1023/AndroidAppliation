package edu.unh.cs.cs619.bulletzone.tank_action;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
import edu.unh.cs.cs619.bulletzone.model.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.Hill;
import edu.unh.cs.cs619.bulletzone.model.Soldier;
import edu.unh.cs.cs619.bulletzone.model.Tank;
import edu.unh.cs.cs619.bulletzone.model.Tunnel;
import edu.unh.cs.cs619.bulletzone.model.Wall;
import edu.unh.cs.cs619.bulletzone.model.Water;
import edu.unh.cs.cs619.bulletzone.model.RockAndDirt;
import edu.unh.cs.cs619.bulletzone.repository.InMemoryGameRepository;

import static org.junit.Assert.*;

/**
 * Created by Tyler on 4/18/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class TankActionTest {

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
    public void initialize() throws Exception
    {
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

        Assert.assertTrue(action.turn(tank.getId(), Direction.Right));
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

        Assert.assertFalse(action.turn(tank.getId(), Direction.Down));
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

        Assert.assertTrue(action.turn(tank.getId(), Direction.Up));
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
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

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
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

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
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

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
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

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
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedFireInterval());
        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedFireInterval());
        Assert.assertFalse(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedFireInterval());
        Assert.assertFalse(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        repo.leave(tank.getId());
    }

    @Test
    public void MovingIntoWallFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).setFieldEntity(new Wall());

        Assert.assertFalse(action.move(tank.getId(), Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).clearField();
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
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).setFieldEntity(new Hill());

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).clearField();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingOntoDebrisFieldSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).setFieldEntity(new DebrisField(false));

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).clearField();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingIntoForestFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).setFieldEntity(new DebrisField(true));

        Assert.assertFalse(action.move(tank.getId(), Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).clearField();
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
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).setFieldEntity(new Coast());

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).clearField();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingIntoOpenWaterFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).setFieldEntity(new Water());

        Assert.assertFalse(action.move(tank.getId(), Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).clearField();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingOntoTankFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).setTankEntity(new Tank(1, Direction.Up, "1"));

        Assert.assertFalse(action.move(tank.getId(), Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).clearTankItem();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingOntoBulletFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).setTankEntity(new Bullet(1, Direction.Up, 0));

        Assert.assertFalse(action.move(tank.getId(), Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).clearTankItem();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingOntoSoldierFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).setTankEntity(new Soldier(1, Direction.Up, "1", 25));

        Assert.assertFalse(action.move(tank.getId(), Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).clearTankItem();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingOntoRockFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Below));
        tank.getParent().getNeighbor(Direction.Below).setTankEntity(tank);
        tank.getParent().clearTankItem();
        tank.setParent(tank.getParent().getNeighbor(Direction.Below));
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).setFieldEntity(new RockAndDirt());

        Assert.assertFalse(action.move(tank.getId(), Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).clearField();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingOntoDirtFailsAsTank() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Below));
        tank.getParent().getNeighbor(Direction.Below).setTankEntity(tank);
        tank.getParent().clearTankItem();
        tank.setParent(tank.getParent().getNeighbor(Direction.Below));
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).setFieldEntity(new RockAndDirt(4200));

        Assert.assertFalse(action.move(tank.getId(), Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).clearField();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingOntoDirtSucceedsAsTunneler() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Below));
        tank.getParent().getNeighbor(Direction.Below).setTankEntity(tank);
        tank.getParent().clearTankItem();
        tank.setParent(tank.getParent().getNeighbor(Direction.Below));
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).setFieldEntity(new RockAndDirt(4200));
        Assert.assertTrue(action.toTunneler(tank.getId()));

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).clearField();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingIntoOpenSubSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Below));
        tank.getParent().getNeighbor(Direction.Below).setTankEntity(tank);
        tank.getParent().clearTankItem();
        tank.setParent(tank.getParent().getNeighbor(Direction.Below));
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).setFieldEntity(new RockAndDirt(3));

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).clearField();
        repo.leave(tank.getId());
    }

    @Test
    public void MovingIntoOpenSubBeneathTunnelSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Below));
        tank.getParent().getNeighbor(Direction.Below).setTankEntity(tank);
        tank.getParent().clearTankItem();
        tank.setParent(tank.getParent().getNeighbor(Direction.Below));
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).setFieldEntity(new RockAndDirt(4));

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).clearField();
        repo.leave(tank.getId());
    }

    //End Non-Timed Tests

    //Timed Tests handles all tank states:

    @Test
    public void TurnImmediatelyAfterTurningFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        Assert.assertTrue(action.turn(tank.getId(), Direction.Right));
        Assert.assertFalse(action.turn(tank.getId(), Direction.Up));

        //tunneler test
        Assert.assertTrue(action.toTunneler(tank.getId()));
        TimeUnit.MILLISECONDS.sleep(250);
        Assert.assertTrue(action.turn(tank.getId(), Direction.Right));
        Assert.assertFalse(action.turn(tank.getId(), Direction.Up));

        //ship test
        Assert.assertTrue(action.toTunneler(tank.getId()));
        TimeUnit.MILLISECONDS.sleep(250);
        tank.getParent().setFieldEntity(new Coast());
        Assert.assertTrue(action.toShip(tank.getId()));
        Assert.assertTrue(action.turn(tank.getId(), Direction.Right));
        Assert.assertFalse(action.turn(tank.getId(), Direction.Up));

        repo.leave(tank.getId());
    }

    @Test
    public void TurnWaitingHalfAllowedTurnIntervalsAfterTurningFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        Assert.assertTrue(action.turn(tank.getId(), Direction.Right));
        TimeUnit.MILLISECONDS.sleep((tank.getAllowedMoveInterval())/2);
        Assert.assertFalse(action.turn(tank.getId(), Direction.Up));

        //tunneler test
        Assert.assertTrue(action.toTunneler(tank.getId()));
        TimeUnit.MILLISECONDS.sleep(250);
        Assert.assertTrue(action.turn(tank.getId(), Direction.Right));
        TimeUnit.MILLISECONDS.sleep((tank.getAllowedTurnInterval())/2);
        Assert.assertFalse(action.turn(tank.getId(), Direction.Up));

        //ship test
        Assert.assertTrue(action.toTunneler(tank.getId()));
        TimeUnit.MILLISECONDS.sleep(250);
        tank.getParent().setFieldEntity(new Coast());
        Assert.assertTrue(action.toShip(tank.getId()));
        Assert.assertTrue(action.turn(tank.getId(), Direction.Right));
        TimeUnit.MILLISECONDS.sleep((tank.getAllowedTurnInterval())/2);
        Assert.assertFalse(action.turn(tank.getId(), Direction.Up));


        repo.leave(tank.getId());
    }

    @Test
    public void TurnWaitingAllowedTurnIntervalAfterTurningSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        Assert.assertTrue(action.turn(tank.getId(), Direction.Right));
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedMoveInterval());
        Assert.assertTrue(action.turn(tank.getId(), Direction.Up));

        //tunneler test
        Assert.assertTrue(action.toTunneler(tank.getId()));
        TimeUnit.MILLISECONDS.sleep(250);
        Assert.assertTrue(action.turn(tank.getId(), Direction.Right));
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedTurnInterval());
        Assert.assertTrue(action.turn(tank.getId(), Direction.Up));

        //ship test
        Assert.assertTrue(action.toTunneler(tank.getId()));
        TimeUnit.MILLISECONDS.sleep(250);
        tank.getParent().setFieldEntity(new Coast());
        Assert.assertTrue(action.toShip(tank.getId()));
        Assert.assertTrue(action.turn(tank.getId(), Direction.Right));
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedTurnInterval());
        Assert.assertTrue(action.turn(tank.getId(), Direction.Up));

        repo.leave(tank.getId());
    }

    @Test
    public void MoveImmediatelyAfterMovingFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        Assert.assertFalse(action.move(tank.getId(), Direction.Down));


        //tunneler test
        Assert.assertTrue(action.toTunneler(tank.getId()));
        TimeUnit.MILLISECONDS.sleep(250);
        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        Assert.assertFalse(action.move(tank.getId(), Direction.Down));

        //ship test
        Assert.assertTrue(action.toTunneler(tank.getId()));
        TimeUnit.MILLISECONDS.sleep(250);
        tank.getParent().setFieldEntity(new Coast());
        Assert.assertTrue(action.toShip(tank.getId()));
        tank.getParent().getNeighbor(Direction.Up).setFieldEntity(new Coast());
        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        Assert.assertFalse(action.move(tank.getId(), Direction.Down));

        repo.leave(tank.getId());
    }

    @Test
    public void MoveWaitingHalfAllowedMoveIntervalAfterMoveFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        TimeUnit.MILLISECONDS.sleep((tank.getAllowedMoveInterval())/2);
        Assert.assertFalse(action.move(tank.getId(), Direction.Down));


        //tunneler test
        Assert.assertTrue(action.toTunneler(tank.getId()));
        TimeUnit.MILLISECONDS.sleep(250);
        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        TimeUnit.MILLISECONDS.sleep((tank.getAllowedMoveInterval())/2);
        Assert.assertFalse(action.move(tank.getId(), Direction.Down));

        //ship test
        Assert.assertTrue(action.toTunneler(tank.getId()));
        TimeUnit.MILLISECONDS.sleep(250);
        tank.getParent().setFieldEntity(new Coast());
        Assert.assertTrue(action.toShip(tank.getId()));
        tank.getParent().getNeighbor(Direction.Up).setFieldEntity(new Coast());
        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        TimeUnit.MILLISECONDS.sleep((tank.getAllowedMoveInterval())/2);
        Assert.assertFalse(action.move(tank.getId(), Direction.Down));

        repo.leave(tank.getId());
    }

    @Test
    public void MoveWaitingAllowedMoveIntervalAfterMoveSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedMoveInterval());
        Assert.assertTrue(action.move(tank.getId(), Direction.Down));

        //tunneler test
        Assert.assertTrue(action.toTunneler(tank.getId()));
        TimeUnit.MILLISECONDS.sleep(250);
        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        TimeUnit.MILLISECONDS.sleep((tank.getAllowedMoveInterval()));
        Assert.assertTrue(action.move(tank.getId(), Direction.Down));

        //ship test
        Assert.assertTrue(action.toTunneler(tank.getId()));
        TimeUnit.MILLISECONDS.sleep(250);
        tank.getParent().setFieldEntity(new Coast());
        Assert.assertTrue(action.toShip(tank.getId()));
        tank.getParent().getNeighbor(Direction.Up).setFieldEntity(new Coast());
        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        TimeUnit.MILLISECONDS.sleep((tank.getAllowedMoveInterval()));
        Assert.assertTrue(action.move(tank.getId(), Direction.Down));

        repo.leave(tank.getId());
    }

    @Test
    public void FireImmediatelyAfterFireFailsAsTank() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        Assert.assertFalse(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        repo.leave(tank.getId());
    }

    @Test
    public void FireImmediatelyAfterFireFailsAsTunneler() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        Assert.assertTrue(action.toTunneler(tank.getId()));

        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        Assert.assertFalse(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        repo.leave(tank.getId());
    }

    @Test
    public void FireImmediatelyAfterFireFailsAsShip() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().setFieldEntity(new Coast());
        Assert.assertTrue(action.toShip(tank.getId()));

        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        Assert.assertFalse(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        repo.leave(tank.getId());
    }

    @Test
    public void FireWaitingHalfAllowedFireIntervalAfterFireFailsAsTank() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep((tank.getAllowedFireInterval())/2);
        Assert.assertFalse(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        repo.leave(tank.getId());
    }


    @Test
    public void FireWaitingHalfAllowedFireIntervalAfterFireFailsAsTunneler() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        Assert.assertTrue(action.toTunneler(tank.getId()));

        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep((tank.getAllowedFireInterval())/2);
        Assert.assertFalse(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        repo.leave(tank.getId());
    }


    @Test
    public void FireWaitingHalfAllowedFireIntervalAfterFireFailsAsShip() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().setFieldEntity(new Coast());
        Assert.assertTrue(action.toShip(tank.getId()));

        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep((tank.getAllowedFireInterval())/2);
        Assert.assertFalse(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        repo.leave(tank.getId());
    }


    @Test
    public void FireWaitingAllowedFireIntervalAfterFireSucceedsAsTank() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedFireInterval());
        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        repo.leave(tank.getId());
    }

    @Test
    public void FireWaitingAllowedFireIntervalAfterFireSucceedsAsTunneler() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        Assert.assertTrue(action.toTunneler(tank.getId()));

        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep((tank.getAllowedFireInterval()));
        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        repo.leave(tank.getId());
    }


    @Test
    public void FireWaitingAllowedFireIntervalAfterFireSucceedsAsShip() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().setFieldEntity(new Coast());
        Assert.assertTrue(action.toShip(tank.getId()));

        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep((tank.getAllowedFireInterval()));
        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        repo.leave(tank.getId());
    }

    @Test
    public void TankMovingOntoHillExperiencesCorrectDelay() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).setFieldEntity(new Hill());
        Assert.assertTrue(tank.getParent().getNeighbor(Direction.Up).isPresent());
        Assert.assertTrue(tank.getParent().getNeighbor(Direction.Up).getEntity() instanceof Hill);

        long millis = System.currentTimeMillis();
        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        long millis2 = System.currentTimeMillis();
        millis += tank.getAllowedMoveInterval();

        //Assert current time is "AllowedMoveInterval" more than start time
        Assert.assertTrue(millis2 >= millis);
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedMoveInterval());
        Assert.assertTrue(action.move(tank.getId(), Direction.Down));
        repo.leave(tank.getId());
    }

    @Test
    public void TankMovingIntoDebrisFieldHasNoDelay() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().getNeighbor(Direction.Up).setFieldEntity(new DebrisField(false));
        Assert.assertTrue(tank.getParent().getNeighbor(Direction.Up).isPresent());
        Assert.assertTrue(tank.getParent().getNeighbor(Direction.Up).getEntity() instanceof DebrisField);

        long millis = System.currentTimeMillis();
        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        long millis2 = System.currentTimeMillis();
        millis += tank.getAllowedMoveInterval();

        Assert.assertFalse(millis2 >= millis);
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedMoveInterval());
        Assert.assertTrue(action.move(tank.getId(), Direction.Down));
        repo.leave(tank.getId());
    }

    @Test
    public void TunnelerCannotFireSideways() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        Assert.assertTrue(action.toTunneler(tank.getId()));

        Assert.assertFalse(action.fire(tank.getId(), bulletType, Direction.toByte(Direction.Down)));
        Assert.assertFalse(action.fire(tank.getId(), bulletType, Direction.toByte(Direction.Left)));
        Assert.assertFalse(action.fire(tank.getId(), bulletType, Direction.toByte(Direction.Right)));
        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(Direction.Up)));

    }

    @Test
    public void ShipCanFireSideways() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        tank.getParent().setFieldEntity(new Coast());
        Assert.assertTrue(action.toShip(tank.getId()));

        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(tank.getDirection())));
        TimeUnit.MILLISECONDS.sleep((tank.getAllowedFireInterval()));
        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(Direction.Down)));
        TimeUnit.MILLISECONDS.sleep((tank.getAllowedFireInterval()));
        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(Direction.Left)));
        TimeUnit.MILLISECONDS.sleep((tank.getAllowedFireInterval()));
        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(Direction.Right)));

    }

    @Test
    public void TankCannotFireSideways() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        Assert.assertFalse(action.fire(tank.getId(), bulletType, Direction.toByte(Direction.Down)));
        Assert.assertFalse(action.fire(tank.getId(), bulletType, Direction.toByte(Direction.Left)));
        Assert.assertFalse(action.fire(tank.getId(), bulletType, Direction.toByte(Direction.Right)));
        Assert.assertTrue(action.fire(tank.getId(), bulletType, Direction.toByte(Direction.Up)));

    }


    //End Timed Tests

    //Eject and Reentry Tests:

    @Test
    public void EjectWhileSoldierIsOutFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        Assert.assertFalse(action.eject(tank.getId()));
        repo.leave(tank.getId());
    }

    @Test
    public void EjectImmediatelyAfterJoiningGameFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        Assert.assertFalse(action.eject(tank.getId()));
        repo.leave(tank.getId());
    }

    @Test
    public void EjectWaitingHalfAllowedEjectIntervalAfterJoiningGameFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval()/2);
        Assert.assertFalse(action.eject(tank.getId()));
        repo.leave(tank.getId());
    }

    @Test
    public void EjectWaitingAllowedEjectIntervalAfterJoiningGameSucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        repo.leave(tank.getId());
    }

    @Test
    public void EjectImmediatelyAfterReentryFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        Assert.assertTrue(action.move(tank.getId(), Direction.Down));
        Assert.assertFalse(action.eject(tank.getId()));
        repo.leave(tank.getId());
    }

    @Test
    public void EjectWaitingHalfAllowedEjectIntervalAfterReentryFails() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        Assert.assertTrue(action.move(tank.getId(), Direction.Down));
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval()/2);
        Assert.assertFalse(action.eject(tank.getId()));
        repo.leave(tank.getId());
    }

    @Test
    public void EjectWaitingAllowedEjectIntervalAfterReentrySucceeds() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        Assert.assertTrue(action.move(tank.getId(), Direction.Down));
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        repo.leave(tank.getId());
    }

    @Test
    public void ReentryResetsSoldierHealth() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        tank.getSoldier().setLife(15);
        Assert.assertTrue(tank.getSoldier().getLife() == 15);
        Assert.assertTrue(action.move(tank.getId(), Direction.Down));
        Assert.assertTrue(tank.getSoldier().getLife() == 25);
        repo.leave(tank.getId());
    }

    @Test
    public void SoldierHealthStillMaxAfterSecondEject() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        tank.getSoldier().setLife(15);
        Assert.assertTrue(tank.getSoldier().getLife() == 15);
        Assert.assertTrue(action.move(tank.getId(), Direction.Down));
        Assert.assertTrue(tank.getSoldier().getLife() == 25);
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        assertTrue(action.eject(tank.getId()));
        assertTrue(tank.getSoldier().getLife() == 25);
        repo.leave(tank.getId());
    }

    //End Eject and Reentry Tests

    //Transformation

    @Test
    public void TankCanTransformIntoTunneler() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);

        Assert.assertTrue(action.toTunneler(tank.getId()));
        repo.leave(tank.getId());
    }

    @Test
    public void TankCanTransformIntoShip() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getParent());
        tank.getParent().setFieldEntity(new Coast());

        Assert.assertTrue(action.toShip(tank.getId()));
        repo.leave(tank.getId());
    }

    //End Transformation

    //Move up and below level

    @Test
    public void TunnelerCanDigDownAndMoveUp() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        Assert.assertTrue(action.toTunneler(tank.getId()));
        TimeUnit.MILLISECONDS.sleep(250);

        Assert.assertTrue(action.drill(tank.getId()));
        //Assert.assertFalse(action.drill(tank.getId()));
        TimeUnit.MILLISECONDS.sleep(2000);
        Assert.assertTrue(action.move(tank.getId(), Direction.Above));
        repo.leave(tank.getId());

    }

    @Test
    public void TunnelerCannotDrillRock() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        Assert.assertTrue(action.toTunneler(tank.getId()));
        TimeUnit.MILLISECONDS.sleep(250);

        tank.getParent().getNeighbor(Direction.Below).setFieldEntity(new RockAndDirt());
        //Assert.assertTrue(action.drill(tank.getId()));
        Assert.assertFalse(action.drill(tank.getId()));
        repo.leave(tank.getId());


    }

    @Test
    public void TankCanMoveThroughHoles() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        Assert.assertFalse(action.drill(tank.getId()));

        tank.getParent().setFieldEntity(new Tunnel());
        Assert.assertTrue(action.move(tank.getId(), Direction.Below));
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedMoveInterval());
        Assert.assertTrue(action.move(tank.getId(), Direction.Above));

        repo.leave(tank.getId());

    }

    @Test
    public void TunnelerTakesThreeSecondsToDrillAboveEmpty() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        Assert.assertTrue(action.toTunneler(tank.getId()));
        tank.getParent().getNeighbor(Direction.Below).setFieldEntity(new RockAndDirt(3));

        long millis = System.currentTimeMillis();
        Assert.assertTrue(action.drill(tank.getId()));
        long millis2 = System.currentTimeMillis();
        System.out.println("Delay: " + (millis2 - millis));
        Assert.assertTrue(((millis2 - millis) >= 2000) && ((millis2 - millis) <= 2005));
        repo.leave(tank.getId());
    }

    @Test
    public void TunnelerTakesThreeSecondsToDrillAboveDirt() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        Assert.assertTrue(action.toTunneler(tank.getId()));
        tank.getParent().getNeighbor(Direction.Below).setFieldEntity(new RockAndDirt(4200));

        long millis = System.currentTimeMillis();
        Assert.assertTrue(action.drill(tank.getId()));
        long millis2 = System.currentTimeMillis();
        System.out.println("Delay: " + (millis2 - millis));
        Assert.assertTrue(((millis2 - millis) >= 2000) && ((millis2 - millis) <= 2005));
        repo.leave(tank.getId());
    }

    @Test
    public void TunnelerTanksTwoSecondsToEnterDirt() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));
        Assert.assertTrue(action.toTunneler(tank.getId()));
        tank.getParent().getNeighbor(Direction.Up).setFieldEntity(new RockAndDirt(4200));

        long millis = System.currentTimeMillis();
        Assert.assertTrue(action.move(tank.getId(), Direction.Up));
        long millis2 = System.currentTimeMillis();
        System.out.println("Delay: " + (millis2 - millis));
        Assert.assertTrue(((millis2 - millis) >= 2000) && ((millis2 - millis) <= 2005));
        repo.leave(tank.getId());
    }


}
