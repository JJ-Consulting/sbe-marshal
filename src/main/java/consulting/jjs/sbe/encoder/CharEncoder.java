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

package consulting.jjs.sbe.encoder;

import lombok.Setter;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Corresponds to the {@code char} type of SBE
 */
public class CharEncoder implements TypeEncoder {

  @Setter
  private Charset charset = Charset.defaultCharset();

  /**
   * {@inheritDoc}
   */
  @Override
  public void encode(String value, ByteBuffer buffer) {
    buffer.put(value.getBytes(charset)[0]);
  }

  @Override
  public void encodeNull(ByteBuffer buffer) {
    // TODO
  }

  @Override
  public void encodeMin(ByteBuffer buffer) {
    // TODO
  }

  @Override
  public void encodeMax(ByteBuffer buffer) {
    // TODO
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getSize() {
    return 1;
  }
}
