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

package consulting.jjs.sbe.model.input;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Group extends AbstractField {

  @Getter
  private List<GroupValue> groupValues = new ArrayList<>();

  @Setter
  private BiConsumer<String, Integer> groupHeaderConsumer;
  private BiConsumer<String, String>  nameValueConsumer;

  public Group(String name) {
    super(name);
  }

  public Group addGroupValue(GroupValue groupValue) {
    groupValues.add(groupValue);
    return this;
  }

  public void groupNameAppender(String fieldName, String value) {
    nameValueConsumer.accept(this.name + "." + fieldName, value);
  }

  public void groupNameAppender(String fieldName, Integer value) {
    groupHeaderConsumer.accept(this.name + "." + fieldName, value);
  }

  @Override
  public void consumeValue(BiConsumer<String, String> nameValueConsumer) {
    this.nameValueConsumer = nameValueConsumer;

    groupHeaderConsumer.accept(name, groupValues.size());

    groupValues.forEach(groupValue -> {
      groupValue.getFieldValues().forEach(f -> f.consumeValue(this::groupNameAppender));
      groupValue.getInnerGroups().forEach(g -> {
        g.setGroupHeaderConsumer(this::groupNameAppender);
        g.consumeValue(this::groupNameAppender);
      });
      groupValue.getDataValues().forEach(d -> d.consumeValue(this::groupNameAppender));
    });

  }

}
