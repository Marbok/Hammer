package context;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testHelpers.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static beaninfo.Scope.PROTOTYPE;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

// TODO refactor - mock JsonContext or use it like "system test"
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
        Circle innerCircle = new Circle(5, false);
        circle.setInnerCircle(innerCircle);

        Square square = new Square(5).setName("SQUARE");
        Polyhedron triangle = new Polyhedron(new int[]{1, 2, 3});
        Polyhedron hexagon = new Polyhedron(new int[]{6, 7, 8, 9, 10, 11});

        Screen expected = new Screen(square, circle);
        expected.setPolyhedrons(new Polyhedron[]{triangle, hexagon});

        assertEquals(expected, actual);

        Triangle strangeTriangle = (Triangle) beanFactory.getBean("strangeTriangle");
        Triangle expectedStrangeTriangle = new Triangle().setName("Cool figure!").setScope(PROTOTYPE);
        assertEquals(expectedStrangeTriangle, strangeTriangle);

        Lake actualLake = (Lake) beanFactory.getBean("lake");
        Map<String, Integer> mapForFish = new HashMap<>();
        mapForFish.put("one", 1);
        mapForFish.put("two", 2);
        Fish map = new Fish().setMap(mapForFish);
        Set<Polyhedron> setRefsForFish = new HashSet<>();
        setRefsForFish.add(triangle);
        setRefsForFish.add(hexagon);
        Fish refsSet = new Fish().setRefsSet(setRefsForFish);
        Set<Character> setCharsForFish = new HashSet<>();
        setCharsForFish.add('c');
        setCharsForFish.add('h');
        setCharsForFish.add('a');
        setCharsForFish.add('r');
        Fish charSet = new Fish().setCharsSet(setCharsForFish);
        Lake expectedLake = new Lake(asList(1, 2, 3), asList(map, refsSet, charSet));
        assertEquals(expectedLake, actualLake);
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