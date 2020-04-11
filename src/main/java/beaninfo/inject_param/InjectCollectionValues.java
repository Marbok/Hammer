package beaninfo.inject_param;

import beaninfo.inject_param.generators.CollectionGenerator;
import beaninfo.inject_param.generators.ValueGenerator;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;
import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class InjectCollectionValues extends AbstractInjectParam implements ValueGenerator, CollectionGenerator {

    private final Collection<Object> values;

    public InjectCollectionValues(Class<?> clazz, String name, Class<?> componentType, String[] values) {
        super(clazz, name);

        try {
            this.values = generateCollection(clazz);
        } catch (Exception e) {
            throw new IllegalStateException("Bean isn't a collection: " + name);
        }

        for (String value : values) {
            this.values.add(generateValue(componentType, value));
        }
    }

    @Override
    public Object createObjectForInject(Function<String, Object> initBeanByRef) {
        return values;
    }
}
