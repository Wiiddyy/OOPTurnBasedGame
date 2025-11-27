import java.util.List;

public class Sherwin extends CharacterClass {

    public Sherwin() {
        super("Sherwin", 225, 120, List.of(
            new Skill ("Notebook Shied Bash", 18, 10, "None"),
            new Skill ("Desk Slam", 30, 20, "effect"),
            new Skill ("Hallway Roar", 32, 25, "effect")
        ));
    }

    @Override
    public String characterDescription() {
        return "A formidable presence, tanking hits and intimidating foes.";
    }
}