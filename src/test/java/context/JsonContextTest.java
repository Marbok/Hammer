package context;

import beaninfo.BeanInfo;
import metadata.json.InjectMeta;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonContextTest {

    @Test
    void getBeanInfo() {
        var jsonContext = new JsonContext("src/test/resources/json_context_test/imports/root.json");
        assertEquals(getBean("root"), jsonContext.getBeanInfo("root"));
        assertEquals(getBean("2level_1"), jsonContext.getBeanInfo("2level_1"));
        assertEquals(getBean("2level_2"), jsonContext.getBeanInfo("2level_2"));
        assertEquals(getBean("3level"), jsonContext.getBeanInfo("3level"));
    }

    private BeanInfo getBean(String name) {
        return new BeanInfo()
                .setName(name)
                .setClazz(String.class)
                .setConstructorParam(Collections.singletonList(
                        new InjectMeta().setName("volume").setValue(5)));
    }
}