import java.util.Scanner;

public class Game {
    // Player stats
    private int playerHp;
    private int playerMaxHp;
    private int playerMana;
    private int playerMaxMana;
    private int hpPotions;
    private int manaPotions;
    private CharacterClass playerClass;

    // Enemy stats
    private Enemy enemy;

    private final Scanner scanner = new Scanner(System.in);

    // Enemy sequence
    private final EnemyType[] enemySequence = {
            EnemyType.JUNIOR,
            EnemyType.SENIOR,
            EnemyType.BOSS
    };
    private int currentEnemyIndex = 0;

    private boolean running;

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
                case 1 -> startNewGame();
                case 2 -> Menus.howToPlay();
                case 3 -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
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

        playerMaxMana = playerClass.getMaxMana();
        playerMana = playerMaxMana;

        hpPotions = 3;
        manaPotions = 3;

        System.out.println("You selected: " + playerClass.getDisplayName());
        System.out.println("HP: " + playerHp + "/" + playerMaxHp);
        System.out.println("Mana: " + playerMana + "/" + playerMaxMana);
        System.out.println("HP Potions: " + hpPotions + " | Mana Potions: " + manaPotions);

        Interface.pauseLine();

        // Reset enemy sequence
        currentEnemyIndex = 0;
        enemy = enemySequence[currentEnemyIndex].create();

        battleLoop();

        System.out.println("\nReturning to main menu.");
    }

    private void battleLoop() {
    boolean inBattle = true;

    while (inBattle) {
        MacroLib.clearConsole();
        Interface.showBattleHeader();

        // Show player stats
        Interface.showStats(
                "You (" + playerClass.getDisplayName() + ")",
                playerHp, playerMaxHp,
                playerMana, playerMaxMana
        );
        System.out.println("HP Potions: " + hpPotions + " | Mana Potions: " + manaPotions);

        // Show enemy stats
        Interface.showStats(
                "Enemy (" + enemy.getName() + ")",
                enemy.getHp(), enemy.getMaxHp(),
                enemy.getMana(), enemy.getMaxMana()
        );

        // Player action
        boolean playerDefending = false;
        boolean actionTaken = false; // flag to check if player's turn is consumed

        while (!actionTaken) {
            int action = Menus.battleMenu();

            switch (action) {
                case 1 -> { // Use skill
                    playerUseSkill();
                    actionTaken = true; // turn consumed
                }
                case 2 -> { // Defend
                    playerDefending = true;
                    System.out.println("You brace for the enemy's attack (defending).");
                    actionTaken = true; // turn consumed
                }
                case 3 -> usePotionMenu(); // Use potion, does NOT consume turn
                case 4 -> { // Run away
                    if (MacroLib.randInt(1, 100) <= 50) {
                        System.out.println("You successfully ran away!");
                        return; // exit battle loop immediately
                    } else {
                        System.out.println("You failed to run away!");
                        actionTaken = true; // failed escape consumes turn
                    }
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }

        // Check if enemy is defeated
        if (!enemy.isAlive()) {
            System.out.printf("\n%s defeated!%n", enemy.getName());
            Interface.pauseLine();
            currentEnemyIndex++;

            if (currentEnemyIndex < enemySequence.length) {
                enemy = enemySequence[currentEnemyIndex].create();
                System.out.printf("\nA new enemy appears: %s%n", enemy.getName());
                Interface.pauseLine();
            } else {
                System.out.println("\nAll enemies defeated! You win the game!");
                inBattle = false;
                continue;
            }
        }

        // Enemy turn
        Skill enemySkill = enemy.chooseSkill();
        if (enemySkill != null) {
            enemy.useMana(enemySkill.getManaCost());
            int dmg = enemySkill.getDamage();
            if (playerDefending) dmg /= 2;
            playerHp -= dmg;
            System.out.printf("Enemy used %s and dealt %d damage.%n", enemySkill.getName(), dmg);

            if (!enemySkill.getEffect().equals("None")) {
                System.out.println("Enemy skill effect: " + enemySkill.getEffect());
            }
        } else { // fallback basic attack
            int dmg = MacroLib.randInt(6, 16);
            if (playerDefending) dmg /= 2;
            playerHp -= dmg;
            System.out.printf("Enemy attacks and deals %d damage.%n", dmg);
        }

        // Check if player is defeated
        if (playerHp <= 0) {
            System.out.println("\nYou have been defeated...");
            Interface.pauseLine();
            playerHp = playerMaxHp;
            playerMana = playerMaxMana;
            inBattle = false;
            continue;
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

        if (playerMana < selectedSkill.getManaCost()) {
            System.out.println("Not enough mana!");
            return;
        }

        // consume mana and deal damage
        playerMana -= selectedSkill.getManaCost();
        enemy.takeDamage(selectedSkill.getDamage());

        System.out.printf("You used %s and dealt %d damage!%n",
                selectedSkill.getName(), selectedSkill.getDamage());

        if (!selectedSkill.getEffect().equals("None")) {
            System.out.println("Effect applied: " + selectedSkill.getEffect());
        }
    }

        private void usePotionMenu() {
        while (true) {
            System.out.println("\nWhich potion do you want to use?");
            System.out.println("1. HP Potion (" + hpPotions + " left)");
            System.out.println("2. Mana Potion (" + manaPotions + " left)");
            System.out.println("3. Back to action menu");
            System.out.print("Choose an option: ");
            int potionChoice = scanner.nextInt();

            switch (potionChoice) {
                case 1 -> {
                    if (hpPotions > 0) {
                        int heal = MacroLib.randInt(15, 30);
                        playerHp = Math.min(playerMaxHp, playerHp + heal);
                        hpPotions--;
                        System.out.printf("You used an HP potion and healed %d HP (%d left)%n", heal, hpPotions);
                        return; // return immediately, turn is NOT consumed
                    } else System.out.println("No HP potions left!");
                }
                case 2 -> {
                    if (manaPotions > 0) {
                        int restore = MacroLib.randInt(15, 30);
                        playerMana = Math.min(playerMaxMana, playerMana + restore);
                        manaPotions--;
                        System.out.printf("You used a Mana potion and restored %d Mana (%d left)%n", restore, manaPotions);
                        return; // return immediately, turn is NOT consumed
                    } else System.out.println("No Mana potions left!");
                }
                case 3 -> {
                    System.out.println("Returning to action menu...");
                    return; // just go back to action menu
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
