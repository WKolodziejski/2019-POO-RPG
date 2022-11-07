import characters.Boss;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestCharacter {
    private Boss boss;

    @Before
    public void init() {
        boss = new Boss((inventory, character) -> {});
    }

    @Test
    public void assertBossNameEqualsZabuzaMomochiODemonioDoGasOculto() {
        assertEquals(boss.getName(), "Zabuza Momochi, o demônio do gás oculto");
    }

    @Test
    public void assertBossHasSword() {
        assertTrue(boss.getInventory().stream().anyMatch(item -> item.getName().equals("Kubikiribōchō")));
    }
}
