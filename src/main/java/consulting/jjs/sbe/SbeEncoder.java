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

package consulting.jjs.sbe;

import consulting.jjs.sbe.model.input.ComposedFieldValue;
import consulting.jjs.sbe.model.input.FieldValue;
import consulting.jjs.sbe.model.input.Group;
import consulting.jjs.sbe.model.input.Message;
import consulting.jjs.sbe.model.input.SimpleFieldValue;
import consulting.jjs.sbe.store.MessageStore;
import consulting.jjs.sbe.store.TemplateStore;

import java.nio.ByteBuffer;
import java.util.function.BiConsumer;

public class SbeEncoder {

  private final Message       message;
  private final ByteBuffer    buffer;
  private final TemplateStore templateStore;
  private final MessageStore  store;

  public SbeEncoder(Message message, ByteBuffer buffer, TemplateStore templateStore) {
    this.message = message;
    this.buffer = buffer;
    this.templateStore = templateStore;
    this.store = templateStore.getMessage(message.getName());
  }

  public void encode() {
    encodeHeader();
    encodeFields();
    encodeGroups();
    encodeDatas();
  }

  private void encodeHeader() {
    ComposedFieldValue messageHeader = new ComposedFieldValue("messageHeader")
            .addFieldValue(new SimpleFieldValue("blockLength", store.getMessageSize()))
            .addFieldValue(new SimpleFieldValue("templateId", store.getMessageId()))
            .addFieldValue(new SimpleFieldValue("schemaId", templateStore.getSchemaId()))
            .addFieldValue(new SimpleFieldValue("version", templateStore.getVersion()));
    encodeField(messageHeader);
  }

  private void encodeFields() {
    message.getFields().forEach(this::encodeField);
  }

  private void encodeDatas() {
    message.getDatas().forEach(this::encodeField);
  }

  private void encodeGroups() {
    message.getGroups().forEach(this::encodeGroup);
  }

  private void encodeGroup(Group topLevelGroup) {
    BiConsumer<String, Integer> groupHeaderConsumer = (groupName, groupSize) -> {
      ComposedFieldValue groupHeader = new ComposedFieldValue("groupSizeEncoding")
              .addFieldValue(new SimpleFieldValue("blockLength", store.getGroupSize(groupName)))
              .addFieldValue(new SimpleFieldValue("numInGroup", groupSize));
      encodeField(groupHeader);
    };
    topLevelGroup.setGroupHeaderConsumer(groupHeaderConsumer);
    encodeField(topLevelGroup);
  }

  private void encodeField(FieldValue field) {
    field.consumeValue((fieldName, value) ->
            store.getTemplateFieldByName(fieldName)
                    .getType()
                    .encode(value, buffer)
    );
  }


}
