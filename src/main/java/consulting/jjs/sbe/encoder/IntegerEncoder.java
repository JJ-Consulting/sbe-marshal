package consulting.jjs.sbe.encoder;

import java.nio.ByteBuffer;

public class IntegerEncoder {

  public static class Signed implements TypeEncoder {
    @Override
    public void encode(String value, ByteBuffer buffer) {
      buffer.putInt(Integer.valueOf(value));
    }

    @Override
    public void encodeNull(ByteBuffer buffer) {
      buffer.putInt(Integer.MIN_VALUE);
    }

    @Override
    public void encodeMin(ByteBuffer buffer) {
      buffer.putInt(Integer.MIN_VALUE + 1); // precomputed at compile time
    }

    @Override
    public void encodeMax(ByteBuffer buffer) {
      buffer.putInt(Integer.MAX_VALUE);
    }
  }

  public static class Unsigned implements TypeEncoder {
    @Override
    public void encode(String value, ByteBuffer buffer) {
      buffer.putInt(Integer.parseUnsignedInt(value));
    }

    @Override
    public void encodeNull(ByteBuffer buffer) {
      buffer.putInt(-1);
    }

    @Override
    public void encodeMin(ByteBuffer buffer) {
      buffer.putInt(0);
    }

    @Override
    public void encodeMax(ByteBuffer buffer) {
      buffer.putInt(-2);
    }
  }

}
