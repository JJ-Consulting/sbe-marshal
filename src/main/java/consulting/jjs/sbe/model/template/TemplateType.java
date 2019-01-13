package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import consulting.jjs.sbe.marshal.CharsetDeserializer;
import consulting.jjs.sbe.marshal.PostDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.charset.Charset;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateType implements PostDeserialize {

  @JacksonXmlProperty(isAttribute = true)
  private String            name;
  @JacksonXmlProperty(isAttribute = true)
  private TemplatePrimitive primitiveType;
  @JacksonXmlProperty(isAttribute = true)
  private Integer           length;
  @JacksonXmlProperty(isAttribute = true)
  @JsonDeserialize(using = CharsetDeserializer.class)
  private Charset           characterEncoding;

  private String nullValue;
  private String minValue;
  private String maxValue;

  private byte[] encodedNullValue;
  private byte[] encodedMinValue;
  private byte[] encodedMaxValue;


  @Override
  public void afterUnmarshalling() {
    if (nullValue == null) {
      encodedNullValue = primitiveType.getEncodedNullValue();
    } else {
      encodedNullValue = parseInput(nullValue);
    }

    if (minValue == null) {
      encodedMinValue = primitiveType.getEncodedMinValue();
    } else {
      encodedMinValue = parseInput(minValue);
    }

    if (maxValue == null) {
      encodedMaxValue = primitiveType.getEncodedMaxValue();
    } else {
      encodedMaxValue = parseInput(maxValue);
    }
  }

  private byte[] parseInput(String input) {
    switch (primitiveType) {
      case CHAR:
        if (input.length() != 1) {
          throw new IllegalArgumentException("input must be one char");
        }
        return input.getBytes(characterEncoding);
      case INT_8:
        return new byte[]{Byte.valueOf(input)};
      case INT_16:
        short valueShort = Short.valueOf(input);
        return new byte[]{(byte) (valueShort >> 8), (byte) valueShort};
      case INT_32:
        int valueInt = Integer.valueOf(input);
        return new byte[] {(byte) (valueInt >> 24), (byte) (valueInt >> 16), (byte) (valueInt >> 8), (byte) valueInt};
      case INT_64:
        long valueLong = Long.parseLong(input);
        return new byte[]{
                (byte) valueLong,
                (byte) (valueLong >> 8),
                (byte) (valueLong >> 16),
                (byte) (valueLong >> 24),
                (byte) (valueLong >> 32),
                (byte) (valueLong >> 40),
                (byte) (valueLong >> 48),
                (byte) (valueLong >> 56)};
      case FLOAT:
        int intBits = Float.floatToIntBits(Float.parseFloat(input));
        return new byte[] {(byte) (intBits >> 24), (byte) (intBits >> 16), (byte) (intBits >> 8), (byte) intBits};
      case DOUBLE:
        long longBits = Double.doubleToLongBits(Double.valueOf(input));
        return new byte[]{
                (byte) longBits,
                (byte) (longBits >> 8),
                (byte) (longBits >> 16),
                (byte) (longBits >> 24),
                (byte) (longBits >> 32),
                (byte) (longBits >> 40),
                (byte) (longBits >> 48),
                (byte) (longBits >> 56)};
      case UINT_8:

      case UINT_16:
        int intUbyte = Integer.valueOf(input);
        return new byte[] {(byte) (intUbyte >> 24), (byte) (intUbyte >> 16), (byte) (intUbyte >> 8), (byte) intUbyte};
    }
    return null;
  }

}
