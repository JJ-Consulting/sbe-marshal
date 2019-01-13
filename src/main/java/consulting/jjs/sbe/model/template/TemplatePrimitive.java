package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum TemplatePrimitive {

  @JsonProperty("char")
  CHAR(1, new byte[]{(byte)0xff},  // null
          new byte[]{0x0},         // min
          new byte[]{(byte)0xfe}), // max

  @JsonProperty("int8")
  INT_8(1, new byte[]{(byte)0x80}, // null
          new byte[]{(byte)0x81},  // min
          new byte[]{0x7f}),       // max

  @JsonProperty("int16")
  INT_16(2, new byte[]{(byte)0x80, 0x0}, // null
          new byte[]{(byte)0x80, 0x1},   // min
          new byte[]{0x7f, (byte)0xff}), // max

  @JsonProperty("int32")
  INT_32(4, new byte[]{(byte)0x80, 0x0, 0x0, 0x0},               // null
          new byte[]{(byte)0x80, 0x0, 0x0, 0x1},                 // min
          new byte[]{0x7f, (byte)0xff, (byte)0xff, (byte)0xff}), // max

  @JsonProperty("int64")
  INT_64(8, new byte[]{(byte)0x80, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0},                                                 // null
          new byte[]{(byte)0x80, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x1},                                                   // min
          new byte[]{(byte)0x7f, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff}), // max

  @JsonProperty("uint8")
  UINT_8(1, new byte[]{(byte)0xff}, // null
          new byte[]{0x0},          // min
          new byte[]{(byte)0xfe}),  // max

  @JsonProperty("uint16")
  UINT_16(2, new byte[]{(byte)0xff, (byte)0xff}, // null
          new byte[]{0x0, 0x0},                  // min
          new byte[]{(byte)0xff, (byte)0xfe}),   // max

  @JsonProperty("uint32")
  UINT_32(4, new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff}, // null
          new byte[]{0x0, 0x0, 0x0, 0x0},                                // min
          new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xfe}),   // max

  @JsonProperty("uint64")
  UINT_64(8, new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff}, // null
          new byte[]{0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0},                                                            // min
          new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xfe}),   // max

  @JsonProperty("float")
  FLOAT(4, new byte[]{0x7f, (byte)0xc0, 0x0, 0x0},         // null
          new byte[]{0x0, 0x0, 0x0, 0x1},                  // min
          new byte[]{0x7f, 0x7f, (byte)0xff, (byte)0xff}), // max

  @JsonProperty("double")
  DOUBLE(8, new byte[]{0x7f, (byte)0xf8, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0},                                          // null
          new byte[]{0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x1},                                                    // min
          new byte[]{0x7f, (byte)0xef, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff}); // max

  private final int nbOfByte;
  private final byte[] encodedNullValue;
  private final byte[] encodedMinValue;
  private final byte[] encodedMaxValue;

  TemplatePrimitive(int nbOfBytes, byte[] encodedNullValue, byte[] encodedMinValue, byte[] encodedMaxValue) {
    this.nbOfByte = nbOfBytes;
    this.encodedNullValue = encodedNullValue;
    this.encodedMinValue = encodedMinValue;
    this.encodedMaxValue = encodedMaxValue;
  }

}
