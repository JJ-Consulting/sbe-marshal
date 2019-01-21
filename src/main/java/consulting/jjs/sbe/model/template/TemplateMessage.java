package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateMessage implements Supplier<Stream<? extends TemplateField>> {

  @JacksonXmlProperty(isAttribute = true)
  private String              name;
  @JacksonXmlProperty(isAttribute = true)
  private Integer             id;
  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("field")
  private List<TemplateField> fields = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("group")
  private List<TemplateGroup> groups = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("data")
  private List<TemplateData>  datas  = new ArrayList<>();

  @Override
  public Stream<? extends TemplateField> get() {
    return Stream.concat(groups.stream().flatMap(TemplateGroup::get), fields.stream());
  }
}
