package consulting.jjs.sbe.encoder;

import lombok.Setter;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class CharEncoder implements TypeEncoder {

  @Setter
  private Charset charset = Charset.defaultCharset();

  @Override
  public void encode(String value, ByteBuffer buffer) {
    buffer.put(value.getBytes(charset)[0]);
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
