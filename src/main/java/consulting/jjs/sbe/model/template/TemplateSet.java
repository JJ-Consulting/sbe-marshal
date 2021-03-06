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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import consulting.jjs.sbe.encoder.TypeEncoder;
import consulting.jjs.sbe.marshal.SetEncoderDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class TemplateSet extends AbstractTemplateType {

  @JacksonXmlProperty(isAttribute = true)
  @JsonDeserialize(using = SetEncoderDeserializer.class)
  private TypeEncoder          encodingType;
  @Setter
  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("choice")
  private List<TemplateChoice> choices = new ArrayList<>();

  public TemplateSet(TemplateSet toCopy) {
    super(toCopy.getName());
    this.encodingType = toCopy.encodingType;
    this.choices = toCopy.choices;
  }

  @Override
  public AbstractTemplateType duplicate() {
    return new TemplateSet(this);
  }

  @Override
  public void encode(String value, ByteBuffer buffer) {
    encodingType.encode(value, buffer);
  }

  @Override
  public void encodeNull(ByteBuffer buffer) {

  }

  @Override
  public void encodeMin(ByteBuffer buffer) {

  }

  @Override
  public void encodeMax(ByteBuffer buffer) {

  }

  @Override
  public Integer getSize() {
    return encodingType.getSize();
  }
}
