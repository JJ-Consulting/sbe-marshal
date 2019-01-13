package consulting.jjs.sbe.marshal;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.nio.ByteOrder;

public class ByteOrderDeserializer extends JsonDeserializer<ByteOrder> {

  private static final String SBE_LITTLE_ENDIAN_STR        = "littleEndian";
  private static final String SBE_BIG_ENDIAN_STR           = "bigEndian";
  private static final String UNKNOWN_BYTE_ORDER_ERROR_MSG = "Unknown byte order: %s. Valid values are: %s, %s";

  @Override
  public ByteOrder deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
    String byteOrder = jsonParser.getText();
    switch (byteOrder) {
      case SBE_BIG_ENDIAN_STR:
        return ByteOrder.BIG_ENDIAN;
      case SBE_LITTLE_ENDIAN_STR:
        return ByteOrder.LITTLE_ENDIAN;
      default:
        throw new JsonParseException(jsonParser,
                String.format(UNKNOWN_BYTE_ORDER_ERROR_MSG, byteOrder, SBE_LITTLE_ENDIAN_STR, SBE_BIG_ENDIAN_STR));
    }
  }

}
