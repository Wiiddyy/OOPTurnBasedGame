import java.util.List;

public class Jeno extends CharacterClass {

    public Jeno() {
        super("Jeno", 160, 85, List.of(
            new Skill ("Double Checker", 22, 10, "None"),
            new Skill ("Exam Panic Surge", 35, 20, "effect"),
            new Skill ("Brain Overload Smash", 45, 30, "effect")
        ));
    }

    @Override
    public String characterDescription() {
        return "Known for careful planning and sudden bursts of energy.";
    }
}