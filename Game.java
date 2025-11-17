import java.util.Scanner;

public class Game {
    private int playerHp;
    private int playerMaxHp;
    private int playerPotions;
    private int playerMana;
    private int playerMaxMana;
    private int enemyHp;
    private int enemyMaxHp;
    private int enemyMana;
    private int enemyMaxMana;
    private boolean running;
    private CharacterClass playerClass;

    private Enemy enemy;

    private final Scanner scanner = new Scanner(System.in);

    public Game() { }

    public static void main(String[] args) {
        MacroLib.clearConsole();
        Interface.title();

        Game g = new Game();
        g.loop();
    }

    private void loop() {
        running = true;
        while (running) {
            int choice = Menus.mainMenu();
            switch (choice) {
                case 1: startNewGame(); break;
                case 2: Menus.howToPlay(); break;
                case 3: System.out.println("Goodbye!"); running = false; break;
            }
        }
    }

    private void startNewGame() {
        System.out.println("\nStarting new game...");

        // Character Selection
        playerClass = Menus.chooseCharacterMenu();

        // Apply stats from chosen character
        playerMaxHp = playerClass.getMaxHp();
        playerHp = playerMaxHp;
        playerMana = playerClass.getMaxMana();
        playerPotions = 3; // default potions

        System.out.println("You selected: " + playerClass.getDisplayName());
        System.out.println("HP: " + playerHp + "/" + playerMaxHp);
        System.out.println("Mana: " + playerMana + "/" + playerClass.getMaxMana());

        Interface.pauseLine();

        // Spawn enemy
        enemyMaxHp = MacroLib.ENEMY_MAX_HP;
        enemyHp = enemyMaxHp;

        battleLoop();

        System.out.println("\nReturning to main menu.");
    }

    private void battleLoop() {
        boolean inBattle = true;
        while (inBattle) {
            MacroLib.clearConsole();
            Interface.showBattleHeader();
            Interface.showStats("You (" + playerClass.getDisplayName() + ")", playerHp, playerMaxHp, playerMana, playerMaxMana);
            Interface.showStats("Enemy", enemyHp, enemyMaxHp, enemyMana, enemyMaxMana);

            int action = Menus.battleMenu();
            boolean playerDefending = false;

            switch (action) {
                case 1: playerUseSkill(); break; // Use skill
                case 2:
                    playerDefending = true;
                    System.out.println("You brace for the enemy's attack (defending).");
                    break;
                case 3:
                    if (playerPotions > 0) {
                        int heal = MacroLib.randInt(15, 30);
                        playerHp = Math.min(playerMaxHp, playerHp + heal);
                        playerPotions--;
                        System.out.printf("You use a potion and heal %d HP. (%d left)%n", heal, playerPotions);
                    } else {
                        System.out.println("No potions left!");
                    }
                    break;
                case 4:
                    if (MacroLib.randInt(1, 100) <= 50) {
                        System.out.println("You successfully ran away!");
                        inBattle = false;
                        continue;
                    } else {
                        System.out.println("You failed to run away!");
                    }
                    break;
            }

            if (enemyHp <= 0) {
                System.out.println("\nEnemy defeated! You win!");
                Interface.pauseLine();
                break;
            }

            // Enemy turn
            int enemyAction = MacroLib.randInt(1, 100);
            if (enemyAction <= 75) {
                int dmg = MacroLib.randInt(6, 16);
                if (playerDefending) dmg /= 2;
                playerHp -= dmg;
                System.out.printf("Enemy attacks and deals %d damage.%n", dmg);
            } else {
                System.out.println("Enemy hesitates.");
            }

            if (playerHp <= 0) {
                System.out.println("\nYou have been defeated...");
                Interface.pauseLine();
                playerHp = playerMaxHp;
                break;
            }

            System.out.println("\n(End of turn)");
            Interface.pauseLine();
        }
    }

    private void playerUseSkill() {
        System.out.println("\nAvailable skills for " + playerClass.getDisplayName() + ":");
        for (int i = 0; i < playerClass.getSkills().size(); i++) {
            System.out.println((i + 1) + ". " + playerClass.getSkills().get(i));
        }

        System.out.print("Choose a skill (1-" + playerClass.getSkills().size() + "): ");
        int choice = scanner.nextInt();

        if (choice < 1 || choice > playerClass.getSkills().size()) {
            System.out.println("Invalid choice! You miss your turn.");
            return;
        }

        Skill selectedSkill = playerClass.getSkills().get(choice - 1);

        enemyHp -= selectedSkill.getDamage();
        System.out.printf("You used %s and dealt %d damage!%n",
                selectedSkill.getName(), selectedSkill.getDamage());

        if (!selectedSkill.getEffect().equals("None")) {
            System.out.println("Effect applied: " + selectedSkill.getEffect());
            // Later: implement Stun, Bleed, Burn, etc.
        }
    }
}
