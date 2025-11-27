import java.util.List;

public class Mico extends CharacterClass {

    public Mico() {
        super("Mico", 115, 90, List.of(
            new Skill ("Crazy Spin", 28, 16, "None"),
            new Skill ("Paper Cutter Swipe", 20, 12, "effect"),
            new Skill ("Backpack Uppercut", 38, 25, "effect")
        ));
    }

    @Override
    public String characterDescription() {
        return "Excels in close-quarters combat with everyday items.";
    }
}