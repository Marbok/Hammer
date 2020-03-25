package util;

import java.util.Map;

public final class MapUtil {
    private MapUtil() {
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNonEmpty(Map map) {
        return !isEmpty(map);
    }
}
