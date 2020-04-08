package beaninfo.inject_param;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.lang.reflect.Array;
import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class InjectValues extends AbstractInjectParam implements ValueGenerator {

    private Object values;

    public InjectValues(final Class<?> clazz, String name, String[] values) {
        super(clazz, name);
        this.values = Array.newInstance(clazz.getComponentType(), values.length);
        for (int i = 0, n = values.length; i < n; i++) {
            Array.set(this.values, i, generateValue(clazz.getComponentType(), values[i]));
        }
    }

    @Override
    public Object createObjectForInject(Function<String, Object> initBeanByRef) {
        return values;
    }
}
