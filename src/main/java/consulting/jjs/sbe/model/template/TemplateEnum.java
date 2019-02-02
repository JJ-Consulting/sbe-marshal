package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import consulting.jjs.sbe.encoder.TypeEncoder;
import consulting.jjs.sbe.marshal.TypeEncoderDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
public class TemplateEnum extends AbstractTemplateType {

  @Setter
  @JacksonXmlProperty(isAttribute = true)
  @JsonDeserialize(using = TypeEncoderDeserializer.class)
  private TypeEncoder             encodingType;
  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("validValue")
  private List<TemplateEnumValue> values = new ArrayList<>();
  private Map<String, String>     valuesByName;

  public TemplateEnum(TemplateEnum toCopy) {
    super(toCopy.getName());
    this.encodingType = toCopy.encodingType;
    this.values = toCopy.values;
    this.valuesByName = toCopy.valuesByName;
  }

  @Override
  public AbstractTemplateType duplicate() {
    return new TemplateEnum(this);
  }

  public void setValues(List<TemplateEnumValue> values) {
    this.values = values;
    valuesByName = values.stream().collect(Collectors.toMap(TemplateEnumValue::getName, TemplateEnumValue::getValue));
  }

  @Override
  public void encode(String value, ByteBuffer buffer) {
    String enumValue = valuesByName.get(value);
    if (enumValue != null) {
      encodingType.encode(enumValue, buffer);
    }
  }

  @Override
  public void encodeNull(ByteBuffer buffer) {
    encodingType.encodeNull(buffer);
  }

  @Override
  public void encodeMin(ByteBuffer buffer) {
    throw new UnsupportedOperationException("encode minimum value is not supported for enum");
  }

  @Override
  public void encodeMax(ByteBuffer buffer) {
    throw new UnsupportedOperationException("encode maximum value is not supported for enum");
  }
}
