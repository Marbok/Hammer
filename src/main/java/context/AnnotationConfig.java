package context;

import annotations.Bean;
import annotations.Inject;
import beaninfo.BeanInfo;
import exceptions.BeanInfoException;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        BeanInfo info = new BeanInfo();
        info.setClazz(clazz);
        info.setName(clazz.getName());
        List<Constructor<?>> annotatedConstructor = Arrays.stream(clazz.getConstructors())
                .filter(constructor -> constructor.isAnnotationPresent(Inject.class))
                .collect(Collectors.toList());
        if (annotatedConstructor.size() == 0 && haveDefaultConstructor(clazz)) {
            info.setConstructor(clazz.getConstructor());
        } else if (annotatedConstructor.size() == 1) {
            info.setConstructor(annotatedConstructor.get(0));
        } else {
            throw new BeanInfoException("Can't find constructor in " + clazz);
        }

        List<Method> setters = Stream.of(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(Inject.class))
                .collect(Collectors.toList());
        info.setSetters(setters);
        return info;
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
