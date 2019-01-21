package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TemplateField {

  @JacksonXmlProperty(isAttribute = true)
  private Integer          id;
  @JacksonXmlProperty(isAttribute = true)
  private String           name;
  @JsonProperty("type")
  private String           typeName;
  private TemplatePresence presence = TemplatePresence.REQUIRED;
  private String           valueRef;
  @JsonIgnore
  private TemplateType     type;

}
