public class Interface {

    public static void title() {
        System.out.println("==================================");
        System.out.println("        YAWA TURN-BASED GAME      ");
        System.out.println("==================================");
    }

    public static void showStats(String name, int hp, int maxHp) {
        System.out.printf("%s: HP %d/%d%n", name, Math.max(hp, 0), maxHp);
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
