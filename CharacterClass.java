import java.util.List;

public enum CharacterClass {
    MARK("Mark", 120, 30, List.of(
        new Skill("Slash", 20, 5, "None"),
        new Skill("Bleed", 15, 10, "Bleed"),
        new Skill("Charge", 25, 15, "Stun")
    )),
    MICO ("Mico", 80, 100, List.of(
        new Skill("Ninjutsu", 30, 20, "Burn"),
        new Skill("Domain Expansion", 20, 15, "Freeze")
    )),
    JENO ("Jeno", 100, 50, List.of(
        new Skill("Arrow Shot", 15, 5, "None"),
        new Skill("Poison Arrow", 10, 10, "Poison")
    )),
    SHERWIN("Sherwin", 100, 50, List.of(
        new Skill("Rock Slam", 40, 15, "Stun"),
        new Skill("Rock Solid", 15, 10, "Freeze")
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
