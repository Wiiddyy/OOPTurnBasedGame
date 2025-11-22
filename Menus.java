import java.util.List;
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
    System.out.println("- Using potions can regenerate your HP and Mana.");  
    System.out.println("- Try to win fights!");  
    Interface.pauseLine();  
}  

// pili character gikan sa available list (gamitan after player dies)  
public static CharacterClass chooseCharacterFromList(List<CharacterClass> availableClasses) {  
    while (true) {  
        System.out.println("\nAvailable characters:");  
        for (int i = 0; i < availableClasses.size(); i++) {  
            CharacterClass c = availableClasses.get(i);  
            System.out.printf("%d. %s (HP: %d, Mana: %d)%n",  
                    i + 1, c.getDisplayName(), c.getMaxHp(), c.getMaxMana());  
        }  
        System.out.print("Choose a character: ");  

        String input = SC.nextLine().trim();  
        int choice;  
        try {  
            choice = Integer.parseInt(input);  
        } catch (NumberFormatException e) {  
            System.out.println("Invalid input! Please enter a number.");  
            continue;  
        }  

        if (choice < 1 || choice > availableClasses.size()) {  
            System.out.println("Invalid choice! Try again.");  
            continue;  
        }  

        return availableClasses.get(choice - 1);  
    }  
}  

// initial character selection sa game start  
public static CharacterClass chooseCharacterMenu() {  
    System.out.println("\nChoose Your Character:");  
    List<CharacterClass> allCharacters = CharacterClass.getAllCharacters(); // gamitan sa static list sa CharacterClass  
    int index = 1;  
    for (CharacterClass c : allCharacters) {  
        System.out.printf("%d) %s (HP: %d, Skills: %d)%n",  
                index++, c.getDisplayName(), c.getMaxHp(), c.getSkills().size());  
    }  
    System.out.print("> ");  
    int choice = readIntInRange(1, allCharacters.size());  
    return allCharacters.get(choice - 1);  
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
