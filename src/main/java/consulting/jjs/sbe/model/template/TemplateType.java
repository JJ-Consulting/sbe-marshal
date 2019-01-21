package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import consulting.jjs.sbe.encoder.TypeEncoder;
import consulting.jjs.sbe.marshal.CharsetDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateType implements TypeEncoder {

  @JacksonXmlProperty(isAttribute = true)
  private String            name;
  @JacksonXmlProperty(isAttribute = true)
  private TemplatePrimitive primitiveType;
  @JacksonXmlProperty(isAttribute = true)
  private Integer           length;
  @JacksonXmlProperty(isAttribute = true)
  @JsonDeserialize(using = CharsetDeserializer.class)
  private Charset           characterEncoding;
  @JacksonXmlProperty(isAttribute = true)
  private String            nullValue;
  @JacksonXmlProperty(isAttribute = true)
  private String            minValue;
  @JacksonXmlProperty(isAttribute = true)
  private String            maxValue;


  @Override
  public void encode(String value, ByteBuffer buffer) {
    primitiveType.getEncoder().encode(value, buffer);
  }

  @Override
  public void encodeNull(ByteBuffer buffer) {
    if (nullValue == null) {
      primitiveType.getEncoder().encodeNull(buffer);
    } else {
      primitiveType.getEncoder().encode(nullValue, buffer);
    }
  }

  @Override
  public void encodeMin(ByteBuffer buffer) {
    if (minValue == null) {
      primitiveType.getEncoder().encodeMin(buffer);
    } else {
      primitiveType.getEncoder().encode(minValue, buffer);
    }
  }

  @Override
  public void encodeMax(ByteBuffer buffer) {
    if (maxValue == null) {
      primitiveType.getEncoder().encodeMax(buffer);
    } else {
      primitiveType.getEncoder().encode(maxValue, buffer);
    }
  }
}
