package beaninfo;

public abstract class AbstractInjectSingleReference extends AbstractInjectReference {
    public AbstractInjectSingleReference(Class<?> clazz, String name) {
        super(clazz, name);
    }

    @Override
    public boolean isArray() {
        return false;
    }
}
