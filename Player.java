import java.util.List;

public class Player extends Entity {
    private int hpPotions;
    private int manaPotions;

    public Player(String name, int maxHp, int maxMana, int attack, int par1, List<Skill> skills) {
        super(
            name,
            maxHp,
            maxMana,
            attack,
            skills
        );
        this.hpPotions = 5;
        this.manaPotions = 5;
    }

    public int getHpPotions() {
        return hpPotions;
    }

    public void useHpPotion() {
        if (hpPotions > 0) {
            hpPotions--;
        }
    }

    public int getManaPotions() {
        return manaPotions;
    }

    public void useManaPotion() {
        if (manaPotions > 0) {
            manaPotions--;
        }
    }


    // Choose skill (override abstract method from entity)
    @Override
    public Skill chooseAction(Entity target) {
        System.out.println("\nYour Skills:");
        for (int i = 0; i < skills.size(); i++) {
            System.out.println((i + 1) + ". " + skills.get(i));
        }

        int choice = Menus.readIntInRange(1, skills.size(), "Choose a skill: ");
        Skill chosen = skills.get(choice - 1);

        if (mana < chosen.getManaCost()) {
            System.out.println("Not enough mana!");
            return null;
        }

        return chosen;
    }

}
