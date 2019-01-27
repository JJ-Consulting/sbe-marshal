package consulting.jjs.sbe.encoder;

import java.nio.ByteBuffer;

public class ShortEncoder {

  public static class Signed implements TypeEncoder {
    @Override
    public void encode(String value, ByteBuffer buffer) {
      buffer.putShort(Short.valueOf(value));
    }

    @Override
    public void encodeNull(ByteBuffer buffer) {
      buffer.putShort(Short.MIN_VALUE);
    }

    @Override
    public void encodeMin(ByteBuffer buffer) {
      buffer.putShort((short)(Short.MIN_VALUE + 1));  // precomputed at compile time
    }

    @Override
    public void encodeMax(ByteBuffer buffer) {
      buffer.putShort(Short.MAX_VALUE);
    }
  }

  public static class Unsigned implements TypeEncoder {
    @Override
    public void encode(String value, ByteBuffer buffer) {
      int intValue = Integer.valueOf(value);
      buffer.putShort((short) intValue);
    }

    @Override
    public void encodeNull(ByteBuffer buffer) {
      buffer.putShort((short)-1);
    }

    @Override
    public void encodeMin(ByteBuffer buffer) {
      buffer.putShort((short)0);
    }

    @Override
    public void encodeMax(ByteBuffer buffer) {
      buffer.putShort((short)-2);
    }
  }

}
