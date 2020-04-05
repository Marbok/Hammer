package beaninfo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public interface ValueGenerator {

    Map<Class<?>, Function<String, Object>> VALUES_GENERATOR = getValuesGenerator();

    private static Map<Class<?>, Function<String, Object>> getValuesGenerator() {
        Map<Class<?>, Function<String, Object>> valuesGenerator = new HashMap<>();
        valuesGenerator.put(byte.class, Byte::valueOf);
        valuesGenerator.put(int.class, Integer::valueOf);
        valuesGenerator.put(long.class, Long::valueOf);
        valuesGenerator.put(float.class, Float::valueOf);
        valuesGenerator.put(double.class, Double::valueOf);
        valuesGenerator.put(boolean.class, Boolean::valueOf);
        valuesGenerator.put(char.class, s -> s.charAt(0));
        valuesGenerator.put(String.class, s -> s);
        return valuesGenerator;
    }

    default Object generateValue(Class<?> clazz, String value) {
        Function<String, Object> generateFunction = VALUES_GENERATOR.get(clazz);
        if (generateFunction == null) {
            throw new UnsupportedOperationException("Can't inject type: " + clazz.getName());
        }
        return generateFunction.apply(value);
    }
}
