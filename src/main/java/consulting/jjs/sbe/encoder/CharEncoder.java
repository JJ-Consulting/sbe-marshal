package consulting.jjs.sbe.encoder;

import java.nio.ByteBuffer;

public class CharEncoder implements TypeEncoder {
  @Override
  public void encode(String value, ByteBuffer buffer) {
    buffer.putChar(value.charAt(0));
  }

  @Override
  public void encodeNull(ByteBuffer buffer) {
    // TODO
  }

  @Override
  public void encodeMin(ByteBuffer buffer) {
    // TODO
  }

  @Override
  public void encodeMax(ByteBuffer buffer) {
    // TODO
  }
}
