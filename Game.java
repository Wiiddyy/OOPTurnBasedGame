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

    private EnemyType[] enemySequence = {
        EnemyType.JUNIOR,
        EnemyType.SENIOR,
        EnemyType.BOSS
    };

    private int currentEnemyIndex = 0; //tracks which enemy in seuqnce

    private void startNewGame() {
        System.out.println("\nStarting new game...");

        // reset enemy sequence for new game
        currentEnemyIndex = 0;
        enemy = enemySequence[currentEnemyIndex].create();

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

        battleLoop();

        System.out.println("\nReturning to main menu.");
    }

private void battleLoop() {
    boolean inBattle = true;

    // Initialize first enemy in sequence
    enemy = enemySequence[currentEnemyIndex].create();

    while (inBattle) {
        MacroLib.clearConsole();
        Interface.showBattleHeader();

        // Show player stats
        Interface.showStats(
                "You (" + playerClass.getDisplayName() + ")",
                playerHp,
                playerMaxHp,
                playerMana,
                playerMaxMana
        );

        // Show enemy stats
        Interface.showStats(
                "Enemy (" + enemy.getName() + ")",
                enemy.getHp(),
                enemy.getMaxHp(),
                enemy.getMana(),
                enemy.getMaxMana()
        );

        // Player action
        int action = Menus.battleMenu();
        boolean playerDefending = false;

        switch (action) {
            case 1 -> playerUseSkill();
            case 2 -> {
                playerDefending = true;
                System.out.println("You brace for the enemy's attack (defending).");
            }
            case 3 -> {
                if (playerPotions > 0) {
                    int heal = MacroLib.randInt(15, 30);
                    playerHp = Math.min(playerMaxHp, playerHp + heal);
                    playerPotions--;
                    System.out.printf("You use a potion and heal %d HP. (%d left)%n", heal, playerPotions);
                } else {
                    System.out.println("No potions left!");
                }
            }
            case 4 -> {
                if (MacroLib.randInt(1, 100) <= 50) {
                    System.out.println("You successfully ran away!");
                    inBattle = false;
                    continue;
                } else {
                    System.out.println("You failed to run away!");
                }
            }
        }

        // Check if enemy is defeated
        if (!enemy.isAlive()) {
            System.out.printf("\n%s defeated!%n", enemy.getName());
            Interface.pauseLine();
            currentEnemyIndex++; // move to next enemy in sequence

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
        } else {
            // fallback basic attack
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


        // mana checker
        if (playerMana < selectedSkill.getManaCost()) {
            System.out.println("Not enough mana!");
            return;
        }

        // mana consume
        playerMana -= selectedSkill.getManaCost();

        // apply damage
        enemy.takeDamage(selectedSkill.getDamage());
        System.out.printf("You used %s and dealth %d damage!%n",
                    selectedSkill.getName(), selectedSkill.getDamage());

        if (!selectedSkill.getEffect().equals("None")) {
            System.out.println("Effect applied: " + selectedSkill.getEffect());
            // Later: implement Stun, Bleed, Burn, etc.
        }
    }
}