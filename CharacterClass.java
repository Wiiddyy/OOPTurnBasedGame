import java.util.ArrayList;
import java.util.List;

/*
    FULLY INHERITANCE NANI IM SO FUCKING SUREEE !! PLEASE SEND HELP
 */

public abstract class CharacterClass { // made abstract B))

    protected final String name;
    protected final int maxHp;
    protected final int maxMana;
    protected final List<Skill> skills;

    // protcted constructors para ma gamit sa subclasses
    protected CharacterClass(String name, int maxHp, int maxMana, List<Skill> skills) {
        this.name = name;
        this.maxHp = maxHp;
        this.maxMana = maxMana;
        this.skills = skills;
    }

    // Getters
    public String getName() { return name; }
    public int getMaxHp() { return maxHp; }
    public int getMaxMana() { return maxMana; }
    public List<Skill> getSkills() { return skills; }

    /*needed para ma-set nimo nga template lang ni siya, dili pwede directly gamiton,
    ug aron subclasses mopuno sa specific behavior.*/

    public abstract String characterDescription();

    @Override
    public String toString() {
        return name + " HP: " + maxHp + " Mana: " + maxMana;
    }

    public static List<CharacterClass> getAllCharacters() {
        List<CharacterClass> all = new ArrayList<>();
        all.add(new Mark());
        all.add(new Jeno());
        all.add(new Clyde());
        all.add(new Mico());
        all.add(new Sherwin());
        return all;
    }
}