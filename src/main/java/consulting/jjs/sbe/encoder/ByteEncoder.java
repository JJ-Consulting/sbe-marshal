package consulting.jjs.sbe.encoder;

import java.nio.ByteBuffer;

public class ByteEncoder {

  public static class Signed implements TypeEncoder{
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
      buffer.put((byte)(Byte.MIN_VALUE + 1)); // precomputed at compile time
    }

    @Override
    public void encodeMax(ByteBuffer buffer) {
      buffer.put(Byte.MAX_VALUE);
    }
  }

  public static class Unsigned implements TypeEncoder{
    @Override
    public void encode(String value, ByteBuffer buffer) {
      int intValue = Integer.valueOf(value);
      buffer.put((byte) intValue);
    }

    @Override
    public void encodeNull(ByteBuffer buffer) {
      buffer.put((byte)-1);
    }

    @Override
    public void encodeMin(ByteBuffer buffer) {
      buffer.put((byte)0);
    }

    @Override
    public void encodeMax(ByteBuffer buffer) {
      buffer.put((byte)-2);
    }
  }

}
