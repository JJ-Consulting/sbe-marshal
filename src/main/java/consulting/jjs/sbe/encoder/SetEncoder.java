package consulting.jjs.sbe.encoder;

import java.nio.ByteBuffer;

public class SetEncoder  {
  public static class U8 implements TypeEncoder {
    @Override
    public void encode(String value, ByteBuffer buffer) {
      short shortVal = Short.parseShort(value, 2);
      buffer.put((byte) shortVal);
    }

    @Override
    public void encodeNull(ByteBuffer buffer) {}

    @Override
    public void encodeMin(ByteBuffer buffer) {}

    @Override
    public void encodeMax(ByteBuffer buffer) {}
  }


  public static class U16 implements TypeEncoder {
    @Override
    public void encode(String value, ByteBuffer buffer) {
      int intValue = Integer.valueOf(value, 2);
      buffer.putShort((short) intValue);
    }

    @Override
    public void encodeNull(ByteBuffer buffer) {}

    @Override
    public void encodeMin(ByteBuffer buffer) {}

    @Override
    public void encodeMax(ByteBuffer buffer) {}
  }


  public static class U32 implements TypeEncoder {
    @Override
    public void encode(String value, ByteBuffer buffer) {
      buffer.putInt(Integer.parseUnsignedInt(value, 2));
    }

    @Override
    public void encodeNull(ByteBuffer buffer) {}

    @Override
    public void encodeMin(ByteBuffer buffer) {}

    @Override
    public void encodeMax(ByteBuffer buffer) {}
  }


  public static class U64 implements TypeEncoder {
    @Override
    public void encode(String value, ByteBuffer buffer) {
      buffer.putLong(Long.parseLong(value, 2));
    }

    @Override
    public void encodeNull(ByteBuffer buffer) {}

    @Override
    public void encodeMin(ByteBuffer buffer) {}

    @Override
    public void encodeMax(ByteBuffer buffer) {}
  }
}
