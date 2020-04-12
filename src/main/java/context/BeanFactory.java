package context;

import annotations.InitMethod;
import beaninfo.BeanInfo;
import beaninfo.inject_param.AbstractInjectParam;
import exceptions.CreateBeanException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import util.CollectionsUtil;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static beaninfo.Scope.SINGLETON;
import static java.util.Arrays.asList;

@Log4j2
public class BeanFactory {

    public static final String PREFIX_SETTER = "set";

    private final Context context;
    private final Map<String, Object> container = new HashMap<>();
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public BeanFactory(Context context) {
        this.context = context;
        container.put("applicationContext", context);
        container.put("beanFactory", this);

        context.getAllBeanInfo()
                .stream()
                .filter(this::isBeanPostProcessor)
                .forEach(this::initBeanPostProcessor);

        context.getAllBeanInfo()
                .stream()
                .filter(this::isNotBeanPostProcessor)
                .forEach(this::initBean);
    }


    private void initBeanPostProcessor(BeanInfo beanInfo) {
        log.debug("Start to create BeanPostProcessor: " + beanInfo.getName() + ", " + beanInfo.getClazz());
        BeanPostProcessor o = (BeanPostProcessor) createBean(beanInfo);
        infectParamBySetters(o, beanInfo);
        log.debug("Created BeanPostProcessor: " + beanInfo.getName() + ", " + beanInfo.getClazz());
        beanPostProcessors.add(o);
    }

    private Object initBean(BeanInfo beanInfo) {
        Object o = container.get(beanInfo.getName());
        if (o != null) {
            return o;
        }

        log.debug("Start to create bean: " + beanInfo.getName());
        o = createBean(beanInfo);
        infectParamBySetters(o, beanInfo);
        log.debug("Created bean: " + beanInfo.getName());

        beforeInitializationMethodInvoke(o, beanInfo.getName());
        invokeInitMethod(o);
        afterInitializationMethodInvoke(o, beanInfo.getName());

        if (SINGLETON.equals(beanInfo.getScope())) {
            container.put(beanInfo.getName(), o);
        }

        return o;
    }

    private void beforeInitializationMethodInvoke(Object bean, String name) {
        for (var beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.beforeInitialization(bean, name);
        }
    }


    private void invokeInitMethod(Object o) {
        try {
            for (var method : o.getClass().getMethods()) {
                if (method.isAnnotationPresent(InitMethod.class)) {
                    log.info("Invoke init-method in bean");
                    method.invoke(o);
                }
            }
        } catch (Exception e) {
            throw new CreateBeanException("Can't invoke init-method", e);
        }
    }

    private void afterInitializationMethodInvoke(Object bean, String name) {
        for (var beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.afterInitialization(bean, name);
        }
    }

    private void infectParamBySetters(Object bean, BeanInfo beanInfo) {
        List<AbstractInjectParam> setterParams = beanInfo.getSetterParams();

        if (CollectionsUtil.isEmpty(setterParams)) {
            return;
        }

        setterParams.forEach(param -> {
            try {
                String setterName = PREFIX_SETTER + StringUtils.capitalize(param.getName());
                bean.getClass()
                        .getDeclaredMethod(setterName, param.getClazz())
                        .invoke(bean, param.createObjectForInject(s -> initBean(context.getBeanInfo(s))));
            } catch (Exception e) {
                throw new CreateBeanException("Can't inject in setter: " + param.getName() + " in bean: " + beanInfo.getName(), e);
            }
        });

    }

    private Object createBean(BeanInfo beanInfo) {
        try {
            Constructor<?> actualCons = getActualConstructor(beanInfo);
            List<AbstractInjectParam> constructorParams = beanInfo.getConstructorParams();
            if (CollectionsUtil.isEmpty(constructorParams)) {
                return actualCons.newInstance();
            }

            Object[] parameters = constructorParams.stream()
                    .map(param -> param.createObjectForInject(s -> initBean(context.getBeanInfo(s))))
                    .toArray();

            return actualCons.newInstance(parameters);
        } catch (Exception e) {
            throw new CreateBeanException("Can't create bean: " + beanInfo.getName(), e);
        }
    }

    private Constructor<?> getActualConstructor(BeanInfo beanInfo) {
        if (CollectionsUtil.isEmpty(beanInfo.getConstructorParams())) {
            return getDefaultConstructor(beanInfo);
        }
        try {
            Class<?>[] parameterTypes = beanInfo
                    .getConstructorParams()
                    .stream()
                    .map(AbstractInjectParam::getClazz)
                    .toArray(Class<?>[]::new);
            return beanInfo.getClazz().getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new CreateBeanException("Wrong arguments for constructor in bean: " + beanInfo.getName());
        }
    }


    private Constructor<?> getDefaultConstructor(BeanInfo beanInfo) {
        try {
            return beanInfo.getClazz().getConstructor();
        } catch (NoSuchMethodException e) {
            throw new CreateBeanException("Need parameters or constructor without arguments for bean: " + beanInfo.getName(), e);
        }
    }

    private boolean isBeanPostProcessor(BeanInfo beanInfo) {
        return asList(beanInfo.getClazz().getInterfaces()).contains(BeanPostProcessor.class);
    }

    private boolean isNotBeanPostProcessor(BeanInfo beanInfo) {
        return !isBeanPostProcessor(beanInfo);
    }

    public Object getBean(String name) {
        return initBean(context.getBeanInfo(name));
    }

}
