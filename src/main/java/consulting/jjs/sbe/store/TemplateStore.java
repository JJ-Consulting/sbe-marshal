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

package consulting.jjs.sbe.store;

import consulting.jjs.sbe.marshal.Marshaller;
import consulting.jjs.sbe.model.template.DeserializedTemplate;
import consulting.jjs.sbe.model.template.MessagesSchema;
import consulting.jjs.sbe.model.template.TemplateMessage;
import lombok.Getter;

import java.io.InputStream;
import java.nio.ByteOrder;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplateStore {

  private Map<String, MessageStore> messagesStore;
  @Getter
  private ByteOrder                 byteOrder;
  @Getter
  private Integer                   schemaId;
  @Getter
  private Integer                   version;

  public TemplateStore(String xmlMessageSchema) {
    initStores(new Marshaller().unmarshal(xmlMessageSchema));
  }

  public TemplateStore(InputStream xmlMessageSchema) {
    initStores(new Marshaller().unmarshal(xmlMessageSchema));
  }

  private void initStores(DeserializedTemplate template) {
    MessagesSchema messagesSchema = template.getMessagesSchema();
    schemaId = messagesSchema.getId();
    version = messagesSchema.getVersion();
    byteOrder = messagesSchema.getByteOrder();
    messagesStore = messagesSchema.getMessages().stream()
            .collect(Collectors.toMap(TemplateMessage::getName,
                    message -> new MessageStore(message, template.getDeclaredTypes(), template.getDeclaredComposedTypes())));
  }

  public MessageStore getMessage(String name) {
    return messagesStore.get(name);
  }

}
