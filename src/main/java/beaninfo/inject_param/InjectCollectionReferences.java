package beaninfo.inject_param;

import beaninfo.inject_param.generators.CollectionGenerator;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;
import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class InjectCollectionReferences extends AbstractInjectParam implements CollectionGenerator {

    private final String[] references;
    private final Class<?> subClass;

    public InjectCollectionReferences(Class<?> injectClass, String name, Class<?> subClass, String[] references) {
        super(injectClass, name);
        this.references = references;
        this.subClass = subClass;
    }


    @Override
    public Object createObjectForInject(Function<String, Object> initBeanByRef) {

        Collection<Object> injectBeans;
        try {
            injectBeans = generateCollection(clazz);
        } catch (Exception e) {
            throw new IllegalStateException("Bean isn't a collection: " + name);
        }

        for (var reference : references) {
            Object bean = initBeanByRef.apply(reference);
            if (!subClass.equals(bean.getClass())) throw new IllegalStateException("Not consistent types in list");
            injectBeans.add(bean);
        }
        return injectBeans;
    }
}
