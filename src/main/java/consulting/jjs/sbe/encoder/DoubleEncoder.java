package consulting.jjs.sbe.encoder;

import java.nio.ByteBuffer;

public class DoubleEncoder implements TypeEncoder {
  @Override
  public void encode(String value, ByteBuffer buffer) {
    buffer.putDouble(Double.valueOf(value));
  }

  @Override
  public void encodeNull(ByteBuffer buffer) {
    buffer.putDouble(Double.NaN);
  }

  @Override
  public void encodeMin(ByteBuffer buffer) {
    buffer.putDouble(Double.MIN_VALUE);
  }

  @Override
  public void encodeMax(ByteBuffer buffer) {
    buffer.putDouble(Double.MAX_VALUE);
  }
}
