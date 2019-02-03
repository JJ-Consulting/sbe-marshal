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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import consulting.jjs.sbe.encoder.BytesMeasurable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TemplateField implements BytesMeasurable {

  public TemplateField(String name, String typeName) {
    this.name = name;
    this.typeName = typeName;
  }

  @JacksonXmlProperty(isAttribute = true)
  private Integer              id;
  @JacksonXmlProperty(isAttribute = true)
  private String               name;
  @JsonProperty("type")
  private String               typeName;
  private TemplatePresence     presence = TemplatePresence.REQUIRED;
  private String               valueRef;
  @JsonIgnore
  private AbstractTemplateType type;

  public TemplateField setGroupName(String groupName) {
    name = groupName + "." + name;
    return this;
  }

  /**
   * Gets the size of this type in bytes.
   *
   * @return the size in bytes
   */
  @Override
  public Integer getSize() {
    // Type is null if field represents a composite. A constant field is not encoded -> size is 0
    if (type == null || presence == TemplatePresence.CONSTANT) {
      return 0;
    }

    return type.getSize();
  }
}
