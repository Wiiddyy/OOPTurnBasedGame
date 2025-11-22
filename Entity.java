import java.util.List;

public abstract class Entity {
    protected String name;

    protected int hp;
    protected int maxHp;

    protected int mana;
    protected int maxMana;

    protected int attack;

    // dre lng ni butang kay gamit ni sa Player ug Enemy
    protected List<Skill> skills;

    public Entity(String name, int maxHp, int maxMana, int attack, List<Skill> skills) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;

        this.maxMana = maxMana;
        this.mana = maxMana;

        this.attack = attack;

        this.skills = skills;
    }

    // Encapsulation ni ha sa getters, pretty much the same sa Enemy.java, just need for testings.
    // Transfer nlng dre tanan from Enemy.java to Entity.java

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getMana() {
        return mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getAttack() {
        return attack;
    }


    public boolean isAlive() {
        return hp > 0;
    }


    // Common methods nga dapat naa sa tanan Entity
    public void takeDamage(int dmg) {
        hp = Math.max(0, hp - dmg); // dili ni mo minus sa 0
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount); // fixed nga dili mo lapas sa maxHP BOGO KAA
    }

    public void useMana(int amount) {
        mana = Math.max(0, mana - amount);
    }

    public void restoreMana(int amount) {
        mana = Math.min(maxMana, mana + amount);
    }

    public abstract Skill chooseAction(Entity target);
}
