import java.util.List;
// Base Class for the enemies
public class Enemy {
    String name;
    int maxHp;
    int hp;
    int maxMana;
    int mana;
    int attack;
    List<Skill> skills; // Para ma list unsa nga skills ma use sa enemy


    // constructor for enemy with stats and skills
    public Enemy(String name, int maxHp, int attack, List<Skill> skills) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp; // indicator nga enemy starts with max HP.
        this.attack = attack;
        this.skills = skills;
        this.maxMana = maxMana;
        this.mana = maxMana;
    }

    // Reads enemy infos yadadee yadadoo.
    public String getName() {
        return name;
    } // retruns the enemy name

    public int getHp() {
        return  hp;
    } // returns the enemy's current HP

    public int getMaxHp() {
        return maxHp;
    } // Returns the enemy's max HP

    public int getMana() {
        return mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getAttack() {
        return attack;
    } // Retrns the enemy's base attack power

    public List<Skill> getSkills() {
        return skills;
    } // return the list of skills this enemy can use


    // checks if the enemy is alive
    public boolean isAlive() {
        return hp > 0;
    }

    // setter for current mana
    public void setMana(int mana) {
        this.mana = mana;
    }

    // setter for max mana
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    // reduce enemy HP when attacked
    public void takeDamage(int amount) {
        hp -= amount;
        if (hp < 0) {
            hp = 0; // para d ma negative ang HP
        }
    }

    // reduce mana when using a skill
    public void useMana(int amount) {
        mana -= amount;
        if (mana < 0) {
            mana = 0;
        }
    }



    // enemy picks random skills in their skill list
    public Skill chooseSkill() {
        if (skills == null || skills.isEmpty()) {
            return  null;
        } // tig check if naay skills ang enemy (Thanks sir Jeff for giving the idea of a checker) !

        // creates a list of skills that the enemy has enough mana to use churva2
        List<Skill> usableSkills = skills.stream()
        .filter(s -> s.getManaCost() <= mana)
        .toList();

        // if no usuable skill because no mana
        if (usableSkills.isEmpty()) {
            return null;
        }

        return usableSkills.get(MacroLib.randInt(0, usableSkills.size() - 1)); // generates a random index to pick a random skills sa skill list !
    }

    @Override
    public String toString() {
        return name + " HP: " + hp + "/" + maxHp;
    }
}