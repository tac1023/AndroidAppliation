package edu.unh.cs.cs619.bulletzone;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.unh.cs.cs619.bulletzone.ui.Battlefield;
import edu.unh.cs.cs619.bulletzone.ui.BulletItem;
import edu.unh.cs.cs619.bulletzone.ui.FieldEntity;
import edu.unh.cs.cs619.bulletzone.ui.FieldItem;

/**
 * Created by Jason Vettese on 4/1/2018.
 *
 * Tests to ensure that all of the objects are properly created on the board.
 */

public class GameBoardUpdateTest {

    @Test
    public void BoardContainsAllEmpties() {
        Battlefield b = new Battlefield();
        for( int i = 0; i < 256; i++) {
            b.setFieldEntity(i, new FieldEntity());
            assertEquals((int)b.getFieldEntity(i).getResourceID(), R.drawable.blank );
        }

    }

    @Test
    public void BoardContainsABullet() {
        Battlefield b = new Battlefield();
        for( int i = 0; i < 256; i++) {
            b.setFieldEntity(i, new FieldEntity());
            //assertEquals((int)b.getFieldEntity(i).getResourceID(), R.drawable.blank );
        }
        b.setFieldEntity(0, new BulletItem(2000000, 100, 0,0));
        int i = b.getFieldEntity(0).getResourceID();
        assertEquals( (int)i, R.drawable.bullet);
        //assertTrue(i == R.drawable.bullet);
    }

    @Test
    public void BoardContainsAWall() {
        Battlefield b = new Battlefield();
        for( int i = 0; i < 256; i++) {
            b.setFieldEntity(i, new FieldEntity());
            //assertEquals((int)b.getFieldEntity(i).getResourceID(), R.drawable.blank );
        }
        b.setFieldEntity(0, new FieldItem(1001, 100, 0,0));
        int i = b.getFieldEntity(0).getResourceID();
        assertEquals( i, R.drawable.wall);
    }

    @Test
    public void BoardContainsAnIndestructibleWall() {
        Battlefield b = new Battlefield();
        for( int i = 0; i < 256; i++) {
            b.setFieldEntity(i, new FieldItem());
            //assertEquals((int)b.getFieldEntity(i).getResourceID(), R.drawable.blank );
        }
        b.setFieldEntity(0, new FieldItem(1000, 100, 0,0));
        int i = b.getFieldEntity(0).getResourceID();
        //System.out.println(b.getFieldEntity(0).getResourceID());
        //System.out.println(i);
        //System.out.println(R.drawable.immortal_wall);
        assertEquals( i, R.drawable.immortal_wall);
    }
    @Test
    public void BoardContainsAHill() {
        Battlefield b = new Battlefield();
        for( int i = 0; i < 256; i++) {
            b.setFieldEntity(i, new FieldEntity());
            //assertEquals((int)b.getFieldEntity(i).getResourceID(), R.drawable.blank );
        }
        b.setFieldEntity(0, new FieldItem(2000, 100, 0,0));
        int i = b.getFieldEntity(0).getResourceID();
        assertEquals( i, R.drawable.hill);
    }
    @Test
    public void BoardContainsAForest() {
        Battlefield b = new Battlefield();
        for( int i = 0; i < 256; i++) {
            b.setFieldEntity(i, new FieldEntity());
            //assertEquals((int)b.getFieldEntity(i).getResourceID(), R.drawable.blank );
        }
        b.setFieldEntity(0, new FieldItem(3001, 100, 0,0));
        int i = b.getFieldEntity(0).getResourceID();
        assertEquals( i, R.drawable.forest);
    }
}
