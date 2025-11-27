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
            .maxHp(150)
            .maxMana(67)
            .skills(List.of(
                    new Skill ("Scissor Cut", 20, 12, "Bleed"),
                    new Skill ("Notebook Throw", 15, 10, "None"),
                    new Skill ("Pencil Jab", 10, 5, "Bleed")
            ))
            .build();
    
    public static final EnemyType SENIOR = new EnemyType.Builder()
            .name("Senior Student")
            .maxHp(220)
            .maxMana(100)
            .skills(List.of(
                    new Skill ("Elite Ball Knowledge", 35, 20, "None"),
                    new Skill ("Upperclass Kick", 28, 15, "None"),
                    new Skill ("Graduation Punch", 32, 18, "None")
            ))
            .build();

    public static final EnemyType BOSS = new EnemyType.Builder()
            .name("Principal")
            .maxHp(650)
            .maxMana(999)
            .skills(List.of(
                new Skill ("Called to Office", 40, 60, "None"),
                new Skill ("Expelled", 55, 80, "None"),
                new Skill ("Suspension", 50, 70, "None")
            ))
            .build();
}
