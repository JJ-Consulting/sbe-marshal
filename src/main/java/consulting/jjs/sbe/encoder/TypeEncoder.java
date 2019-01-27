package consulting.jjs.sbe.encoder;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public interface TypeEncoder {

  void encode(String value, ByteBuffer buffer);
  void encodeNull(ByteBuffer buffer);
  void encodeMin(ByteBuffer buffer);
  void encodeMax(ByteBuffer buffer);
  default void setCharset(Charset charset) {
    throw new UnsupportedOperationException();
  }

}
