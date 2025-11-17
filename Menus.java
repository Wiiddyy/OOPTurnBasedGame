import java.util.Scanner;

public class Menus {
    private static final Scanner SC = new Scanner(System.in);

    public static int mainMenu() {
        System.out.println("\nMain Menu");
        System.out.println("1) Start New Game");
        System.out.println("2) How To Play");
        System.out.println("3) Exit");
        System.out.print("> ");
        return readIntInRange(1, 3);
    }

    public static int battleMenu() {
        System.out.println("\nChoose action:");
        System.out.println("1) Attack (Use Skill)");
        System.out.println("2) Defend");
        System.out.println("3) Potions");
        System.out.println("4) Run");
        System.out.print("> ");
        return readIntInRange(1, 4);
    }

    public static void howToPlay() {
        System.out.println("\nHow to play:");
        System.out.println("- Choose a character with different stats.");
        System.out.println("- Choose actions each turn in battle.");
        System.out.println("- Attack uses class skills to reduce enemy HP.");
        System.out.println("- Defend reduces incoming damage this turn.");
        System.out.println("- Items heal you.");
        System.out.println("- Try to win fights!");
        Interface.pauseLine();
    }

    public static CharacterClass chooseCharacterMenu() {
        System.out.println("\nChoose Your Character:");
        int index = 1;
        for (CharacterClass c : CharacterClass.values()) {
            // Display HP and number of skills (instead of minDmg/maxDmg/potions)
            System.out.printf("%d) %s (HP: %d, Skills: %d)%n",
                    index++, c.getDisplayName(), c.getMaxHp(), c.getSkills().size());
        }
        System.out.print("> ");
        int choice = readIntInRange(1, CharacterClass.values().length);
        return CharacterClass.values()[choice - 1];
    }

    private static int readIntInRange(int min, int max) {
        while (true) {
            String s = SC.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v < min || v > max) {
                    System.out.printf("Enter a number between %d and %d: ", min, max);
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number: ");
            }
        }
    }
}
