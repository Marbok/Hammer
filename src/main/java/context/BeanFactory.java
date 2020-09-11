package context;

import beaninfo.BeanInfo;
import exceptions.CreateBeanException;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BeanFactory {

    private Config config;
    private Map<String, Object> container = new HashMap<>();

    /**
     * @param config information about context
     */
    public BeanFactory(Config config) {
        this.config = config;
        Collection<BeanInfo> beanInfos = config.getBeanInfos();
        for (BeanInfo beanInfo : beanInfos) {
            createBean(beanInfo);
        }
    }

    /**
     * create and configure bean
     * save it in container
     * <p>
     * if bean've created, then return it from container
     *
     * @param beanInfo information about bean
     * @return bean
     */
    @SneakyThrows
    private Object createBean(BeanInfo beanInfo) {
        Object bean = container.get(beanInfo.getName());
        if (bean != null) {
            return bean;
        }

        bean = newInstanceBean(beanInfo);
        setterInject(bean, beanInfo);

        container.put(beanInfo.getName(), bean);
        return bean;
    }

    /**
     * @param bean     bean
     * @param beanInfo information about bean
     */
    private void setterInject(Object bean, BeanInfo beanInfo) {
        beanInfo.getSetters()
                .forEach(method -> {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    Object[] paramValues = Arrays.stream(parameterTypes)
                            .map(type -> {
                                BeanInfo beanInfoParam = config.getBeanInfo(type);
                                return createBean(beanInfoParam);
                            })
                            .toArray();
                    try {
                        method.invoke(bean, paramValues);
                    } catch (Exception e) {
                        throw new CreateBeanException("Can't inject in setter in " + beanInfo.getClazz().getName());
                    }
                });
    }

    /**
     * @param beanInfo information about bean
     * @return created object, but not configured
     */
    @SneakyThrows
    private Object newInstanceBean(BeanInfo beanInfo) {
        Constructor<?> constructor = beanInfo.getConstructor();
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        if (parameterTypes.length == 0) {
            return beanInfo.getClazz().getConstructor().newInstance();
        } else {
            Object[] paramValues = Stream.of(parameterTypes)
                    .map(type -> {
                        BeanInfo beanInfoParam = config.getBeanInfo(type);
                        if (beanInfoParam == null) {
                            throw new CreateBeanException("Can't inject param in " + beanInfo.getName());
                        }
                        return createBean(beanInfoParam);
                    }).toArray();
            return beanInfo.getClazz().getConstructor(parameterTypes).newInstance(paramValues);
        }
    }


    /**
     * @param clazz bean's class
     * @return bean
     */
    public Object getBean(Class<?> clazz) {
        BeanInfo beanInfo = config.getBeanInfo(clazz);
        return container.get(beanInfo.getName());
    }
}
