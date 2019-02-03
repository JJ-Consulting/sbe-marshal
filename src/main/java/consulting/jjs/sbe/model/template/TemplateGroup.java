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
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import consulting.jjs.sbe.encoder.BytesMeasurable;
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
public class TemplateGroup implements Supplier<Stream<? extends TemplateField>>, BytesMeasurable {

  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("field")
  private List<TemplateField> fields = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("group")
  private List<TemplateGroup> groups = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("data")
  private List<TemplateField> datas = new ArrayList<>();
  private Integer id;
  private String name;
  private String dimensionType;

  public TemplateGroup setGroupName(String groupName) {
    name = groupName + "." + name;
    return this;
  }

  @Override
  public Stream<? extends TemplateField> get() {
    return Stream.concat(
            Stream.concat(groups.stream().map(g -> g.setGroupName(name)).flatMap(TemplateGroup::get),
                    fields.stream().map(f -> f.setGroupName(name))),
            datas.stream().map(d -> d.setGroupName(name)));
  }

  @Override
  public Integer getSize() {
    return fields.stream().map(TemplateField::getSize).reduce(0, (a, b) -> a + b);
  }
}
