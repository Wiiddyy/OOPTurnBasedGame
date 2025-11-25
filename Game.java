import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/*
 Chill comments with a bit of Bisaya peppered in:
 - Kini ang full Game.java nga imong gihangyo.
 - Transparency fixed so background layers actually show.
 - Ayaw kabalaka, wala ko gi-unsa ang game logic (gihikling lang ang UI transparency stuff).
*/

public class Game extends JFrame {
    private Player player;
    private Enemy enemy;
    private List<CharacterClass> availableCharacters;
    private final EnemyType[] enemySequence = {EnemyType.JUNIOR, EnemyType.SENIOR, EnemyType.BOSS};
    private int currentEnemyIndex = 0;

    private JTextArea battleLog;
    private JButton defendBtn, potionBtn, runBtn;
    private JPanel skillPanel;

    private StatusCard playerCard, enemyCard;

    public Game() {

        
        setTitle("Fight Club Turn-Based Game");

        setSize(1280, 880);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        availableCharacters = new ArrayList<>(CharacterClass.getAllCharacters());
        showStartMenu();
    }

    /** START MENU (simple, gana ra ni) **/
    private void showStartMenu() {
        getContentPane().removeAll();

        // Use BackgroundPanel for nice background
        BackgroundPanel bgPanel = new BackgroundPanel(
                "D:\\FightClubYada\\Assets\\background2.png",
                "D:\\FightClubYada\\Assets\\background1.png"
        );
        bgPanel.setLayout(new BoxLayout(bgPanel, BoxLayout.Y_AXIS));
        setContentPane(bgPanel);

        // Add some space on top
        bgPanel.add(Box.createVerticalStrut(100));

        // Title as an image
        String titleImgPath = "D:\\FightClubYada\\Assets\\titlesc.png";
        ImageIcon titleIcon = new ImageIcon(titleImgPath);
        Image titleImg = titleIcon.getImage().getScaledInstance(600, 150, Image.SCALE_SMOOTH);
        JLabel titleLabel = new JLabel(new ImageIcon(titleImg));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bgPanel.add(titleLabel);

        bgPanel.add(Box.createVerticalStrut(50));

        // Image paths for buttons
        String startImgPath = "D:\\FightClubYada\\Assets\\startbtn.png";
        String howToPlayImgPath = "D:\\FightClubYada\\Assets\\howtoplaybtn.png";
        String exitImgPath = "D:\\FightClubYada\\Assets\\exitbtn.png";

        // Load and scale buttons
        ImageIcon startIcon = new ImageIcon(startImgPath);
        Image startImg = startIcon.getImage().getScaledInstance(200, 50, Image.SCALE_SMOOTH);
        JButton startBtn = new JButton(new ImageIcon(startImg));

        ImageIcon howToPlayIcon = new ImageIcon(howToPlayImgPath);
        Image howToPlayImg = howToPlayIcon.getImage().getScaledInstance(250, 60, Image.SCALE_SMOOTH);
        JButton howToPlayBtn = new JButton(new ImageIcon(howToPlayImg));

        ImageIcon exitIcon = new ImageIcon(exitImgPath);
        Image exitImg = exitIcon.getImage().getScaledInstance(200, 50, Image.SCALE_SMOOTH);
        JButton exitBtn = new JButton(new ImageIcon(exitImg));

        // Make buttons transparent and center them
        JButton[] buttons = {startBtn, howToPlayBtn, exitBtn};
        for (JButton btn : buttons) {
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            bgPanel.add(btn);
            bgPanel.add(Box.createVerticalStrut(100)); // spacing
        }

        // Button actions
        startBtn.addActionListener(e -> initCharacterSelection());
        howToPlayBtn.addActionListener(e -> showHowToPlay());
        exitBtn.addActionListener(e -> System.exit(0));

        revalidate();
        repaint();
    }

    private void showHowToPlay() {
        String msg = """
                How to Play:
                - Choose a character with different stats.
                - Each turn, choose an action: Attack (use skills), Defend, Use Potions, or Run.
                - Attack consumes mana and reduces enemy HP.
                - Defend reduces damage for this turn.
                - Potions restore HP or Mana and do NOT consume your turn.
                - Try to defeat all enemies to win the game!
                """;
        JOptionPane.showMessageDialog(this, msg, "How To Play", JOptionPane.INFORMATION_MESSAGE);
    }

