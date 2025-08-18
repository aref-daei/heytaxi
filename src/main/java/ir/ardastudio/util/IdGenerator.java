package ir.ardastudio.util;

import java.util.UUID;

public class IdGenerator {
    public static String generate() {
        String appCode = "10410112111697120105";
        String uuid = UUID.randomUUID().toString();
        String combined = appCode + uuid.replace("-", "");
        UUID customUUID = UUID.nameUUIDFromBytes(combined.getBytes());
        return customUUID.toString();
    }
}
