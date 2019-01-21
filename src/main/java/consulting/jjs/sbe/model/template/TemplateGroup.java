package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
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
public class TemplateGroup implements Supplier<Stream<? extends TemplateField>> {

  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("field")
  private List<TemplateField> fields = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("group")
  private List<TemplateGroup> groups = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("data")
  private List<TemplateData> datas = new ArrayList<>();
  private Integer id;
  private String name;
  private String dimensionType;

  @Override
  public Stream<? extends TemplateField> get() {
    return Stream.concat(groups.stream().flatMap(TemplateGroup::get), fields.stream());
  }
}
