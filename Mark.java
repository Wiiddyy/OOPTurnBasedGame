import java.util.List;

public class Mark extends CharacterClass { // Extends the modified CharacterClass

    public Mark() {
        super("Mark", 140, 100, List.of(
            new Skill ("Forgotten Assignment", 1000, 10, "None"),
            new Skill ("Crashout", 28, 15, "None"),
            new Skill ("Last-Minute Cram Attack", 40, 25, "effect")
        ));
    }

    @Override
    public String characterDescription() {
        return "Specializes in last-minute efforts.";
    }
}