package util;

public final class StringUtil {

    private StringUtil() {
    }

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isNonEmpty(String s) {
        return !isEmpty(s);
    }
}
