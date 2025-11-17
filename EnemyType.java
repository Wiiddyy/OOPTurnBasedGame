import java.util.List;


// gi enum to contain all enemy type of the game, each enemy has their own stats and skills.
public enum  EnemyType {
    JUNIOR("Junior", 120, 30, List.of(
        new Skill("Slash", 20, 5, "None"),
        new Skill("Bleed", 15, 10, "Bleed"),
        new Skill("Charge", 25, 15, "Stun")
    )),
    SENIOR("Senior", 120, 30, List.of(
        new Skill("Slash", 20, 5, "None"),
        new Skill("Bleed", 15, 10, "Bleed"),
        new Skill("Charge", 25, 15, "Stun")
    )),
    BOSS("Boss", 120, 30, List.of(
        new Skill("Slash", 20, 5, "None"),
        new Skill("Bleed", 15, 10, "Bleed"),
        new Skill("Charge", 25, 15, "Stun")
    ));


    // stats stored in enum
    String name;
    int hp;
    int mana;
    List<Skill> skills;

    EnemyType(String name, int hp, int mana, List<Skill> skills) {
        this.name = name;
        this.hp = hp;
        this.mana = mana;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMana() {
        return hp;
    }

    public List<Skill> getSkills() {
        return skills;
    }


    // creates new Enemy based on this type
    public Enemy create() {
        return new Enemy(name, hp, mana, skills);
    }
}