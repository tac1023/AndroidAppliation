package edu.unh.cs.cs619.bulletzone.repository;

/**
 * Created by Jason Vettese on 4/14/2018.
 *
 * This test is duplicate and is covered better in the
 * TankActionTest
 */
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
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Soldier;
import edu.unh.cs.cs619.bulletzone.model.Tank;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.tank_action.Action;
import edu.unh.cs.cs619.bulletzone.tank_action.TankAction;

@RunWith(MockitoJUnitRunner.class)
public class SoldierFunctionalityTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @InjectMocks
    InMemoryGameRepository repo;

    @Test
    public void soldierIsCreated() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());
        Assert.assertNotNull(tank.getParent().getNeighbor(Direction.Up));

        Action action = new Action();
        TimeUnit.MILLISECONDS.sleep(tank.getAllowedEjectInterval());
        Assert.assertTrue(action.eject(tank.getId()));
        Assert.assertFalse(action.eject(tank.getId()));


    }


}
