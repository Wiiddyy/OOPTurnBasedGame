import java.util.List;

public class Clyde extends CharacterClass {

    public Clyde() {
        super("Clyde", 80, 150, List.of(
            new Skill ("Outsmart", 45, 35, "None"),
            new Skill ("Ruler flick", 30, 25, "effect"),
            new Skill ("Desk Smash", 60, 45, "effect")
        ));
    }

    @Override
    public String characterDescription() {
        return "A quick thinker with surprising agility.";
    }
}