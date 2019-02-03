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

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateComposite {

  @JacksonXmlProperty(isAttribute = true)
  private String name;
  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("type")
  private List<TemplateType> types = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("enum")
  private List<TemplateEnum> enums = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("ref")
  private List<TemplateTypeReference> references = new ArrayList<>();

}
