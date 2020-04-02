package beaninfo;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class InjectValue extends AbstractInjectParam {

    private Object value;

    public InjectValue(Class<?> clazz, String value) {
        this(clazz, null, value);
    }

    public InjectValue(Class<?> clazz, String name, String value) {
        super(clazz, name);
        this.value = getInjectValue(value, clazz);
    }

    private Object getInjectValue(String value, Class<?> clazz) {
        String name = clazz.getName();
        switch (name) {
            case "byte":
                return Byte.valueOf(value);
            case "int":
                return Integer.valueOf(value);
            case "long":
                return Long.valueOf(value);
            case "float":
                return Float.valueOf(value);
            case "double":
                return Double.valueOf(value);
            case "boolean":
                return Boolean.valueOf(value);
            case "char":
                return value.charAt(0);
            case "java.lang.String":
                return value;
            default:
                throw new IllegalStateException("Can't inject type: " + clazz.getName());
        }
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean isReference() {
        return false;
    }
}
