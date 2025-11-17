import java.util.List;


// gi enum to contain all enemy type of the game, each enemy has their own stats and skills.
public enum  EnemyType {
    JUNIOR("Junior", 120, 30, List.of(
        new Skill("Slash", 20, 5, "None"),
        new Skill("Bleed", 15, 10, "Bleed"),
        new Skill("Charge", 25, 15, "Stun")
    ));


    // stats stored in enum
    String name;
    int hp;
    int attack;
    List<Skill> skills;

    EnemyType(String name, int hp, int attack, List<Skill> skills) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.skills = skills;
    }


    // creates new Enemy based on this type
    public Enemy create() {
        return new Enemy(name, hp, attack, skills);
    }
}
