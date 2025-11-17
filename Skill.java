public class Skill {
    private String name;
    private int damage;
    private int manaCost;
    private String effect; // e.g., "Stun", "Bleed", "None"

    public Skill(String name, int damage, int manaCost, String effect) {
        this.name = name;
        this.damage = damage;
        this.manaCost = manaCost;
        this.effect = effect;
    }

    // Getters
    public String getName() { return name; }
    public int getDamage() { return damage; }
    public int getManaCost() { return manaCost; }
    public String getEffect() { return effect; }

    @Override
    public String toString() {
        return name + " (Damage: " + damage + ", Mana: " + manaCost + ", Effect: " + effect + ")";
    }
}