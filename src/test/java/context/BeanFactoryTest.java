package context;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BeanFactoryTest {

    @Test
    void getBean() {
        BeanFactory beanFactory = new BeanFactory(new JsonContext("src/test/resources/bean_factory_test/root.json"));
        Integer root = (Integer) beanFactory.getBean("root");

        Integer actual = 5;
        assertEquals(actual, root);
    }
}