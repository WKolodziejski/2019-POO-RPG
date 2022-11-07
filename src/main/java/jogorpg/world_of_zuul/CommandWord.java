package jogorpg.world_of_zuul;

/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */
public enum CommandWord {
    UNKNOWN("?"),
    GO("go"),
    QUIT("quit"),
    HELP("help"),
    ME("me"),

    ATTACK("attack"),
    USE("use"),
    EQUIP("equip"),
    UNEQUIP("unequip"),
    EQUIPPED("equipped"),

    OPEN("open"),
    BUY("buy"),
    SELL("sell"),
    REPAIR("repair"),
    DESTROY("destroy"),

    TAKE("take"),
    PICK("pick"),
    DROP("drop"),
    INVENTORY("inventory"),

    LOOK("look");

    private String commandString;

    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    public String toString() {
        return commandString;
    }

}

