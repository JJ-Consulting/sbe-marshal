package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import consulting.jjs.sbe.encoder.TypeEncoder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractTemplateType implements TypeEncoder {

  @JacksonXmlProperty(isAttribute = true)
  private String            name;

  public AbstractTemplateType(String name) {
    this.name = name;
  }

  abstract public AbstractTemplateType duplicate();

}
