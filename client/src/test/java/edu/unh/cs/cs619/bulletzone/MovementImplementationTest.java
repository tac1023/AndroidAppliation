package edu.unh.cs.cs619.bulletzone;

import android.os.Bundle;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.unh.cs.cs619.bulletzone.control.ButtonHandler;
import edu.unh.cs.cs619.bulletzone.ui.FieldEntity;
import edu.unh.cs.cs619.bulletzone.ui.FieldEntityFactory;
import edu.unh.cs.cs619.bulletzone.ui.TankItem;

/**
 * Created by Jason Vettese on 4/1/2018.
 */
public class MovementImplementationTest {

    @Test
    public void CreatesTankAndValidatesUp() {
        FieldEntityFactory factory = FieldEntityFactory.getInstance(270);
        int i = factory.makeEntity(10000000, 2,2).getResourceID();
        assertEquals(i, R.drawable.tank_other_up);
    }

    @Test
    public void CreatesTankAndValidatesDown() {
        FieldEntityFactory factory = FieldEntityFactory.getInstance(270);
        int i = factory.makeEntity(10000004, 2,2).getResourceID();
        assertEquals(i, R.drawable.tank_other_down);
    }

    @Test
    public void CreatesTankAndValidatesRight() {
        FieldEntityFactory factory = FieldEntityFactory.getInstance(270);
        int i = factory.makeEntity(10000002, 2,2).getResourceID();
        assertEquals(i, R.drawable.tank_other_right);
    }

    @Test
    public void CreatesTankAndValidatesLeft() {
        FieldEntityFactory factory = FieldEntityFactory.getInstance(270);
        int i = factory.makeEntity(10000006, 2,2).getResourceID();
        assertEquals(i, R.drawable.tank_other_left);
    }

    @Test
    public void UpButtonTankMovesUpward(){
        FieldEntityFactory factory = FieldEntityFactory.getInstance(10000000);
        //FieldEntity entity = factory.makeEntity(10000000, 2,2);
        TankItem tank = (TankItem)factory.makeEntity(10000000, 2, 2);
        factory.makeEntity(0, 2, 2);
        tank = (TankItem)factory.makeEntity(10000000, 2, 1);
        int tankId = tank.getResourceID();
        assertEquals(tankId, R.drawable.tank_other_up );
    }

    @Test
    public void DownButtonTanksMovesDownward() {
        FieldEntityFactory factory = FieldEntityFactory.getInstance(10000000);
        //FieldEntity entity = factory.makeEntity(10000000, 2,2);
        TankItem tank = (TankItem)factory.makeEntity(10000000, 2, 2);
        factory.makeEntity(0, 2, 2);
        tank = (TankItem)factory.makeEntity(10000000, 2, 3);
        int tankId = tank.getResourceID();
        assertEquals(tankId, R.drawable.tank_other_up );
    }

    @Test
    public void RightButtonRotatesTankRight() {
        FieldEntityFactory factory = FieldEntityFactory.getInstance(10000000);
        //FieldEntity entity = factory.makeEntity(10000000, 2,2);
        TankItem tank = (TankItem)factory.makeEntity(10000000, 2, 2);
        tank = (TankItem)factory.makeEntity(10000002, 2, 2);
        int tankId = tank.getResourceID();
        assertEquals(tankId, R.drawable.tank_other_right );
    }

    @Test
    public void LeftButtonRotatesTankLeft() {
        FieldEntityFactory factory = FieldEntityFactory.getInstance(10000000);
        //FieldEntity entity = factory.makeEntity(10000000, 2,2);
        TankItem tank = (TankItem)factory.makeEntity(10000000, 2, 2);
        tank = (TankItem)factory.makeEntity(10000006, 2, 2);
        int tankId = tank.getResourceID();
        assertEquals(tankId, R.drawable.tank_other_left );
    }

}

