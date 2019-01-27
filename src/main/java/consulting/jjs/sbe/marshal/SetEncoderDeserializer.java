package consulting.jjs.sbe.marshal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import consulting.jjs.sbe.encoder.SetEncoder;

import java.io.IOException;

public class SetEncoderDeserializer extends JsonDeserializer {
  @Override
  public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
    String textValue = jsonParser.getText();
    switch (textValue) {
      case "uint8":
        return new SetEncoder.U8();
      case "uint16":
        return new SetEncoder.U16();
      case "uint32":
        return new SetEncoder.U32();
      case "uint64":
        return new SetEncoder.U64();
      default:
        throw new IOException("Unknown set encoding type " + textValue);
    }
  }
}
