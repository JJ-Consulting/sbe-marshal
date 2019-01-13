package consulting.jjs.sbe.encoder;

import java.nio.ByteBuffer;

public interface TypeEncoder {

  void encode(String value, ByteBuffer buffer);
  void encodeNull(ByteBuffer buffer);
  void encodeMin(ByteBuffer buffer);
  void encodeMax(ByteBuffer buffer);

}
