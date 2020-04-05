package beaninfo;

public abstract class AbstractInjectSingleValue extends AbstractInjectValue {

    public AbstractInjectSingleValue(Class<?> clazz, String name) {
        super(clazz, name);
    }

    @Override
    public boolean isArray() {
        return false;
    }
}
