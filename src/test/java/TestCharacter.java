import characters.Boss;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCharacter {
    private static Boss boss;

    @BeforeAll
    static void init() {
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
