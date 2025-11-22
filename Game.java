import java.util.Scanner;

public class Game {

    private Player player; // active player
    private Enemy enemy;   // current enemy

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
        CharacterClass chosenClass = Menus.chooseCharacterMenu();

        // Create player from chosen class
        player = new Player(
                chosenClass.getDisplayName(),
                chosenClass.getMaxHp(),
                chosenClass.getMaxMana(),
                0, // attack placeholder
                chosenClass.getSkills()
        );

        System.out.println("You selected: " + chosenClass.getDisplayName());
        System.out.printf("HP: %d/%d | Mana: %d/%d%n",
                player.getHp(), player.getMaxHp(), player.getMana(), player.getMaxMana());
        Interface.pauseLine();

        // Start first enemy
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
            Interface.showStats("You (" + player.getName() + ")",
                    player.getHp(), player.getMaxHp(),
                    player.getMana(), player.getMaxMana());

            // Show enemy stats
            Interface.showStats("Enemy (" + enemy.getName() + ")",
                    enemy.getHp(), enemy.getMaxHp(),
                    enemy.getMana(), enemy.getMaxMana());

            // Player action
            int action = Menus.battleMenu();
            boolean playerDefending = false;
            boolean turnConsumed = true;

            switch (action) {
                case 1 -> {
                    Skill chosenSkill = player.chooseAction(enemy);
                    if (chosenSkill != null) {
                        if (player.getMana() >= chosenSkill.getManaCost()) {
                            player.useMana(chosenSkill.getManaCost());
                            enemy.takeDamage(chosenSkill.getDamage());
                            System.out.printf("You used %s and dealt %d damage!%n",
                                    chosenSkill.getName(), chosenSkill.getDamage());
                            if (!chosenSkill.getEffect().equals("None")) {
                                System.out.println("Effect applied: " + chosenSkill.getEffect());
                            }
                        } else {
                            System.out.println("Not enough mana!");
                        }
                    } else {
                        System.out.println("Turn missed!");
                    }
                    turnConsumed = true;
                }
                case 2 -> {
                    playerDefending = true;
                    System.out.println("You brace for the enemy's attack (defending).");
                    turnConsumed = true;
                }
                case 3 -> turnConsumed = usePotionMenu(); // false if turn not consumed
                case 4 -> {
                    if (MacroLib.randInt(1, 100) <= 50) {
                        System.out.println("You successfully ran away!");
                        return;
                    } else {
                        System.out.println("You failed to run away!");
                    }
                }
            }

            // Enemy action
            if (turnConsumed && enemy.isAlive()) {
                Skill enemySkill = enemy.chooseAction(player);
                int dmg;
                if (enemySkill != null) {
                    enemy.useMana(enemySkill.getManaCost());
                    dmg = enemySkill.getDamage();
                } else {
                    dmg = MacroLib.randInt(6, 16);
                }

                if (playerDefending) dmg /= 2;
                player.takeDamage(dmg);
                System.out.printf("Enemy deals %d damage.%n", dmg);
            }

            // Check defeat/win
            if (!player.isAlive()) {
                System.out.println("\nYou have been defeated...");
                Interface.pauseLine();
                inBattle = false; // simple defeat handling
            } else if (!enemy.isAlive()) {
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
                }
            }

            System.out.println("\n(End of turn)");
            Interface.pauseLine();
        }
    }

    private boolean usePotionMenu() {
        while (true) {
            System.out.println("\nWhich potion do you want to use?");
            System.out.println("1. HP Potion");
            System.out.println("2. Mana Potion");
            System.out.println("3. Back to action menu");
            System.out.print("Choose an option: ");
            int potionChoice = scanner.nextInt();

            switch (potionChoice) {
                case 1 -> {
                    int heal = MacroLib.randInt(15, 30);
                    player.heal(heal);
                    System.out.printf("You used an HP potion and healed %d HP%n", heal);
                    return false; // turn not consumed
                }
                case 2 -> {
                    int restore = MacroLib.randInt(15, 30);
                    player.restoreMana(restore);
                    System.out.printf("You used a Mana potion and restored %d Mana%n", restore);
                    return false;
                }
                case 3 -> {
                    System.out.println("Returning to action menu...");
                    return false;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
