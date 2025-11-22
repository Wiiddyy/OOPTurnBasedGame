import java.util.List;

public enum CharacterClass {
    MARK("Mark", 120, 40, List.of(
        new Skill("Analytical Strike", 10, 5, "None"),
        new Skill("Crypto Drain", 15, 10, "Weaken"),
        new Skill("High-Risk Charge", 80, 0, "Stun")
    )),
    MICO ("Micogwapo", 100, 100, List.of(
        new Skill("OTIN DAKO", 30, 20, "Burn"),
        new Skill("Frozen Domain", 20, 15, "Freeze"),
        new Skill("Shadow Step", 25, 10, "None")
    )),
    JENO ("Jeno", 100, 50, List.of(
        new Skill("Sand Shot", 10, 5, "None"),
        new Skill("Toxic Quirk", 20, 10, "Poison"),
        new Skill("Desert Mirage", 30, 20, "Confuse")
    )),
    SHERWIN("Sherwin", 150, 70, List.of(
        new Skill("Ritual Slam", 25, 15, "Stun"),
        new Skill("Discipline Shield", 15, 10, "Freeze"),
        new Skill("Cult's Wrath", 30, 20, "Fear")
    )),
    CLYDE("Clyde", 120, 40, List.of(
        new Skill("Silent Slash", 10, 5, "None"),
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
    // bayot si mark
    // Getters
    public String getDisplayName() { return displayName; }
    public int getMaxHp() { return maxHp; }
    public int getMaxMana() { return maxMana; }
    public List<Skill> getSkills() { return skills; }
}
