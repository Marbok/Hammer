package beaninfo;

import metadata.json.BeanMeta;
import metadata.json.InjectMeta;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BeanInfoTest {

    @Test
    void map() {
        var beanMeta = new BeanMeta()
                .setBeanName("string")
                .setClassName("java.lang.String")
                .setConstructor(Arrays.asList(
                        new InjectMeta().setName("one").setValue(5),
                        new InjectMeta().setName("two").setRef("builder")
                ))
                .setSetters(Arrays.asList(
                        new InjectMeta().setName("three").setRef("buffer"),
                        new InjectMeta().setName("four").setValue(6.5)
                ));

        var actual = BeanInfo.map(beanMeta);

        var constructor = new HashMap<String, InjectParam>();
        constructor.put("one", new InjectParam(5));
        constructor.put("two", new InjectParam("builder"));

        var setters = new HashMap<String, InjectParam>();
        setters.put("three", new InjectParam("buffer"));
        setters.put("four", new InjectParam(6.5));

        var expected = new BeanInfo()
                .setName("string")
                .setClazz(String.class)
                .setConstructorParam(constructor)
                .setSetterParam(setters);

        assertEquals(expected, actual);
    }
}