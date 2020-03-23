package context;

import beaninfo.BeanInfo;

public interface Context {
    BeanInfo getBeanInfo(String beanName);
}
