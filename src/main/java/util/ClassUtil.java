package util;

import static java.util.Arrays.asList;

public final class ClassUtil {

    private ClassUtil() {
    }

    public static boolean haveInterface(Class<?> clazz, Class<?> checkInterface) {
        return clazz.equals(checkInterface) || asList(clazz.getInterfaces()).contains(checkInterface);
    }
}
