package context;

import beaninfo.BeanInfo;
import beaninfo.inject_param.AbstractInjectParam;
import exceptions.CreateBeanException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import util.CollectionsUtil;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static beaninfo.Scope.SINGLETON;

@Log4j2
public class BeanFactory {

    public static final String PREFIX_SETTER = "set";

    private final Context context;
    private final Map<String, Object> container = new HashMap<>();

    public BeanFactory(Context context) {
        this.context = context;
        container.put("applicationContext", context);
        container.put("beanFactory", this);
        context.getAllBeanInfo().forEach(this::initBean);
    }

    private Object initBean(BeanInfo beanInfo) {
        Object o = container.get(beanInfo.getName());
        if (o != null) {
            return o;
        }

        log.debug("Start to create bean: " + beanInfo.getName());
        o = createBean(beanInfo);

        infectParamBySetters(o, beanInfo);

        if (SINGLETON.equals(beanInfo.getScope())) {
            container.put(beanInfo.getName(), o);
        }

        log.debug("Created bean: " + beanInfo.getName());
        return o;
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

    // TODO while this method is used for test, may be need kill it after develop
    public Object getBean(String name) {
        return initBean(context.getBeanInfo(name));
    }

}
