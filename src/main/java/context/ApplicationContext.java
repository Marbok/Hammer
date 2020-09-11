package context;

public interface ApplicationContext {

    /**
     * get bean by class
     *
     * @param clazz class of bean
     * @return bean
     */
    <T> T getBean(Class<T> clazz);
}
