package beaninfo;

public class InjectReferences extends AbstractInjectArrayReference {
    private String[] references;

    public InjectReferences(Class<?> clazz, String name, String[] references) {
        super(clazz, name);
        this.references = references;
    }

    @Override
    public Object getValue() {
        return references;
    }

}
