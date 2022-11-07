import characters.Enemy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestEnemy {
    private Enemy enemy;

    @Before
    public void init() {
        enemy = new Enemy((inventory, character) -> {});
    }

    @Test
    public void a() {
        enemy.damageEquippedWeapon();
    }

    @Test
    public void testDamageItem() {

    }
}
