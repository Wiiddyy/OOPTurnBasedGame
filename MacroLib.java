

import java.util.Random;

public final class MacroLib {
    private static final Random RAND = new Random();

    public static final int PLAYER_MAX_HP = 100;
    public static final int ENEMY_MAX_HP = 80;

    public static int randInt(int min, int max) {
        if (min >= max) return min;
        return RAND.nextInt(max - min + 1) + min;
    }

    public static void clearConsole() {
        // Best-effort console clear (works on many terminals)
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}