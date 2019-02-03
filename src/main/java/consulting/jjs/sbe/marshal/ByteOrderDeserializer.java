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
