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

@Getter
@Setter
public class GroupValue extends AbstractField {

  private List<FieldValue> fieldValues = new ArrayList<>();
  private List<FieldValue> dataValues  = new ArrayList<>();
  private List<Group>      innerGroups = new ArrayList<>();

  public GroupValue(String name) {
    super(name);
  }

  public GroupValue() {
    super(null);
  }

  @Override
  public void consumeValue(BiConsumer<String, String> nameValueConsumer) {
    BiConsumer<String, String> groupNameAppender = (fieldName, value) ->
            nameValueConsumer.accept(this.name + "." + fieldName, value);

    fieldValues.forEach(fieldValue -> fieldValue.consumeValue(groupNameAppender));
    // TODO: separate field and group
    dataValues.forEach(dataValue -> dataValue.consumeValue(groupNameAppender));
  }

  public GroupValue addFieldValue(FieldValue fieldValue) {
    fieldValues.add(fieldValue);
    return this;
  }

  public GroupValue addDataValue(FieldValue dataValue) {
    dataValues.add(dataValue);
    return this;
  }

  public GroupValue addGroup(Group group) {
    innerGroups.add(group);
    return this;
  }

}
