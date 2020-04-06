package context;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testHelpers.*;

import static beaninfo.Scope.PROTOTYPE;
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

        Square square = new Square(5).setName("SQUARE");
        Polyhedron triangle = new Polyhedron(new int[]{1, 2, 3});
        Polyhedron hexagon = new Polyhedron(new int[]{6, 7, 8, 9, 10, 11});

        Screen expected = new Screen(square, circle);
        expected.setPolyhedrons(new Polyhedron[]{triangle, hexagon});

        assertEquals(expected, actual);

        Triangle strangeTriangle = (Triangle) beanFactory.getBean("strangeTriangle");
        Triangle expectedStrangeTriangle = new Triangle().setName("Cool figure!").setScope(PROTOTYPE);
        assertEquals(expectedStrangeTriangle, strangeTriangle);
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