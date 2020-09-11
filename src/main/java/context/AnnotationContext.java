package context;

/**
 * Create context base on annotation
 */
public class AnnotationContext implements ApplicationContext {

    private BeanFactory beanFactory;

    /**
     * @param scanningPath package, which need scan
     */
    public AnnotationContext(String scanningPath) {
        beanFactory = new BeanFactory(new AnnotationConfig(scanningPath));
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return (T) beanFactory.getBean(clazz);
    }
}
