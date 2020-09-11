package context;

import beaninfo.BeanInfo;

import java.util.Collection;

/**
 * Context configuration
 */
public interface Config {
    /**
     * @param clazz bean's class
     * @return information about bean by class
     */
    BeanInfo getBeanInfo(Class<?> clazz);

    /**
     * @return information about all beans
     */
    Collection<BeanInfo> getBeanInfos();
}
