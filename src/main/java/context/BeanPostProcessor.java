package context;

import exceptions.CreateBeanException;


public interface BeanPostProcessor {

    Object beforeInitialization(Object bean, String beanName) throws CreateBeanException;

    Object afterInitialization(Object bean, String beanName) throws CreateBeanException;
}
