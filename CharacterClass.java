import java.util.List;

public enum CharacterClass {
    MARK("Mark", 120, 30, List.of(
        new Skill("Analytical Strike", 20, 5, "None"),
        new Skill("Crypto Drain", 15, 10, "Weaken"),
        new Skill("High-Risk Charge", 25, 15, "Stun")
    )),
    MICO ("Mico", 80, 100, List.of(
        new Skill("Flame Shuriken", 30, 20, "Burn"),
        new Skill("Frozen Domain", 20, 15, "Freeze"),
        new Skill("Shadow Step", 25, 10, "None")
    )),
    JENO ("Jeno", 100, 50, List.of(
        new Skill("Sand Shot", 15, 5, "None"),
        new Skill("Toxic Quirk", 10, 10, "Poison"),
        new Skill("Desert Mirage", 20, 15, "Confuse")
    )),
    SHERWIN("Sherwin", 100, 50, List.of(
        new Skill("Ritual Slam", 30, 15, "Stun"),
        new Skill("Discipline Shield", 15, 10, "Freeze"),
        new Skill("Cult's Wrath", 30, 20, "Fear")
    )),
    CLYDE("Clyde", 120, 30, List.of(
        new Skill("Silent Slash", 20, 5, "None"),
        new Skill("Soul Bleed", 15, 10, "Bleed"),
        new Skill("Preemptive Strike", 25, 15, "Stun")
    ));

    private final String displayName;
    private final int maxHp;
    private final int maxMana;
    private final List<Skill> skills;

    CharacterClass(String displayName, int maxHp, int maxMana, List<Skill> skills) {
        this.displayName = displayName;
        this.maxHp = maxHp;
        this.maxMana = maxMana;
        this.skills = skills;
    }

    // Getters
    public String getDisplayName() { return displayName; }
    public int getMaxHp() { return maxHp; }
    public int getMaxMana() { return maxMana; }
    public List<Skill> getSkills() { return skills; }
}
