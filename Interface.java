public class Interface {

    public static void title() {
        System.out.println("========================================");
        System.out.println("        FIGHT CLUB TURN BASED GAME      ");
        System.out.println("========================================");
    }

    //public static void showStats(String name, int hp, int maxHp,int mana, int maxMana) {
    //System.out.printf("%s: HP %d/%d%n MANA %d/%d%n", name, Math.max(hp, 0), maxHp, Math.max(mana, 0), maxMana);
    ///}
    
    public static void showStats(String name, int hp, int maxHp, int mana, int maxMana) {
    System.out.printf("%s%n  HP:   %d/%d%n  Mana: %d/%d%n", name, Math.max(hp, 0), maxHp, Math.max(mana, 0), maxMana);
    }


    public static void showBattleHeader() {
        System.out.println("\n========== BATTLE ==========");
    }

    public static void pauseLine() {
        System.out.println("\n(Press Enter to continue)");
        try { System.in.read(); } catch (Exception ignored) {}
    }

    public static void printCentered(String s) {
        System.out.println(s);
    }
}