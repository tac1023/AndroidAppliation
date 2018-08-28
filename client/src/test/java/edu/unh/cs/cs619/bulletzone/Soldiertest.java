package edu.unh.cs.cs619.bulletzone;

/**
 * Created by luxing on 2018/4/15.
 */
import org.junit.Test;
import static org.junit.Assert.*;

import edu.unh.cs.cs619.bulletzone.ui.Battlefield;
import edu.unh.cs.cs619.bulletzone.ui.BulletItem;
import edu.unh.cs.cs619.bulletzone.ui.FieldEntity;
import edu.unh.cs.cs619.bulletzone.ui.FieldEntityFactory;
import edu.unh.cs.cs619.bulletzone.ui.FieldItem;
import edu.unh.cs.cs619.bulletzone.ui.TankItem;


public class Soldiertest {
    @Test
    public void CreatesSoliderAndValidatesUp() {
        FieldEntityFactory factory = FieldEntityFactory.getInstance(270);
        int i = factory.makeEntity(10000000, 2,2).getResourceID();
        assertEquals(i, R.drawable.tank_other_up);
    }
    @Test
    public void CreatessSoliderAndValidatesDown() {
        FieldEntityFactory factory = FieldEntityFactory.getInstance(270);
        int i = factory.makeEntity(10000004, 2,2).getResourceID();
        assertEquals(i, R.drawable.tank_other_down);
    }

    @Test
    public void CreatessSoliderAndValidatesRight() {
        FieldEntityFactory factory = FieldEntityFactory.getInstance(270);
        int i = factory.makeEntity(10000002, 2,2).getResourceID();
        assertEquals(i, R.drawable.tank_other_right);
    }

    @Test
    public void CreatesSoliderAndValidatesLeft() {
        FieldEntityFactory factory = FieldEntityFactory.getInstance(270);
        int i = factory.makeEntity(10000006, 2,2).getResourceID();
        assertEquals(i, R.drawable.tank_other_left);
    }
    @Test
    public void UpButtonoliderMovesUpward(){
        FieldEntityFactory factory = FieldEntityFactory.getInstance(10000000);
        //FieldEntity entity = factory.makeEntity(10000000, 2,2);
        TankItem tank = (TankItem)factory.makeEntity(10000000, 2, 2);
        factory.makeEntity(0, 2, 2);
        tank = (TankItem)factory.makeEntity(10000000, 2, 1);
        int tankId = tank.getResourceID();
        assertEquals(tankId, R.drawable.tank_other_up );
    }


    @Test
    public void DownButtonoliderMovesDownward() {
        FieldEntityFactory factory = FieldEntityFactory.getInstance(10000000);
        //FieldEntity entity = factory.makeEntity(10000000, 2,2);
        TankItem tank = (TankItem)factory.makeEntity(10000000, 2, 2);
        factory.makeEntity(0, 2, 2);
        tank = (TankItem)factory.makeEntity(10000000, 2, 3);
        int tankId = tank.getResourceID();
        assertEquals(tankId, R.drawable.tank_other_up );
    }

    @Test
    public void RightButtonRotatesoliderRight() {
        FieldEntityFactory factory = FieldEntityFactory.getInstance(10000000);
        //FieldEntity entity = factory.makeEntity(10000000, 2,2);
        TankItem tank = (TankItem)factory.makeEntity(10000000, 2, 2);
        tank = (TankItem)factory.makeEntity(10000002, 2, 2);
        int tankId = tank.getResourceID();
        assertEquals(tankId, R.drawable.tank_other_right );
    }

    @Test
    public void LeftButtonRotateoliderLeft() {
        FieldEntityFactory factory = FieldEntityFactory.getInstance(10000000);
        //FieldEntity entity = factory.makeEntity(10000000, 2,2);
        TankItem tank = (TankItem)factory.makeEntity(10000000, 2, 2);
        tank = (TankItem)factory.makeEntity(10000006, 2, 2);
        int tankId = tank.getResourceID();
        assertEquals(tankId, R.drawable.tank_other_left );
    }
/* above should be the same as the tank*/


}
