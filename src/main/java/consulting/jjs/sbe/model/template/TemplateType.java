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

package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import consulting.jjs.sbe.encoder.TypeEncoder;
import consulting.jjs.sbe.marshal.CharsetDeserializer;
import consulting.jjs.sbe.marshal.TypeEncoderDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class TemplateType extends AbstractTemplateType {

  @JacksonXmlProperty(isAttribute = true)
  @JsonDeserialize(using = TypeEncoderDeserializer.class)
  private TypeEncoder      primitiveType;
  @Setter
  @JacksonXmlProperty(isAttribute = true)
  private Integer          length;
  @JacksonXmlProperty(isAttribute = true)
  @JsonDeserialize(using = CharsetDeserializer.class)
  private Charset          characterEncoding;
  @JacksonXmlProperty(isAttribute = true)
  @Setter
  private String           nullValue;
  @JacksonXmlProperty(isAttribute = true)
  @Setter
  private String           minValue;
  @JacksonXmlProperty(isAttribute = true)
  @Setter
  private String           maxValue;
  private TemplatePresence presence = TemplatePresence.REQUIRED;

  public TemplateType(TemplateType toCopy) {
    super(toCopy.getName());
    this.primitiveType = toCopy.primitiveType;
    this.length = toCopy.length;
    this.characterEncoding = toCopy.characterEncoding;
    this.nullValue = toCopy.nullValue;
    this.minValue = toCopy.minValue;
    this.maxValue = toCopy.maxValue;
    this.presence = toCopy.presence;
  }

  @Override
  public AbstractTemplateType duplicate() {
    return new TemplateType(this);
  }

  public void setPrimitiveType(TypeEncoder primitiveType) {
    this.primitiveType = primitiveType;
    if (characterEncoding != null) {
      this.primitiveType.setCharset(characterEncoding);
    }
  }

  public void setCharacterEncoding(Charset characterEncoding) {
    this.characterEncoding = characterEncoding;
    if (primitiveType != null) {
      primitiveType.setCharset(characterEncoding);
    }
  }

  @Override
  public void encode(String value, ByteBuffer buffer) {
    primitiveType.encode(value, buffer);
  }

  @Override
  public void encodeNull(ByteBuffer buffer) {
    if (nullValue == null) {
      primitiveType.encodeNull(buffer);
    } else {
      primitiveType.encode(nullValue, buffer);
    }
  }

  @Override
  public void encodeMin(ByteBuffer buffer) {
    if (minValue == null) {
      primitiveType.encodeMin(buffer);
    } else {
      primitiveType.encode(minValue, buffer);
    }
  }

  @Override
  public void encodeMax(ByteBuffer buffer) {
    if (maxValue == null) {
      primitiveType.encodeMax(buffer);
    } else {
      primitiveType.encode(maxValue, buffer);
    }
  }

  @Override
  public Integer getSize() {
    // a constant type is not encoded -> size 0
    if (presence == TemplatePresence.CONSTANT) {
      return 0;
    }
    if (length != null) { // array
      return length * primitiveType.getSize();
    }

    return primitiveType.getSize();
  }

  public static TemplateTypeBuilder builder() {
    return new TemplateTypeBuilder();
  }

  public static class TemplateTypeBuilder {
    private String      name;
    private TypeEncoder primitiveType;

    public TemplateTypeBuilder name(String name) {
      this.name = name;
      return this;
    }

    public TemplateTypeBuilder primitiveType(TypeEncoder primitiveType) {
      this.primitiveType = primitiveType;
      return this;
    }

    public TemplateType build() {
      TemplateType result = new TemplateType();
      result.setName(name);
      result.setPrimitiveType(primitiveType);
      return result;
    }
  }

}
