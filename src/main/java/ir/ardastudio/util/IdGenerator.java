package ir.ardastudio.util;

import java.security.SecureRandom;

public class IdGenerator {
    public static int generate() {
        SecureRandom random = new SecureRandom();
        int id = 0;
        for (int i = 0; i < 8; i++) {
            id += (int) (random.nextInt(1,10) * Math.pow(10, i));
        }
        return id;
    }
}
