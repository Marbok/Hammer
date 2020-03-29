package beaninfo;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
public class InjectPrimitive extends AbstractInjectParam {

    private Object value;

    public InjectPrimitive(Class<?> clazz, String value) {
        super(clazz);
        this.value = getPrimitiveValue(value, clazz);
    }

    private Object getPrimitiveValue(String value, Class<?> clazz) {
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
            default:
                throw new IllegalStateException("Not Primitive type: " + clazz.getName());
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
