package metadata.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InjectMeta {
    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private Object value;

    @JsonProperty("ref")
    private String ref;
}