    /** CHARACTER SELECTION (transparent too so background can show) **/
    private void initCharacterSelection() {
        getContentPane().removeAll();

        // Use your custom BackgroundPanel to show background behind transparent buttons
        BackgroundPanel bgPanel = new BackgroundPanel(
                "D:\\FightClubYada\\Assets\\background2.png",
                "D:\\FightClubYada\\Assets\\background1.png"
        );
        bgPanel.setLayout(new BorderLayout());
        setContentPane(bgPanel);

        // Title label
        JLabel title = new JLabel("Choose your character:");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.WHITE); // make text visible
        title.setOpaque(false);
        bgPanel.add(title, BorderLayout.NORTH);

        // Top and bottom rows for characters
        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        topRow.setOpaque(false);
        JPanel bottomRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 20));
        bottomRow.setOpaque(false);

        int index = 0;
        for (CharacterClass cc : availableCharacters) {
            ImageIcon img = new ImageIcon("D:\\FightClubYada\\Assets\\" + cc.getName() + ".png");
            Image scaled = img.getImage().getScaledInstance(220, 320, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaled);

            JButton heroBtn = new JButton(scaledIcon);
            heroBtn.setOpaque(false);
            heroBtn.setContentAreaFilled(false);
            heroBtn.setBorderPainted(false);
            heroBtn.setFocusPainted(false);
            heroBtn.setToolTipText(cc.getName() + " | HP: " + cc.getMaxHp() + " Mana: " + cc.getMaxMana());

            heroBtn.addActionListener(e -> {
                player = new Player(cc.getName(), cc.getMaxHp(), cc.getMaxMana(), 5, 5, cc.getSkills());
                availableCharacters.remove(cc);
                startBattle();
            });

            if (index < 3) topRow.add(heroBtn);
            else bottomRow.add(heroBtn);
            index++;
            if (index >= 5) break;
        }

        // Wrapper panel
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.add(topRow);
        wrapper.add(bottomRow);

        bgPanel.add(wrapper, BorderLayout.CENTER);

        revalidate();
        repaint();
    }



    /** BATTLE **/
    private void startBattle() {
        // start at the first enemy for a new game
        currentEnemyIndex = 0;
        enemy = enemySequence[currentEnemyIndex].createEnemy();
        setupBattleScreen();
    }

    private void setupBattleScreen() {
        getContentPane().removeAll();

        // two-layer background (bottom then top)
        BackgroundPanel bgPanel = new BackgroundPanel(
            "D:\\FightClubYada\\Assets\\background2.png",
            "D:\\FightClubYada\\Assets\\background1.png"
        );

        // set as content pane so bg paints behind everything
        setContentPane(bgPanel);
        bgPanel.setLayout(new BorderLayout(10, 10));

        // Status cards panel (player + enemy) - make them transparent
        JPanel cardsPanel = new JPanel(new GridLayout(1, 2, 50, 10));
        cardsPanel.setOpaque(false);
        cardsPanel.setPreferredSize(new Dimension(0, 120));
        playerCard = new StatusCard();
        enemyCard = new StatusCard();
        playerCard.setOpaque(false);
        enemyCard.setOpaque(false);
        playerCard.setPreferredSize(new Dimension(300, 120));
        enemyCard.setPreferredSize(new Dimension(300, 120));
        playerCard.updateFromEntity(player);
        enemyCard.updateFromEntity(enemy);
        cardsPanel.add(playerCard);
        cardsPanel.add(enemyCard);

        // center wrapper to keep the cards in the middle
        JPanel centerWrapper = new JPanel();
        centerWrapper.setOpaque(false);
        centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
        centerWrapper.add(Box.createVerticalGlue());
        centerWrapper.add(cardsPanel);
        centerWrapper.add(Box.createVerticalGlue());
        bgPanel.add(centerWrapper, BorderLayout.CENTER);

        // Skills panel (horizontal) above battle log
        skillPanel = new JPanel(new GridLayout(1, 0, 6, 6));
        skillPanel.setOpaque(false);
        skillPanel.setBorder(BorderFactory.createTitledBorder("Skills"));
        updateSkillButtons();

        // Action buttons (put them in a semi-transparent panel so text is readable)
        defendBtn = new JButton("Defend");
        potionBtn = new JButton("Potions");
        runBtn = new JButton("Run");
        defendBtn.addActionListener(this::defendAction);
        potionBtn.addActionListener(this::potionAction);
        runBtn.addActionListener(this::runAction);

        JPanel actionPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        actionPanel.setOpaque(false);
        actionPanel.add(defendBtn);
        actionPanel.add(potionBtn);
        actionPanel.add(runBtn);

        // Bottom panel combining skills + actions (transparent)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        skillPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(skillPanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(actionPanel);

        // Battle log (transparent text area + transparent scroll viewport)
        battleLog = new JTextArea(10, 50);
        battleLog.setEditable(false);
        battleLog.setLineWrap(true);
        battleLog.setWrapStyleWord(true);
        battleLog.setOpaque(false); // text area transparent
        battleLog.setForeground(Color.WHITE); // make text visible on background (adjust as needed)

        JScrollPane scrollPane = new JScrollPane(battleLog);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Battle Log"));

        // log wrapper (transparent) - skills above, log below
        JPanel logWrapper = new JPanel(new BorderLayout());
        logWrapper.setOpaque(false);
        logWrapper.add(bottomPanel, BorderLayout.NORTH);
        logWrapper.add(scrollPane, BorderLayout.CENTER);

        bgPanel.add(logWrapper, BorderLayout.SOUTH);

        battleLog.setText("A wild " + enemy.getName() + " appears!\n");
        refreshCards();
        revalidate();
        repaint();
    }

    /** SKILLS **/
    private void updateSkillButtons() {
        skillPanel.removeAll();
        if (player == null) return;
        for (Skill s : player.getSkills()) {
            JButton btn = new JButton(s.getName() + " (Mana: " + s.getManaCost() + ")");
            btn.addActionListener(e -> useSkillDelayed(s));
            skillPanel.add(btn);
        }
        skillPanel.revalidate();
        skillPanel.repaint();
    }

    private void useSkillDelayed(Skill chosenSkill) {
        disableActionButtons();
        Timer t = new Timer(500, e -> {
            useSkill(chosenSkill);
            ((Timer) e.getSource()).stop();
        });
        t.setRepeats(false);
        t.start();
    }

    private void useSkill(Skill chosenSkill) {
        if (player.getMana() < chosenSkill.getManaCost()) {
            battleLog.append("Not enough mana!\n");
            enableActionButtons();
            return;
        }

        player.useMana(chosenSkill.getManaCost());
        enemy.takeDamage(chosenSkill.getDamage());
        battleLog.append("You used " + chosenSkill.getName() + " for " + chosenSkill.getDamage() + " damage.\n");
        if (!chosenSkill.getEffect().equals("None"))
            battleLog.append("Effect applied: " + chosenSkill.getEffect() + "\n");

        refreshCards();
        checkEnemyStatus();

        if (enemy.isAlive()) {
            enemyTurnDelayed(false);
        } else {
            enableActionButtons();
        }
        updateSkillButtons();
    }

    /** ACTIONS **/
    private void defendAction(ActionEvent e) {
        battleLog.append("You brace for the enemy's attack.\n");
        enemyTurnDelayed(true);
    }

    private void potionAction(ActionEvent e) {
        boolean choosing = true;
        while (choosing) {
            String[] options = {
                    "HP Potion (" + player.getHpPotions() + " left)",
                    "Mana Potion (" + player.getManaPotions() + " left)",
                    "Done"
            };
            int choice = JOptionPane.showOptionDialog(this, "Choose potion:", "Potions",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            switch (choice) {
                case 0 -> {
                    if (player.getHpPotions() > 0) {
                        int heal = MacroLib.randInt(15, 30);
                        player.heal(heal);
                        player.useHpPotion();
                        battleLog.append("You used an HP potion and healed " + heal + " HP.\n");
                    } else {
                        battleLog.append("No HP potions left!\n");
                    }
                }
                case 1 -> {
                    if (player.getManaPotions() > 0) {
                        int restore = MacroLib.randInt(15, 30);
                        player.restoreMana(restore);
                        player.useManaPotion();
                        battleLog.append("You used a Mana potion and restored " + restore + " Mana.\n");
                    } else {
                        battleLog.append("No Mana potions left!\n");
                    }
                }
                default -> choosing = false;
            }
            refreshCards();
            updateSkillButtons();
        }
    }

    private void runAction(ActionEvent e) {
        if (MacroLib.randInt(1, 100) <= 50) {
            battleLog.append("You successfully ran away!\n");
            showStartMenu();
        } else {
            battleLog.append("You failed to run away!\n");
            enemyTurnDelayed(false);
        }
    }

    /** ENEMY TURN WITH DELAY **/
    private void enemyTurnDelayed(boolean playerDefending) {
        disableActionButtons();
        Timer t = new Timer(800, e -> {
            Skill enemySkill = enemy.chooseAction(player);
            int dmg;
            if (enemySkill != null) {
                enemy.useMana(enemySkill.getManaCost());
                dmg = enemySkill.getDamage();
                battleLog.append("Enemy uses " + enemySkill.getName() + "!\n");
            } else {
                dmg = MacroLib.randInt(6, 16);
                battleLog.append("Enemy attacks normally!\n");
            }
            if (playerDefending) dmg /= 2;
            player.takeDamage(dmg);
            battleLog.append("Enemy deals " + dmg + " damage.\n");

            refreshCards();
            checkPlayerStatus();
            enableActionButtons();
            ((Timer) e.getSource()).stop();
        });
        t.setRepeats(false);
        t.start();
    }

    private void checkEnemyStatus() {
        if (!enemy.isAlive()) {
            battleLog.append(enemy.getName() + " defeated!\n");
            currentEnemyIndex++;
            if (currentEnemyIndex < enemySequence.length) {
                enemy = enemySequence[currentEnemyIndex].createEnemy();
                battleLog.append("A new enemy appears: " + enemy.getName() + "\n");
                refreshCards();
            } else {
                // Boss defeated -> show victory
                showVictoryScreen();
            }
        }
    }

    private void showVictoryScreen() {
        getContentPane().removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setOpaque(false); // transparent so bg still shows (if bg present)

        JLabel victoryLabel = new JLabel("VICTORY!");
        victoryLabel.setFont(new Font("Arial", Font.BOLD, 72));
        victoryLabel.setForeground(Color.YELLOW);
        victoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(victoryLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 50)));

        JButton mainMenuBtn = new JButton("Back to Main Menu");
        mainMenuBtn.setFont(new Font("Arial", Font.BOLD, 24));
        mainMenuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuBtn.addActionListener(e -> showStartMenu());
        panel.add(mainMenuBtn);

        getContentPane().add(panel);
        revalidate();
        repaint();

        // small pulse animation
        final int[] fontSize = {72};
        final boolean[] growing = {true};

        Timer timer = new Timer(50, e -> {
            if (growing[0]) {
                fontSize[0] += 2;
                if (fontSize[0] >= 92) growing[0] = false;
            } else {
                fontSize[0] -= 2;
                if (fontSize[0] <= 72) growing[0] = true;
            }
            victoryLabel.setFont(new Font("Arial", Font.BOLD, fontSize[0]));
        });
        timer.start();
    }

    private void checkPlayerStatus() {
        if (!player.isAlive()) {
            battleLog.append("You have been defeated!\n");
            if (!availableCharacters.isEmpty()) {
                JOptionPane.showMessageDialog(this, "You died! Choose another character.");
                chooseNewPlayerAfterDeath(); // resume battle with current enemy
            } else {
                battleLog.append("No more characters available. Game over.\n");
                disableActionButtons();
            }
        }
    }

    /** Choose replacement player after death without resetting enemy (panels transparent as well) **/
    private void chooseNewPlayerAfterDeath() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Choose a new character:");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        topRow.setOpaque(false);
        JPanel bottomRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 20));
        bottomRow.setOpaque(false);

        int index = 0;
        for (CharacterClass cc : availableCharacters) {
            ImageIcon img = new ImageIcon("D:\\FightClubYada\\Assets\\" + cc.getName() + ".png");
            Image scaled = img.getImage().getScaledInstance(220, 320, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaled);

            JButton heroBtn = new JButton(scaledIcon);
            heroBtn.setBorderPainted(false);
            heroBtn.setContentAreaFilled(false);
            heroBtn.setFocusPainted(false);
            heroBtn.setToolTipText(cc.getName() + " | HP: " + cc.getMaxHp() + " Mana: " + cc.getMaxMana());

            heroBtn.addActionListener(e -> {
                player = new Player(cc.getName(), cc.getMaxHp(), cc.getMaxMana(), 5, 5, cc.getSkills());
                availableCharacters.remove(cc);
                setupBattleScreen(); // resume battle with current enemy
            });

            if (index < 3) topRow.add(heroBtn);
            else bottomRow.add(heroBtn);
            index++;
            if (index >= 5) break;
        }

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.add(topRow);
        wrapper.add(bottomRow);

        add(wrapper, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /* -------------------------
       BackgroundPanel - 2 layer
       -------------------------
       bottom bg = bg1, top overlay = bg2
       gamay lang ang comments para di formal kaayo
    */
    class BackgroundPanel extends JPanel {
        private Image bg1, bg2;

        public BackgroundPanel(String image1Path, String image2Path) {
            loadBackgrounds(image1Path, image2Path);
            setLayout(new BorderLayout());
            setOpaque(true); // the panel paints the images
        }

        public void loadBackgrounds(String image1Path, String image2Path) {
            bg1 = loadImageSafely(image1Path);
            bg2 = loadImageSafely(image2Path);
            repaint();
        }

        private Image loadImageSafely(String path) {
            try {
                if (path == null || path.isEmpty()) return null;
                ImageIcon icon = new ImageIcon(path);
                Image img = icon.getImage();
                if (img == null) {
                    System.err.println("BackgroundPanel: image is null for: " + path);
                    return null;
                }
                return img;
            } catch (Exception ex) {
                System.err.println("BackgroundPanel: failed to load '" + path + "': " + ex.getMessage());
                return null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // draw bottom layer first
            if (bg1 != null) {
                g.drawImage(bg1, 0, 0, getWidth(), getHeight(), this);
            }

            // draw top layer (overlay)
            if (bg2 != null) {
                g.drawImage(bg2, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    private void disableActionButtons() {
        if (defendBtn != null) defendBtn.setEnabled(false);
        if (potionBtn != null) potionBtn.setEnabled(false);
        if (runBtn != null) runBtn.setEnabled(false);
        if (skillPanel != null) {
            for (Component c : skillPanel.getComponents()) c.setEnabled(false);
        }
    }

    private void enableActionButtons() {
        if (defendBtn != null) defendBtn.setEnabled(true);
        if (potionBtn != null) potionBtn.setEnabled(true);
        if (runBtn != null) runBtn.setEnabled(true);
        if (skillPanel != null) {
            for (Component c : skillPanel.getComponents()) c.setEnabled(true);
        }
    }

    private void refreshCards() {
        if (playerCard != null) playerCard.updateFromEntity(player);
        if (enemyCard != null) enemyCard.updateFromEntity(enemy);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            game.setVisible(true);
        });
    }
}

/** STATUS CARD **/
class StatusCard extends JPanel {
    private final JLabel nameLabel;
    private final JProgressBar hpBar, manaBar;
    private final JLabel statusLabel;

    public StatusCard() {
        setPreferredSize(new Dimension(350, 140));
        // we want the card itself transparent so bg shows, but keep a border so it's visible
        setOpaque(false);
        setLayout(new BorderLayout(8, 8));

        // border so you can still see the card area on top of the bg
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);

        nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Serif", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE); // text more visible on most backgrounds
        center.add(nameLabel);

        hpBar = new JProgressBar();
        hpBar.setStringPainted(true);
        hpBar.setForeground(Color.RED);
        hpBar.setOpaque(false); // try to keep bars transparent-ish (depends on LAF)
        center.add(hpBar);

        manaBar = new JProgressBar();
        manaBar.setStringPainted(true);
        manaBar.setForeground(Color.BLUE);
        manaBar.setOpaque(false);
        center.add(manaBar);

        statusLabel = new JLabel("- No Status Effects -");
        statusLabel.setFont(new Font("Serif", Font.ITALIC, 12));
        statusLabel.setForeground(Color.WHITE);
        center.add(statusLabel);

        add(center, BorderLayout.CENTER);
    }

    public void updateFromEntity(Entity e) {
        if (e == null) return;
        nameLabel.setText(e.getName());
        hpBar.setMaximum(e.getMaxHp());
        hpBar.setValue(Math.max(0, e.getHp()));
        hpBar.setString("HP: " + e.getHp() + "/" + e.getMaxHp());

        manaBar.setMaximum(e.getMaxMana());
        manaBar.setValue(Math.max(0, e.getMana()));
        manaBar.setString("SP: " + e.getMana() + "/" + e.getMaxMana());

        statusLabel.setText("- No Status Effects -");
    }
}
