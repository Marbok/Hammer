package beaninfo;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class InjectValue extends AbstractInjectSingleValue {

    private Object value;

    public InjectValue(Class<?> clazz, String name, String value) {
        super(clazz, name);
        this.value = generateValue(clazz, value);
    }

    @Override
    public Object getValue() {
        return value;
    }

}
