package context;

import beaninfo.BeanInfo;
import beaninfo.InjectParamFactory;
import beaninfo.InjectValue;
import exceptions.ContextException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonContextTest {

    private InjectParamFactory injectParamFactory = new InjectParamFactory();

    @Test
    void getBeanInfo_doubleInitialize() {
        assertThrows(ContextException.class, () -> new JsonContext("src/test/resources/json_context_test/double/double.json"));
    }


    @Test
    void getBeanInfo() {
        var jsonContext = new JsonContext("src/test/resources/json_context_test/imports/root.json", injectParamFactory);
        assertEquals(getBean("root"), jsonContext.getBeanInfo("root"));
        assertEquals(getBean("2level_1"), jsonContext.getBeanInfo("2level_1"));
        assertEquals(getBean("2level_2"), jsonContext.getBeanInfo("2level_2"));
        assertEquals(getBean("3level"), jsonContext.getBeanInfo("3level"));
    }

    private BeanInfo getBean(String name) {
        return new BeanInfo(injectParamFactory)
                .setName(name)
                .setClazz(String.class)
                .setConstructorParams(Collections.singletonList(
                        new InjectValue(int.class, null, "5")))
                .setSetterParams(new ArrayList<>());
    }
}