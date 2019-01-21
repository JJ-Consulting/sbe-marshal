package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import consulting.jjs.sbe.encoder.ByteEncoder;
import consulting.jjs.sbe.encoder.CharEncoder;
import consulting.jjs.sbe.encoder.DoubleEncoder;
import consulting.jjs.sbe.encoder.FloatEncoder;
import consulting.jjs.sbe.encoder.IntegerEncoder;
import consulting.jjs.sbe.encoder.LongEncoder;
import consulting.jjs.sbe.encoder.ShortEncoder;
import consulting.jjs.sbe.encoder.TypeEncoder;
import lombok.Getter;

@Getter
public enum TemplatePrimitive {

  @JsonProperty("char")
  CHAR(new CharEncoder()),

  @JsonProperty("int8")
  INT_8(new ByteEncoder.Signed()),

  @JsonProperty("int16")
  INT_16(new ShortEncoder.Signed()),

  @JsonProperty("int32")
  INT_32(new IntegerEncoder.Signed()),

  @JsonProperty("int64")
  INT_64(new LongEncoder.Signed()),

  @JsonProperty("uint8")
  UINT_8(new ByteEncoder.Unsigned()),

  @JsonProperty("uint16")
  UINT_16(new ShortEncoder.Unsigned()),

  @JsonProperty("uint32")
  UINT_32(new IntegerEncoder.Unsigned()),

  @JsonProperty("uint64")
  UINT_64(new LongEncoder.Unsigned()),

  @JsonProperty("float")
  FLOAT(new FloatEncoder()),

  @JsonProperty("double")
  DOUBLE(new DoubleEncoder());

  private final TypeEncoder encoder;

  TemplatePrimitive(TypeEncoder encoder) {
    this.encoder = encoder;
  }

}
