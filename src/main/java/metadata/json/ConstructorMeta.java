package metadata.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ConstructorMeta {
    @JsonProperty("name")
    private String nameAttr;

    @JsonProperty("value")
    private Object value;

    @JsonProperty("ref")
    private String ref;
}
