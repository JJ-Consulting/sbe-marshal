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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateMessage implements Supplier<Stream<? extends TemplateField>> {

  private static final Stream<TemplateField> SBE_FIELDS = Stream.of(
          new TemplateField("messageHeader", "messageHeader"),
          new TemplateField("groupSizeEncoding", "groupSizeEncoding"),
          new TemplateField("varStringEncoding", "varStringEncoding"),
          new TemplateField("varDataEncoding", "varDataEncoding")
  );

  @JacksonXmlProperty(isAttribute = true)
  private String              name;
  @JacksonXmlProperty(isAttribute = true)
  private Integer             id;
  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("field")
  private List<TemplateField> fields = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("group")
  private List<TemplateGroup> groups = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("data")
  private List<TemplateField> datas  = new ArrayList<>();
  //private List<TemplateData>  datas  = new ArrayList<>();

  @Override
  public Stream<? extends TemplateField> get() {
    return Stream.concat(Stream.concat(groups.stream().flatMap(TemplateGroup::get), fields.stream()), datas.stream());
  }

  /**
   * Gets the size of this type in bytes.
   *
   * @return the size in bytes
   */
  public int getSize() {
    return fields.stream().map(TemplateField::getSize).reduce(0, (a, b) -> a + b);
  }
}
