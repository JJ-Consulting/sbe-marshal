package consulting.jjs.sbe.encoder;

import java.nio.ByteBuffer;

public class LongEncoder {

  public static class Signed implements TypeEncoder {
    @Override
    public void encode(String value, ByteBuffer buffer) {
      buffer.putLong(Long.valueOf(value));
    }

    @Override
    public void encodeNull(ByteBuffer buffer) {
      buffer.putLong(Long.MIN_VALUE);
    }

    @Override
    public void encodeMin(ByteBuffer buffer) {
      buffer.putLong(Long.MIN_VALUE + 1); // precomputed at compile time
    }

    @Override
    public void encodeMax(ByteBuffer buffer) {
      buffer.putLong(Long.MAX_VALUE);
    }
  }

  public static class Unsigned implements TypeEncoder {
    @Override
    public void encode(String value, ByteBuffer buffer) {
      buffer.putLong(Long.parseUnsignedLong(value));
    }

    @Override
    public void encodeNull(ByteBuffer buffer) {
      buffer.putLong(-1L);
    }

    @Override
    public void encodeMin(ByteBuffer buffer) {
      buffer.putLong(0L);
    }

    @Override
    public void encodeMax(ByteBuffer buffer) {
      buffer.putLong(-2L);
    }
  }

}
