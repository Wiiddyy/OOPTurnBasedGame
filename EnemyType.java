import java.util.List;

// Enum containing all enemy types of the game, each enemy has its own stats and skills
public enum EnemyType {
    JUNIOR("Junior", 100, 80, 10, List.of(
        new Skill("Rookie Slash", 15, 10, "Bleed"),
        new Skill("Bull Charge", 25, 15, "Stun")
    )),
    SENIOR("Senior", 125, 40, 15, List.of(
        new Skill("Ground Pound", 25, 10, "Stun"),
        new Skill("Bloodletting Strike", 20, 10, "Bleed")
    )),
    BOSS("Boss", 200, 50, 20, List.of(
        new Skill("Titan Smash", 30, 10, "Stun"),
        new Skill("Inferno Orb", 35, 15, "Burn")
    ));

    // Stats stored in enum
    private final String name;
    private final int maxHp;
    private final int maxMana;
    private final int attack;
    private final List<Skill> skills;

    // Enum constructor
    EnemyType(String name, int maxHp, int maxMana, int attack, List<Skill> skills) {
        this.name = name;
        this.maxHp = maxHp;
        this.maxMana = maxMana;
        this.attack = attack;
        this.skills = skills;
    }

    // Creates a new Enemy object based on this type
    public Enemy create() {
        return new Enemy(name, maxHp, maxMana, attack, skills);
    }
}
