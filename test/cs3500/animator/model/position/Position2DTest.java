package cs3500.animator.model.position;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import cs3500.animator.model.position.IPosition;
import cs3500.animator.model.position.Position2D;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class checking that {@link Position2D} methods work as intended.
 */
public class Position2DTest {

  IPosition pos1;
  IPosition pos2;

  @Before
  public void initializePositions() {
    this.pos1 = new Position2D(3, 4);
    this.pos2 = new Position2D(pos1);
  }

  @Test
  public void testGetCoordinates() {
    assertEquals(3, pos1.getX());
    assertEquals(4, pos1.getY());
    assertEquals(3, pos2.getX());
    assertEquals(4, pos2.getY());
  }

  @Test
  public void testSettingCoordinates() {
    pos1.setX(1);
    pos1.setY(2);
    assertEquals(1, pos1.getX());
    assertEquals(2, pos1.getY());
    //Testing if pos2 still has same coordinates despite pos1 being changed
    assertEquals(3, pos2.getX());
    assertEquals(4, pos2.getY());
  }

  @Test
  public void testEqualsAndHashcode() {
    assertTrue(pos1.equals(pos2));
    assertEquals(pos1.hashCode(), pos2.hashCode());
    pos1.setX(5);
    pos1.setY(6);
    assertFalse(pos1.equals(pos2));
  }

}
