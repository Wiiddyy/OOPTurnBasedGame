import java.util.List;

public class Player extends Entity {

    public Player(String name, int maxHp, int maxMana, int attack, List<Skill> skills) {
        super(
            name,
            maxHp,
            maxMana,
            0, // ang attack ni, 0 for now
            skills
        );
    }

    // Choose skill (override abstarct method from entity)
    @Override
    public Skill chooseAction(Entity target) {
        System.out.println("\nYour Skills:");
        for (int i = 0; i < skills.size(); i++) {
            System.out.println((i + 1) + ". " + skills.get(i));
        }

        System.out.print("Choose a skill: ");
        int choice;

        try {
            choice = Integer.parseInt(new java.util.Scanner(System.in).nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input!"); {
                return null;
            }
        }

        Skill chosen = skills.get(choice - 1);

        if (mana < chosen.getManaCost()) {
            System.out.println("Not enough mana!");
            return null;
        }

        return chosen;
    }
}
