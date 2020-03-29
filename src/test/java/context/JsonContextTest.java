package context;

import beaninfo.BeanInfo;
import exceptions.BeanInfoException;
import exceptions.ContextException;
import metadata.json.InjectMeta;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonContextTest {

    @Test
    void getBeanInfo_doubleInitialize() {
        assertThrows(ContextException.class, () -> new JsonContext("src/test/resources/json_context_test/double/double.json"));
    }


    @Test
    void getBeanInfo() throws BeanInfoException {
        var jsonContext = new JsonContext("src/test/resources/json_context_test/imports/root.json");
        assertEquals(getBean("root"), jsonContext.getBeanInfo("root"));
        assertEquals(getBean("2level_1"), jsonContext.getBeanInfo("2level_1"));
        assertEquals(getBean("2level_2"), jsonContext.getBeanInfo("2level_2"));
        assertEquals(getBean("3level"), jsonContext.getBeanInfo("3level"));
    }

    private BeanInfo getBean(String name) throws BeanInfoException {
        return new BeanInfo()
                .setName(name)
                .setClazz(String.class)
                .setConstructorParam(Collections.singletonList(
                        new InjectMeta().setType("int").setValue("5")));
    }
}