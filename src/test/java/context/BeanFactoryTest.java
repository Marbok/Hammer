package context;

import org.junit.jupiter.api.Test;
import testHelpers.Circle;
import testHelpers.Screen;
import testHelpers.Square;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BeanFactoryTest {

    @Test
    void getBean() {
        BeanFactory beanFactory = new BeanFactory(new JsonContext("src/test/resources/bean_factory_test/screen.json"));
        Screen actual = (Screen) beanFactory.getBean("screen");

        Circle circle = new Circle(20, true);
        Square square = new Square(5);
        Screen expected = new Screen(square, circle);

        assertEquals(expected, actual);
    }
}