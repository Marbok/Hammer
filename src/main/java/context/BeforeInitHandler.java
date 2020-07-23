package context;

import exceptions.CreateBeanException;

/**
 * This class is used for additional initialization before {@link annotations.InitMethod}.
 */
public interface BeforeInitHandler {

    /**
     * Method is called before {@link annotations.InitMethod}
     *
     * @param bean     - object
     * @param beanName - name of bean
     * @return it depends from realization. It's may be proxy or the input bean
     * @throws CreateBeanException - if method can't create bean
     */
    Object handle(Object bean, String beanName) throws CreateBeanException;

}
