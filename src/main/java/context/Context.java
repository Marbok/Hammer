package context;

import beaninfo.BeanInfo;

import java.util.Collection;

public interface Context {
    BeanInfo getBeanInfo(String beanName);
    Collection<BeanInfo> getAllBeanInfo();
}
