package context;

import beaninfo.BeanInfo;
import beaninfo.inject_param.InjectListReferences;
import beaninfo.inject_param.InjectListValues;
import beaninfo.inject_param.InjectValue;
import exceptions.ContextException;
import org.junit.jupiter.api.Test;
import testHelpers.Fish;
import testHelpers.Lake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonContextTest {

    @Test
    void getBeanInfo_doubleInitialize() {
        assertThrows(ContextException.class, () -> new JsonContext("src/test/resources/json_context_test/double/double.json"));
    }


    @Test
    void getBeanInfo() {
        JsonContext jsonContext = new JsonContext("src/test/resources/json_context_test/imports/root.json");
        assertEquals(getBean("root"), jsonContext.getBeanInfo("root"));
        assertEquals(getBean("2level_1"), jsonContext.getBeanInfo("2level_1"));
        assertEquals(getBean("2level_2"), jsonContext.getBeanInfo("2level_2"));
        assertEquals(getBean("3level"), jsonContext.getBeanInfo("3level"));
    }

    private BeanInfo getBean(String name) {
        return new BeanInfo()
                .setName(name)
                .setClazz(String.class)
                .setConstructorParams(Collections.singletonList(
                        new InjectValue(int.class, null, "5")))
                .setSetterParams(new ArrayList<>());
    }

    @Test
    void testContextForListMapSet() {
        JsonContext jsonContext = new JsonContext("src/test/resources/bean_factory_test/screen.json");
        BeanInfo actual = jsonContext.getBeanInfo("lake");

        BeanInfo expected = new BeanInfo()
                .setName("lake")
                .setClazz(Lake.class)
                .setConstructorParams(asList(new InjectListValues(List.class, null, int.class, new String[]{"1", "2", "3"}),
                        new InjectListReferences(List.class, null, Fish.class, new String[]{"map"})))
                .setSetterParams(new ArrayList<>());

        assertEquals(expected, actual);
    }
}