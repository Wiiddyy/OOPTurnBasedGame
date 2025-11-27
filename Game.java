import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

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
        setTitle("Fight Club");
        setSize(1280, 880);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        availableCharacters = new ArrayList<>(CharacterClass.getAllCharacters());
        showStartMenu();
    }

    // START MENU (simple, gana ra ni)
    private void showStartMenu() {
        getContentPane().removeAll();

        BackgroundPanel bgPanel = new BackgroundPanel(
                "OOPTurnBasedGame\\Assets\\background1.png",
                "OOPTurnBasedGame\\Assets\\background2.png"
        );

        // IMPORTANT: set layout on the background panel (not on the frame)
        bgPanel.setLayout(new BoxLayout(bgPanel, BoxLayout.Y_AXIS));
        setContentPane(bgPanel);

        // top spacing
        bgPanel.add(Box.createVerticalStrut(100));

        // title as image (not a button)
        String titleImgPath = "OOPTurnBasedGame\\Assets\\titlesc.png";
        ImageIcon titleIcon = new ImageIcon(titleImgPath);
        Image titleImg = titleIcon.getImage().getScaledInstance(600, 150, Image.SCALE_SMOOTH);
        JLabel titleLabel = new JLabel(new ImageIcon(titleImg));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setOpaque(false);
        bgPanel.add(titleLabel);

        bgPanel.add(Box.createVerticalStrut(40));

        // paths for the button images
        String startImgPath = "OOPTurnBasedGame\\Assets\\startbtn.png";
        String howToPlayImgPath = "OOPTurnBasedGame\\Assets\\howtoplaybtn.png";
        String exitImgPath = "OOPTurnBasedGame\\Assets\\exitbtn.png";

        // create image buttons and scale them
        JButton startBtn = makeImageButton(startImgPath, 250, 70);
        JButton howToPlayBtn = makeImageButton(howToPlayImgPath, 300, 70);
        JButton exitBtn = makeImageButton(exitImgPath, 250, 70);

        // center them and add spacing
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        bgPanel.add(startBtn);
        bgPanel.add(Box.createVerticalStrut(80));

        howToPlayBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        bgPanel.add(howToPlayBtn);
        bgPanel.add(Box.createVerticalStrut(80));

        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        bgPanel.add(exitBtn);
        bgPanel.add(Box.createVerticalGlue()); // push to top if space

        // actions
        startBtn.addActionListener(e -> showStoryScreen()); // show story first
        howToPlayBtn.addActionListener(e -> showHowToPlay());
        exitBtn.addActionListener(e -> System.exit(0));

        revalidate();
        repaint();
    }

    // helper to create a transparent image button with scaling
    private JButton makeImageButton(String path, int w, int h) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        JButton btn = new JButton(new ImageIcon(img));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        return btn;
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

    // STORY screen before character selection
    private void showStoryScreen() {
        getContentPane().removeAll();

        // BACKGROUND PANEL
        BackgroundPanel bgPanel = new BackgroundPanel(
                "OOPTurnBasedGame\\Assets\\background2.png",
                "OOPTurnBasedGame\\Assets\\background1.png"
        );
        bgPanel.setLayout(new GridBagLayout());
        setContentPane(bgPanel);

        final String[] storyline = {
                "Central Academy appeared to be a normal school, filled with students, classrooms, and the usual hustle of daily life. "
                        + "But beneath the polished halls and strict rules, a hidden world thrived. In secret corners of the school — "
                        + "abandoned classrooms, the sealed Old Gym, and rarely patrolled hallways — a fight club had existed for years. "
                        + "Only the boldest students dared to enter, proving their strength, cunning, and skill against others who sought the same.",

                "The fight club operated in tiers: juniors tested the newcomers, seniors defended the inner chambers, and at the top of it all "
                        + "stood Ishiguro, the undefeated champion of the secret arena. Victory brought prestige, respect, and control over the "
                        + "school’s hidden battlegrounds, while defeat meant humiliation — and sometimes a painful reminder that the club was "
                        + "not a place for the faint of heart.",

                "Now, a group of students — Mark, Jeno, Clyde, Mico, and Sherwin — have discovered the entrance to this clandestine world. "
                        + "To survive and rise to the top, they must fight through waves of determined juniors, strategic seniors, and finally "
                        + "confront Ishiguro himself. The school’s quiet halls are no longer safe; the secret arena has awakened, and only those "
                        + "with courage, skill, and teamwork will prevail."
        };

        final int[] index = {0};
        JPanel textBox = createTranslucentTextBox();

        //TEXT AREA
        JTextArea storyText = createStoryTextArea(storyline[index[0]]);

        textBox.add(Box.createVerticalGlue());
        textBox.add(storyText);
        textBox.add(Box.createVerticalGlue());

        //NEXT BUTTON
        JButton nextBtn = createNextButton("NEXT");

        //CONTAINER FOR TEXTBOX AND BUTTON
        JPanel contentContainer = new JPanel();
        contentContainer.setOpaque(false);
        contentContainer.setLayout(new BoxLayout(contentContainer, BoxLayout.Y_AXIS));
        contentContainer.add(textBox);
        contentContainer.add(Box.createVerticalStrut(20));
        contentContainer.add(nextBtn);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        bgPanel.add(contentContainer, gbc);


        nextBtn.addActionListener(e -> {
            index[0]++;
            if (index[0] >= storyline.length) {
                initCharacterSelection();
                return;
            }
            storyText.setText(storyline[index[0]]);
            storyText.revalidate();
            storyText.repaint();
        });

        revalidate();
        repaint();
    }
    


    private void showIntermissionScreen() {
        getContentPane().removeAll();

        BackgroundPanel bgPanel = new BackgroundPanel(
                "OOPTurnBasedGame\\Assets\\background2.png",
                "OOPTurnBasedGame\\Assets\\background1.png"
        );
        bgPanel.setLayout(new GridBagLayout());
        setContentPane(bgPanel);

        String titleText = "";
        String intermissionText = "";
        
        // Determine which intermission text to show based on the defeated enemy
        if (currentEnemyIndex == 1) { // After Junior (index 0) is defeated
            titleText = "TIER CLEARED: JUNIOR DIVISION";
            intermissionText = "The first wave of juniors collapses, sprawled across the abandoned classrooms and hallways. Their mischievous grins fade, replaced by respect for those who bested them. The newcomers realize that the fight club isn’t just a rumor — it’s real, and surviving it requires skill and teamwork. With the juniors cleared, the path deeper into the secret arena opens, leading toward the more experienced senior fighters.";
        } else if (currentEnemyIndex == 2) { // After Senior (index 1) is defeated
            titleText = "TIER CLEARED: SENIOR DIVISION";
            intermissionText = "The seniors fall one by one, their confident smirks replaced by grudging admiration. They were masters of strategy and precision, and defeating them required clever thinking and relentless effort. The group feels stronger, more synchronized, and prepared for the ultimate challenge that lies at the heart of the fight club: Ishiguro, the reigning champion of the secret arena.";
        } else {
            // This shouldn't be called unless the game flow is broken
            System.err.println("Called intermission at wrong index: " + currentEnemyIndex);
            startNextEnemy();
            return;
        }

        // Title Label
        JLabel titleLabel = new JLabel(titleText);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Text Box and Area Setup (reusing helper methods)
        JPanel textBox = createTranslucentTextBox();
        JTextArea storyText = createStoryTextArea(intermissionText);
        
        textBox.add(Box.createVerticalGlue());
        textBox.add(titleLabel);
        textBox.add(Box.createVerticalStrut(15));
        textBox.add(storyText);
        textBox.add(Box.createVerticalGlue());

        // NEXT BUTTON
        JButton nextBtn = createNextButton("CONTINUE");
        
        // CONTAINER FOR TEXTBOX AND BUTTON
        JPanel contentContainer = new JPanel();
        contentContainer.setOpaque(false);
        contentContainer.setLayout(new BoxLayout(contentContainer, BoxLayout.Y_AXIS));
        contentContainer.add(textBox);
        contentContainer.add(Box.createVerticalStrut(20));
        contentContainer.add(nextBtn);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        bgPanel.add(contentContainer, gbc);

        // Action: Resume to the next battle
        nextBtn.addActionListener(e -> startNextEnemy());

        revalidate();
        repaint();
    }

    // Helper method to create the semi-transparent text box panel
    private JPanel createTranslucentTextBox() {
        JPanel textBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 180)); // Translucent black background
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);
                g2.dispose();
            }
        };
        textBox.setOpaque(false);
        
        
        textBox.setLayout(new BoxLayout(textBox, BoxLayout.Y_AXIS));
        // Use padding (EmptyBorder) to define the minimum size around the text
        textBox.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); 
        return textBox;
    }
    
    // Helper method to create the JTextArea for story text
    private JTextArea createStoryTextArea(String text) {
        JTextArea storyText = new JTextArea(text);
        // Increased Font Size for better visibility/fit
        storyText.setFont(new Font("Serif", Font.PLAIN, 24)); 
        storyText.setLineWrap(true);
        storyText.setWrapStyleWord(true);
        storyText.setEditable(false);
        storyText.setOpaque(false);
        storyText.setForeground(Color.WHITE);
        storyText.setFocusable(false);
        storyText.setHighlighter(null);
        storyText.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Set a maximum column width (e.g., 60 columns) but let the height be determined by content
        storyText.setColumns(60);
        
        return storyText;
    }

    // Helper method to create the Next/Continue button
    private JButton createNextButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 24));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(20, 20, 20)); // Solid dark gray
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
                BorderFactory.createEmptyBorder(10, 30, 10, 30)
        ));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    // CHARACTER SELECTION
    private void initCharacterSelection() {
        getContentPane().removeAll();

        BackgroundPanel bgPanel = new BackgroundPanel(
                "OOPTurnBasedGame\\Assets\\background2.png",
                "OOPTurnBasedGame\\Assets\\background1.png"
        );
        bgPanel.setLayout(new BorderLayout());
        setContentPane(bgPanel);

        // Title label
        JLabel title = new JLabel("Choose your character:");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setOpaque(false);
        bgPanel.add(title, BorderLayout.NORTH);

        // Top and bottom rows for characters
        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        topRow.setOpaque(false);
        JPanel bottomRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 20));
        bottomRow.setOpaque(false);

        int index = 0;
        for (CharacterClass cc : availableCharacters) {
            ImageIcon img = new ImageIcon(getClass().getResource("/Assets/" + cc.getName() + ".png"));
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

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.add(topRow);
        wrapper.add(bottomRow);

        bgPanel.add(wrapper, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    // BATTLE START / RESTART
    private void startBattle() {
        // start at the first enemy for a new game
        currentEnemyIndex = 0;
        enemy = enemySequence[currentEnemyIndex].createEnemy();
        setupBattleScreen();
    }
    
    // Continue to the next enemy in the sequence
    private void startNextEnemy() {
        if (currentEnemyIndex < enemySequence.length) {
            enemy = enemySequence[currentEnemyIndex].createEnemy();
            setupBattleScreen();
             // Manually add the intro message for the new enemy
            battleLog.setText("A new enemy appears: " + enemy.getName() + "!\n");
        } else {
            // Should not happen, as this is checked before calling
        }
    }

    private void setupBattleScreen() {
    getContentPane().removeAll();

    BackgroundPanel bgPanel = new BackgroundPanel(
        "OOPTurnBasedGame\\Assets\\background2.png",
        "OOPTurnBasedGame\\Assets\\background1.png"
    );
    bgPanel.setLayout(new BorderLayout(10, 10)); 
    setContentPane(bgPanel);
    
    Color solidBlockColor = new Color(30, 30, 30);
    Color logBgColor = new Color(50, 50, 50);
    Color buttonBg = new Color(50, 50, 50);
    Color buttonHover = new Color(70, 70, 70);
    Color buttonBorder = new Color(150, 150, 150);

    // 1. STATUS CARDS AREA (TOP)
    
    JPanel cardsWrapper = new JPanel();
    cardsWrapper.setOpaque(false);
    cardsWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
    cardsWrapper.setLayout(new BorderLayout());

    JPanel cardsPanel = new JPanel(new GridLayout(1, 2, 50, 10));
    cardsPanel.setOpaque(false);
    
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

    JPanel centerWrapper = new JPanel();
    centerWrapper.setOpaque(false);
    centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
    centerWrapper.add(Box.createVerticalGlue());
    centerWrapper.add(cardsPanel);
    centerWrapper.add(Box.createVerticalGlue());
    
    cardsWrapper.add(centerWrapper, BorderLayout.CENTER);
    bgPanel.add(cardsWrapper, BorderLayout.NORTH);

    // ACTIONS AND LOG AREA (BOTTOM)

    JPanel controlsWrapper = new JPanel(new BorderLayout(0, 10));
    controlsWrapper.setBackground(solidBlockColor);
    controlsWrapper.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 
    
    //Log Area
    battleLog = new JTextArea(8, 40);
    battleLog.setEditable(false);
    battleLog.setLineWrap(true);
    battleLog.setWrapStyleWord(true);
    battleLog.setOpaque(true);
    battleLog.setBackground(logBgColor);
    battleLog.setForeground(Color.WHITE);
    battleLog.setFont(new Font("Monospaced", Font.PLAIN, 14));

    JScrollPane scrollPane = new JScrollPane(battleLog);
    scrollPane.setOpaque(true); 
    scrollPane.getViewport().setOpaque(true);
    scrollPane.getViewport().setBackground(logBgColor);
    scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "Battle Log"));
    scrollPane.setViewportBorder(null);
        
    skillPanel = new JPanel(new GridLayout(1, 0, 6, 6));
    skillPanel.setOpaque(true); 
    skillPanel.setBackground(solidBlockColor);
    skillPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE, 1), "Skills"));
    updateSkillButtons();
    
    defendBtn = createModernButton("Defend", buttonBg, buttonHover, buttonBorder);
    potionBtn = createModernButton("Potions", buttonBg, buttonHover, buttonBorder);
    runBtn = createModernButton("Run", buttonBg, buttonHover, buttonBorder);
    
    defendBtn.addActionListener(this::defendAction);
    potionBtn.addActionListener(this::potionAction);
    runBtn.addActionListener(this::runAction);

    JPanel actionPanel = new JPanel(new GridLayout(1, 3, 10, 0));
    actionPanel.setOpaque(true);
    actionPanel.setBackground(solidBlockColor);
    actionPanel.add(defendBtn);
    actionPanel.add(potionBtn);
    actionPanel.add(runBtn);

    // Combine skills and actions vertically
    JPanel controlsPanel = new JPanel();
    controlsPanel.setOpaque(true);
    controlsPanel.setBackground(solidBlockColor);
    controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
    skillPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    controlsPanel.add(skillPanel);
    controlsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    controlsPanel.add(actionPanel);
    
    // Assemble the controlsWrapper yadadee
    controlsWrapper.add(scrollPane, BorderLayout.CENTER);
    controlsWrapper.add(controlsPanel, BorderLayout.SOUTH);
    
    bgPanel.add(controlsWrapper, BorderLayout.SOUTH);


    // Finalization
    if (currentEnemyIndex == 0 && battleLog.getText().isEmpty()) {
        battleLog.setText("A wild " + enemy.getName() + " appears!\n");
    }

    refreshCards();
    revalidate();
    repaint();
}

    private JButton createModernButton(String text, Color bg, Color hover, Color border) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(border, 1));
        button.setPreferredSize(new Dimension(100, 35));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bg);
            }
        });
        return button;
    }

    // SKILL BUTTONS
    private void updateSkillButtons() {
        if (skillPanel == null) return;
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
        if (!"None".equals(chosenSkill.getEffect()))
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

    // ACTIONS
    private void defendAction(ActionEvent e) {
        battleLog.append("You brace for the enemy's attack.\n");
        disableActionButtons(); // Disable actions during enemy turn
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
        disableActionButtons();
        if (MacroLib.randInt(1, 100) <= 50) {
            battleLog.append("You successfully ran away! Game over.\n");
            showStartMenu();
        } else {
            battleLog.append("You failed to run away!\n");
            enemyTurnDelayed(false); // Enemy gets a turn
        }
    }

    // ENEMY TURN WITH DELAY
    private void enemyTurnDelayed(boolean playerDefending) {
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
                showIntermissionScreen();
            } else {
                // after boss killed -> victory
                showVictoryScreen();
            }
        }
    }

    private void showVictoryScreen() {
        getContentPane().removeAll();

        BackgroundPanel bgPanel = new BackgroundPanel(
            "OOPTurnBasedGame\\Assets\\background2.png",
            "OOPTurnBasedGame\\Assets\\background1.png"
        );
        bgPanel.setLayout(new BoxLayout(bgPanel, BoxLayout.Y_AXIS));
        setContentPane(bgPanel);

        //Victory Story Text
        String finalStory = "Turns out, the Principal was behind all of this. After the Principal collapses dramatically in the center of the Old Gym, papers and training equipment scattered around him. For years, he had ruled the secret fight club, unbeaten and feared by all who dared enter. Now, the students — Mark, Jeno, Clyde, Mico, and Sherwin — stand victorious. Their journey through the secret tiers has tested their strength, intellect, and resolve.\n\nThough the fight club remains hidden from the rest of the school, its influence on the group is undeniable. They have earned respect, proven their abilities, and formed bonds that go beyond friendship. The secret arena has revealed that survival depends not only on power but on teamwork, strategy, and courage. For now, the academy returns to its quiet routine, but the lessons and victories of the hidden fight club will stay with them forever — and whispers of new challengers hint that the arena’s battles may not be over.";
        JPanel textBox = createTranslucentTextBox();
        JTextArea storyText = createStoryTextArea(finalStory);
        
        JLabel titleLabel = new JLabel("CHAMPIONS OF CENTRAL ACADEMY!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        textBox.add(Box.createVerticalStrut(10));
        textBox.add(titleLabel);
        textBox.add(Box.createVerticalStrut(15));
        textBox.add(storyText);
        textBox.add(Box.createVerticalGlue());
        
        // UI Setup
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel victoryLabel = new JLabel("VICTORY!");
        victoryLabel.setFont(new Font("Arial", Font.BOLD, 72));
        victoryLabel.setForeground(Color.YELLOW);
        victoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(victoryLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton mainMenuBtn = new JButton("Back to Main Menu");
        mainMenuBtn.setFont(new Font("Arial", Font.BOLD, 24));
        mainMenuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuBtn.addActionListener(e -> showStartMenu());
        
        JPanel contentWrapper = new JPanel();
        contentWrapper.setOpaque(false);
        contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.Y_AXIS));
        contentWrapper.add(panel);
        contentWrapper.add(Box.createVerticalStrut(20));
        contentWrapper.add(textBox);
        contentWrapper.add(Box.createVerticalStrut(30));
        contentWrapper.add(mainMenuBtn);


        bgPanel.add(Box.createVerticalGlue());
        bgPanel.add(contentWrapper);
        bgPanel.add(Box.createVerticalGlue());

        revalidate();
        repaint();

    }

    private void checkPlayerStatus() {
        if (!player.isAlive()) {
            battleLog.append("You have been defeated!\n");
            if (!availableCharacters.isEmpty()) {
                JOptionPane.showMessageDialog(this, "You died! Choose another character.");
                chooseNewPlayerAfterDeath(); // resume with current enemy
            } else {
                battleLog.append("No more characters available. Game over.\n");
                disableActionButtons();
                // TODO: Add a proper Game Over screen here
            }
        }
    }

    // Choose replacement player after death without resetting enemy
    private void chooseNewPlayerAfterDeath() {
        getContentPane().removeAll();

        BackgroundPanel bgPanel = new BackgroundPanel(
                "OOPTurnBasedGame\\Assets\\background2.png",
                "OOPTurnBasedGame\\Assets\\background1.png"
        );
        bgPanel.setLayout(new BorderLayout());
        setContentPane(bgPanel);

        JLabel title = new JLabel("Choose a new character:");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setOpaque(false);
        bgPanel.add(title, BorderLayout.NORTH);

        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        topRow.setOpaque(false);
        JPanel bottomRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 20));
        bottomRow.setOpaque(false);

        int index = 0;
        for (CharacterClass cc : availableCharacters) {
            ImageIcon img = new ImageIcon(getClass().getResource("/Assets/" + cc.getName() + ".png"));
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

        bgPanel.add(wrapper, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    class BackgroundPanel extends JPanel {
        private Image bg1, bg2;

        public BackgroundPanel(String image1Path, String image2Path) {
            loadBackgrounds(image1Path, image2Path);
            setLayout(new BorderLayout());
            setOpaque(true); // the panel paints bg itself
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

            if (bg1 != null) {
                g.drawImage(bg1, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(new Color(20, 20, 20));
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            if (bg2 != null) {
                g.drawImage(bg2, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    // Button enabling/disabling
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
        if (playerCard != null && player != null) {
            playerCard.updateFromEntity(player);
        }
        if (enemyCard != null && enemy != null) {
            enemyCard.updateFromEntity(enemy);
        }
    }

    public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                Game game = new Game();
                game.setVisible(true);
            });
        }
    }
    class StatusCard extends JPanel {
        private final JLabel nameLabel;
        private final JProgressBar hpBar, manaBar;
        private final int ARC_SIZE = 25;

        public StatusCard() {
            setPreferredSize(new Dimension(350, 160));
            setOpaque(false);
            
            setBorder(BorderFactory.createEmptyBorder(12, 12, 14, 12));
            
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            // Name Label (Top)
            nameLabel = new JLabel("Name");
            nameLabel.setFont(new Font("Serif", Font.BOLD, 26));
            nameLabel.setForeground(Color.WHITE);
            nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            add(nameLabel);

            add(Box.createVerticalStrut(10));

            // HP Bar
            hpBar = new JProgressBar();
            hpBar.setStringPainted(true);
            hpBar.setForeground(new Color(200, 50, 50));
            hpBar.setBackground(new Color(100, 10, 10, 100));
            hpBar.setOpaque(false);
            hpBar.setAlignmentX(Component.LEFT_ALIGNMENT);
            // Constrain the bar height for a blocky look
            hpBar.setMaximumSize(new Dimension(350, 24));
            
            hpBar.setFont(new Font("Monospaced", Font.BOLD, 14));
            add(hpBar);

            // Spacing
            add(Box.createVerticalStrut(10));

            // Mana Bar
            manaBar = new JProgressBar();
            manaBar.setStringPainted(true);
            manaBar.setForeground(new Color(100, 100, 255));
            manaBar.setBackground(new Color(10, 10, 50, 100));
            manaBar.setOpaque(false);
            manaBar.setAlignmentX(Component.LEFT_ALIGNMENT);
            manaBar.setMaximumSize(new Dimension(350, 20));

            manaBar.setFont(new Font("Monospaced", Font.BOLD, 14));
            add(manaBar);
            
            add(Box.createVerticalGlue());
        }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(150, 0, 0, 120));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_SIZE, ARC_SIZE);

        g2.setColor(new Color(255, 50, 50, 180));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, ARC_SIZE, ARC_SIZE);

        g2.dispose();
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
    }
}