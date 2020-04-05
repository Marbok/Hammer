package beaninfo;

public abstract class AbstractInjectReference extends AbstractInjectParam {
    public AbstractInjectReference(Class<?> clazz, String name) {
        super(clazz, name);
    }

    @Override
    public boolean isReference() {
        return true;
    }
}
