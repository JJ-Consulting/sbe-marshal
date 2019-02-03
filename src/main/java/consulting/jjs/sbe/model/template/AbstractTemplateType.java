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

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import consulting.jjs.sbe.encoder.TypeEncoder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Generic type that can be encoded such as primitive type, enum, set...
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractTemplateType implements TypeEncoder {

  @JacksonXmlProperty(isAttribute = true)
  private String            name;

  public AbstractTemplateType(String name) {
    this.name = name;
  }

  abstract public AbstractTemplateType duplicate();

}
