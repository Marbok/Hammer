package metadata.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Class consists all information about bean
 */
@Data
public class BeanMeta {

    /**
     * Full class's name.
     * Example: "java.lang.Integer"
     */
    @JsonProperty("class")
    private String className;

    /**
     * Name of bean. Use it when referring to a specific bean.
     * This property is skipped for anonymous bean. For instance, {@link context.BeanPostProcessor}
     */
    @JsonProperty("name")
    private String beanName;

    /**
     * One of {@link beaninfo.Scope}.
     * Define how long bean live.
     */
    private String scope;

    /**
     * Parameters for constructor.
     * Important parameters order.
     * Skip it, if use a no-argument constructor.
     */
    private List<InjectMeta> constructor;

    /**
     * Parameters for setters.
     * Skip it, if don't use setters.
     */
    private List<InjectMeta> setters;
}
