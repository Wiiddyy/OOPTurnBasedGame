import java.util.List;


// Base Class for the enemies
public class Enemy {
    String name;
    int maxHp;
    int hp;
    int attack;
    List<Skill> skills; // Para ma list unsa nga skills ma use sa enemy


    // constructor for enemy with stats and skills
    public Enemy(String name, int maxHp, int attack, List<Skill> skills) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp; // indicator nga enemy starts with max HP.
        this.attack = attack;
        this.skills = skills;
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

    // reduce enemy HP when attacked
    public void takeDamage(int amount) {
        hp -= amount;
        if (hp < 0) {
            hp = 0; // para d ma negative ang HP
        }
    }

    // enemy picks random skills in their skill list
    public Skill chooseSkill() {
        if (skills == null || skills.isEmpty()) {
            return  null;
        } // tig check if naay skills ang enemy (Thanks sir Jeff for giving the idea of a checker) !

        return skills.get(MacroLib.randInt(0, skills.size() - 1)); // generates a random index to pick a random skills sa skill list !
    }
}
