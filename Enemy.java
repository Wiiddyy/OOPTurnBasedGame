import java.util.List;

public class Enemy extends Entity {

    public Enemy(String name, int maxHp, int maxMana, List<Skill> skills) {
        super(
            name,
            maxHp,
            maxMana,
            0,
            skills
        );
    }

    public Skill chooseSkill() {
        List<Skill> usable = skills.stream()
                .filter(s -> s.getManaCost() <= getMana())
                .toList();

        if (usable.isEmpty()) {
            return null;
        }

        return usable.get(MacroLib.randInt(0, usable.size() - 1));
    }


    // here mo choose ang AI sa skills to pick (This whole code wont work if mawala ni kay abstact sha, ts some bs)
    @Override
    public Skill chooseAction(Entity target) {
        return chooseSkill();
    }

    @Override
    public String toString() {
        return getName() + " HP: " + getHp() + "/" + getMaxHp();
    }
}
