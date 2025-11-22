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
    public String getDisplayName() { return name; }
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
        .maxHp(100)
        .maxMana(60)
        .skills(List.of(
            new Skill ("Kalimot ko", 18, 10, "None")
        ))
        .build();

    public static final CharacterClass JENO = new CharacterClass.Builder()
        .name("Jeno")
        .maxHp(100)
        .maxMana(60)
        .skills(List.of(
            new Skill ("Testing", 20, 10, "None")
        ))
        .build();


    @Override
    public String toString() {
        return name + " HP: " + maxHp + " Mana: " + maxMana;
    }

    public static List<CharacterClass> getAllCharacters() {
        return List.of(MARK, JENO);
    }
}
