package context;

public class JsonApplicationContext implements ApplicationContext {

    private final BeanFactory beanFactory;

    public JsonApplicationContext(String path) {
        beanFactory = new BeanFactory(new JsonContext(path));
    }

    @Override
    public Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }
}
