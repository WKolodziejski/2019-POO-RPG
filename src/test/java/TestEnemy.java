import characters.Boss;
import characters.Enemy;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;

import java.util.logging.Logger;

public class TestEnemy {
    private static Enemy enemy;
    static final Logger logger = Logger.getLogger(TestEnemy.class.getName());

    @BeforeAll
    static void init() {
        enemy =  new Enemy((inventory, character) -> {});
    }

    @ParameterizedTest(name = "{0}")
    @ValueSource(ints = {1, 2, 3, 4})
    void parameterized(int i) {
        logger.info(String.valueOf(i));
    }

    @Test
    @DisplayName("MEU TESTE")
    @Tag("cu")
    public void a() {
        enemy.damageEquippedWeapon();
    }

    @Test
    public void testPutItem_coinBag() {
    }
}
