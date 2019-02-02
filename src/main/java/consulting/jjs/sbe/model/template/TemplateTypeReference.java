package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TemplateTypeReference {

  @JacksonXmlProperty(isAttribute = true)
  private String name;
  @JacksonXmlProperty(isAttribute = true)
  private String type;
  @JsonIgnore
  private boolean referenceComposite = false;

}
