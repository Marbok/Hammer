package beaninfo;

public abstract class AbstractInjectArrayValue extends AbstractInjectValue {

    public AbstractInjectArrayValue(Class<?> clazz, String name) {
        super(clazz, name);
    }

    @Override
    public boolean isArray() {
        return true;
    }
}
