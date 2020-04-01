package context;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testHelpers.Circle;
import testHelpers.Screen;
import testHelpers.Square;

import static org.junit.jupiter.api.Assertions.*;

class BeanFactoryTest {

    BeanFactory beanFactory;

    @BeforeEach
    void setUp() {
        beanFactory = new BeanFactory(new JsonContext("src/test/resources/bean_factory_test/screen.json"));
    }

    @Test
    void getBean() {
        Screen actual = (Screen) beanFactory.getBean("screen");

        Circle circle = new Circle(20, true);
        circle.setInnerCircle(new Circle(5, false));
        Square square = new Square(5);
        Screen expected = new Screen(square, circle);

        assertEquals(expected, actual);
    }

    @Test
    void testScope() {
        Square square1 = (Square) beanFactory.getBean("square");
        Square square2 = (Square) beanFactory.getBean("square");
        assertSame(square1, square2);

        Circle Circle1 = (Circle) beanFactory.getBean("innerCircle");
        Circle Circle2 = (Circle) beanFactory.getBean("innerCircle");
        assertNotSame(Circle1, Circle2);
    }
}