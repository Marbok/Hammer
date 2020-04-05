package beaninfo;

import java.lang.reflect.Array;

public class InjectValues extends AbstractInjectArrayValue {

    private Object values;

    public InjectValues(final Class<?> clazz, String name, String[] values) {
        super(clazz, name);
        this.values = Array.newInstance(clazz.getComponentType(), values.length);
        for (int i = 0, n = values.length; i < n; i++) {
            Array.set(this.values, i, generateValue(clazz.getComponentType(), values[i]));
        }
//        this.values = clazz.cast(Stream.of(values).map(value -> generateValue(clazz.getComponentType(), value)).toArray());
    }

    @Override
    public Object getValue() {
        return values;
    }
}
