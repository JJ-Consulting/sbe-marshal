package consulting.jjs.sbe.encoder;

import lombok.Setter;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ByteEncoder {

  public static class Signed implements TypeEncoder {

    @Override
    public void encode(String value, ByteBuffer buffer) {
      buffer.put(Byte.valueOf(value));
    }

    @Override
    public void encodeNull(ByteBuffer buffer) {
      buffer.put(Byte.MIN_VALUE);
    }

    @Override
    public void encodeMin(ByteBuffer buffer) {
      buffer.put((byte) (Byte.MIN_VALUE + 1)); // precomputed at compile time
    }

    @Override
    public void encodeMax(ByteBuffer buffer) {
      buffer.put(Byte.MAX_VALUE);
    }
  }

  public static class Unsigned implements TypeEncoder {

    // UINT8 may be used to write string as byte array
    @Setter
    private Charset charset;

    @Override
    public void encode(String value, ByteBuffer buffer) {
      if (charset != null) { // SBE varStringEncoding type
        buffer.put(value.getBytes(charset));
      } else {
        short shortValue = Short.valueOf(value);
        buffer.put((byte) shortValue);
      }
    }

    @Override
    public void encodeNull(ByteBuffer buffer) {
      buffer.put((byte) -1);
    }

    @Override
    public void encodeMin(ByteBuffer buffer) {
      buffer.put((byte) 0);
    }

    @Override
    public void encodeMax(ByteBuffer buffer) {
      buffer.put((byte) -2);
    }
  }

}
