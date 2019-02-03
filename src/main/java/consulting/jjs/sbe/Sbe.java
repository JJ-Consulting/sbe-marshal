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

import consulting.jjs.sbe.model.input.Message;
import consulting.jjs.sbe.store.TemplateStore;

import java.io.InputStream;
import java.nio.ByteBuffer;

public class Sbe {

  private static final int DEFAULT_MEMORY_SIZE = 1024;

  private TemplateStore templateStore;

  public Sbe(String xmlMessageSchema) {
    templateStore = new TemplateStore(xmlMessageSchema);
  }

  public Sbe(InputStream xmlMessageSchema) {
    templateStore = new TemplateStore(xmlMessageSchema);
  }

  public ByteBuffer encode(Message message) {
    ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_MEMORY_SIZE);
    encode(message, buffer);
    return buffer;
  }

  public ByteBuffer encode(Message message, int memorySizeToAllocate) {
    ByteBuffer buffer = ByteBuffer.allocate(memorySizeToAllocate);
    encode(message, buffer);
    return buffer;
  }

  public ByteBuffer encodeMinMemoryAllocated(Message message) {
    ByteBuffer buffer = ByteBuffer.allocate(getMessageSize(message));
    encode(message, buffer);
    return buffer;
  }

  public void encode(Message message, ByteBuffer byteBuffer) {
    byteBuffer.order(templateStore.getByteOrder());
    new SbeEncoder(message, byteBuffer, templateStore).encode();
  }

  private int getMessageSize(Message message) {
    // TODO: get exact size from message
    return 1024;
  }

}
