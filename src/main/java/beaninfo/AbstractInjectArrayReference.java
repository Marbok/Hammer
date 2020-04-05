package beaninfo;

public abstract class AbstractInjectArrayReference extends AbstractInjectReference {

    public AbstractInjectArrayReference(Class<?> clazz, String name) {
        super(clazz, name);
    }

    @Override
    public boolean isArray() {
        return true;
    }
}
