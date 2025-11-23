import java.util.List;
public class EnemyType {

    private final String name;
    private final int maxHp;
    private final int maxMana;
    private final List<Skill> skills;

    private EnemyType(Builder b) {
        this.name = b.name;
        this.maxHp = b.maxHp;
        this.maxMana = b.maxMana;
        this.skills = b.skills;
    }

    // Gettars
    public String getDisplayName() { return name; }
    public int getMaxHp() { return maxHp; }
    public int getMaxMana() { return maxMana; }
    public List<Skill> getSkills() { return skills; }

    public Enemy createEnemy() {
        return new Enemy(name, maxHp, maxMana, skills);
    }


    //Builder class para sa flexibile object creation B))
    public static class Builder {
        private String name;
        private int maxHp;
        private int maxMana;
        private List<Skill> skills;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder maxHp(int maxHp) {
            this.maxHp = maxHp;
            return this;
        }

        public Builder maxMana(int maxMana) {
            this.maxMana = maxMana;
            return this;
        }

        public Builder skills(List<Skill> skills) {
            this.skills = skills;
            return  this;
        }

        //build method pang finalize ang enemytype object
        public EnemyType build() {
            return new EnemyType(this);
        }

    }
    
    public static final EnemyType JUNIOR = new EnemyType.Builder()
            .name("Junior Student")
            .maxHp(100)
            .maxMana(80)
            .skills(List.of(
                    new Skill("Watatatoo", 20, 10, "Bleed")
            ))
            .build();
    
    public static final EnemyType SENIOR = new EnemyType.Builder()
            .name("Senior Student")
            .maxHp(100)
            .maxMana(80)
            .skills(List.of(
                    new Skill("Watashi", 50, 100, "None")
            ))
            .build();

    public static final EnemyType BOSS = new EnemyType.Builder()
            .name("Ishiguro")
            .maxHp(150)
            .maxMana(999)
            .skills(List.of(
                new Skill("Borderline Unhinged", 20, 250, "None")
            ))
            .build();
}
