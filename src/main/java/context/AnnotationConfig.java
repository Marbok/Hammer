package context;

import annotations.Bean;
import annotations.Inject;
import beaninfo.BeanInfo;
import beaninfo.Scope;
import exceptions.BeanInfoException;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static beaninfo.Scope.SINGLETON;
import static util.ClassUtil.haveDefaultConstructor;

/**
 * This configuration use annotation
 * for producing information about bean
 */
public class AnnotationConfig implements Config {

    private final Map<String, BeanInfo> beanInfos;

    /**
     * @param packageToScan package with beans
     */
    public AnnotationConfig(String packageToScan) {
        Reflections scanner = new Reflections(packageToScan);
        Set<Class<?>> beanClasses = scanner.getTypesAnnotatedWith(Bean.class);
        beanInfos = beanClasses.stream()
                .map(this::createBeanInfo)
                .collect(Collectors.toMap(beanInfo -> beanInfo.getClazz().getInterfaces()[0].getName(), beanInfo -> beanInfo));
    }

    /**
     * @param clazz bean's class
     * @return information about bean
     */
    @SneakyThrows
    private BeanInfo createBeanInfo(Class<?> clazz) {
        return new BeanInfo()
                .setClazz(clazz)
                .setName(clazz.getName())
                .setScope(getScope(clazz))
                .setConstructor(getConstructor(clazz))
                .setSetters(getSetters(clazz));
    }

    /**
     * @param clazz bean's class
     * @return bean's scope
     */
    private Scope getScope(Class<?> clazz) {
        annotations.Scope scope = clazz.getAnnotation(annotations.Scope.class);
        return scope == null ? SINGLETON : scope.value();
    }

    /**
     * @param clazz bean's class
     * @return setters for inject
     */
    private List<Method> getSetters(Class<?> clazz) {
        return Stream.of(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(Inject.class))
                .collect(Collectors.toList());
    }

    /**
     * @param clazz bean's class
     * @return constructor for creating class
     */
    @SneakyThrows
    private Constructor<?> getConstructor(Class<?> clazz) {
        List<Constructor<?>> annotatedConstructor = Arrays.stream(clazz.getConstructors())
                .filter(constructor -> constructor.isAnnotationPresent(Inject.class))
                .collect(Collectors.toList());
        if (annotatedConstructor.size() == 0 && haveDefaultConstructor(clazz)) {
            return clazz.getConstructor();
        } else if (annotatedConstructor.size() == 1) {
            return annotatedConstructor.get(0);
        } else {
            throw new BeanInfoException("Can't find constructor in " + clazz);
        }
    }

    @Override
    public BeanInfo getBeanInfo(Class<?> clazz) {
        return beanInfos.get(clazz.getName());
    }

    @Override
    public Collection<BeanInfo> getBeanInfos() {
        return beanInfos.values();
    }
}
