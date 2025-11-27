import java.util.ArrayList;
import java.util.List;

public class CharacterClass {

    private final String name;
    private final int maxHp;
    private final int maxMana;
    private final List<Skill> skills;

    private CharacterClass(Builder b) {
        this.name = b.name;
        this.maxHp = b.maxHp;
        this.maxMana = b.maxMana;
        this.skills = b.skills;
    }

    // Getters
    public String getName() { return name; }
    public int getMaxHp() { return maxHp; }
    public int getMaxMana() { return maxMana; }
    public List<Skill> getSkills() { return skills; }

    // builder class (sulod ra diri)
    public static class Builder {
        private String name;
        private int maxHp;
        private int maxMana;
        private List<Skill> skills = new ArrayList<>();


        // para i-set ang name (return this para pwede ma chain)
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        //set sa HP (again, chainable ra.. etc)
        public Builder maxHp(int maxHp) {
            this.maxHp = maxHp;
            return this;
        }

        public Builder maxMana(int maxMana) {
            this.maxMana = maxMana;
            return this;
        }

        public Builder skills(List<Skill> skills) {
            this.skills = skills;
            return this;
        }

        // Murag "finalize" mao ni ang mo build sa final CharacterClass object
        public CharacterClass build() {
            return new CharacterClass(this);
        }
    }

    public static final CharacterClass MARK = new CharacterClass.Builder()
        .name("Mark")
        .maxHp(140)
        .maxMana(100)
        .skills(List.of(
            new Skill ("Forgotten Assignment", 1000, 10, "None"),
            new Skill ("Crashout", 28, 15, "None"),
            new Skill ("Last-Minute Cram Attack", 40, 25, "effect")
        ))
        .build();

    public static final CharacterClass JENO = new CharacterClass.Builder()
        .name("Jeno")
        .maxHp(160)
        .maxMana(85)
        .skills(List.of(
            new Skill ("Double Checker", 22, 10, "None"),
            new Skill ("Exam Panic Surge", 35, 20, "effect"),
            new Skill ("Brain Overload Smash", 45, 30, "effect")
        ))
        .build();

    public static final CharacterClass CLYDE = new CharacterClass.Builder()
        .name("Clyde")
        .maxHp(80)
        .maxMana(150)
        .skills(List.of(
            new Skill ("Outsmart", 45, 35, "None"),
            new Skill ("Ruler flick", 30, 25, "effect"),
            new Skill ("Desk Smash", 60, 45, "effect")
        ))
        .build();
    
    public static final CharacterClass MICO = new CharacterClass.Builder()
        .name("Mico")
        .maxHp(115)
        .maxMana(90)
        .skills(List.of(
            new Skill ("Crazy Spin", 28, 16, "None"),
            new Skill ("Paper Cutter Swipe", 20, 12, "effect"),
            new Skill ("Backpack Uppercut", 38, 25, "effect")
        ))
        .build();

    public static final CharacterClass SHERWIN = new CharacterClass.Builder()
        .name("Sherwin")
        .maxHp(225)
        .maxMana(120)
        .skills(List.of(
            new Skill ("Notebook Shied Bash", 18, 10, "None"),
            new Skill ("Desk Slam", 30, 20, "effect"),
            new Skill ("Hallway Roar", 32, 25, "effect")
        ))
        .build();


    @Override
    public String toString() {
        return name + " HP: " + maxHp + " Mana: " + maxMana;
    }

    public static List<CharacterClass> getAllCharacters() {
        return List.of(MARK, JENO, CLYDE, MICO, SHERWIN);
    }
}
