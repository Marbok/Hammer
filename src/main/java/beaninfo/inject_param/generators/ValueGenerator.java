package beaninfo.inject_param.generators;

import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public interface ValueGenerator {

    Map<Class<?>, Function<String, Object>> VALUES_GENERATOR = getValuesGenerator();

    private static Map<Class<?>, Function<String, Object>> getValuesGenerator() {
        Map<Class<?>, Function<String, Object>> valuesGenerator = new HashMap<>();
        valuesGenerator.put(byte.class, Byte::valueOf);
        valuesGenerator.put(Byte.class, Byte::valueOf);
        valuesGenerator.put(int.class, Integer::valueOf);
        valuesGenerator.put(Integer.class, Integer::valueOf);
        valuesGenerator.put(long.class, Long::valueOf);
        valuesGenerator.put(Long.class, Long::valueOf);
        valuesGenerator.put(float.class, Float::valueOf);
        valuesGenerator.put(Float.class, Float::valueOf);
        valuesGenerator.put(double.class, Double::valueOf);
        valuesGenerator.put(Double.class, Double::valueOf);
        valuesGenerator.put(boolean.class, Boolean::valueOf);
        valuesGenerator.put(Boolean.class, Boolean::valueOf);
        valuesGenerator.put(char.class, s -> s.charAt(0));
        valuesGenerator.put(Character.class, s -> s.charAt(0));
        valuesGenerator.put(String.class, s -> s);
        return valuesGenerator;
    }

    @SneakyThrows
    default Object generateValue(Class<?> clazz, String value) {
        if (clazz.isEnum()) {
            return clazz.getDeclaredMethod("valueOf", String.class).invoke(clazz, value);
        }

        Function<String, Object> generateFunction = VALUES_GENERATOR.get(clazz);
        if (generateFunction == null) {
            throw new UnsupportedOperationException("Can't inject type: " + clazz.getName());
        }
        return generateFunction.apply(value);
    }
}
