package consulting.jjs.sbe.encoder;

import java.nio.ByteBuffer;

public class FloatEncoder implements TypeEncoder {

  @Override
  public void encode(String value, ByteBuffer buffer) {
    buffer.putFloat(Float.valueOf(value));
  }

  @Override
  public void encodeNull(ByteBuffer buffer) {
    buffer.putFloat(Float.NaN);
  }

  @Override
  public void encodeMin(ByteBuffer buffer) {
    buffer.putFloat(Float.MIN_VALUE);
  }

  @Override
  public void encodeMax(ByteBuffer buffer) {
    buffer.putFloat(Float.MAX_VALUE);
  }

}
