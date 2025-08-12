package ir.ardastudio.util;

public class IdGenerator {
    private static long counter = 0;

    public static long generate() {
        return ++counter;
    }
}
