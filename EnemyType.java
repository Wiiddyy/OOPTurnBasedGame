import java.util.List;

// Enum containing all enemy types of the game, each enemy has its own stats and skills
public enum EnemyType {
    JUNIOR("Junior", 100,80, List.of(
        new Skill("Slash", 20, 5, "None"),
        new Skill("Bleed", 15, 10, "Bleed"),
        new Skill("Charge", 25, 15, "Stun")
    )),
    SENIOR("Senior", 125, 40, List.of(
        new Skill("Smash", 25, 10, "Stun"),
        new Skill("Bleed", 20, 10, "Bleed")
    )),
    BOSS("Boss", 200, 50, List.of(
        new Skill("Smash", 30, 10, "Stun"),
        new Skill("Fireball", 35, 15, "Burn")
    ));

    // Stats stored in enum
    private final String name;
    private final int maxHp;
    private final int maxMana;
    private final List<Skill> skills;

    // Enum constructor
    EnemyType(String name, int maxHp, int maxMana, List<Skill> skills) {
        this.name = name;
        this.maxHp = maxHp;
        this.maxMana = maxMana;
        this.skills = skills;
    }

    // Creates a new Enemy object based on this type
    public Enemy create() {
        Enemy e = new Enemy(name, maxHp, 0, skills); // old constructor: maxHp, attack placeholder 0, skills
        e.setMaxMana(maxMana); // set enemy's max mana
        e.setMana(maxMana);    // current mana = max mana
        return e;
    }
}
