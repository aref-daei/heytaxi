package ir.ardastudio.util;

public class IdGenerator {
    private static int counter = 0;

    public static int generate() {
        return ++counter;
    }
}
