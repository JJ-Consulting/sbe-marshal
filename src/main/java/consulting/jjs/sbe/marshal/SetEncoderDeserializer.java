/*
 * Copyright (c) 2019 J&J's Consulting.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
