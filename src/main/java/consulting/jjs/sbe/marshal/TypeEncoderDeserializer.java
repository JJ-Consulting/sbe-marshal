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
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import consulting.jjs.sbe.encoder.ByteEncoder;
import consulting.jjs.sbe.encoder.CharEncoder;
import consulting.jjs.sbe.encoder.DoubleEncoder;
import consulting.jjs.sbe.encoder.FloatEncoder;
import consulting.jjs.sbe.encoder.IntegerEncoder;
import consulting.jjs.sbe.encoder.LongEncoder;
import consulting.jjs.sbe.encoder.ShortEncoder;
import consulting.jjs.sbe.encoder.TypeEncoder;

import java.io.IOException;

public class TypeEncoderDeserializer extends JsonDeserializer {

  @Override
  public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    String textValue = jsonParser.getText();
    TypeEncoder typeEncoder = typeEncoderFactory(textValue);
    if (typeEncoder == null) {
      throw new IOException("Unknown primitive type " + textValue);
    }
    return typeEncoder;
  }

  public static TypeEncoder typeEncoderFactory(String textValue) {
    switch (textValue) {
      case "char":
        return new CharEncoder();
      case "int8":
        return new ByteEncoder.Signed();
      case "int16":
        return new ShortEncoder.Signed();
      case "int32":
        return new IntegerEncoder.Signed();
      case "int64":
        return new LongEncoder.Signed();
      case "uint8":
        return new ByteEncoder.Unsigned();
      case "uint16":
        return new ShortEncoder.Unsigned();
      case "uint32":
        return new IntegerEncoder.Unsigned();
      case "uint64":
        return new LongEncoder.Unsigned();
      case "float":
        return new FloatEncoder();
      case "double":
        return new DoubleEncoder();
      default:
        return null;
    }
  }

}
