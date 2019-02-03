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

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * SBE primitive type appending the given value, null value, minimum value or maximum value to the given
 * {@link ByteBuffer}
 */
public interface TypeEncoder extends BytesMeasurable {

  /**
   * Parses the given string value to the type of this encoder and writes the value to the {@link ByteBuffer}.
   *
   * @param value  the value to parse
   * @param buffer the buffer to append the value
   */
  void encode(String value, ByteBuffer buffer);

  /**
   * Writes default null encoded value for this type.
   *
   * @param buffer the buffer to append the value
   */
  void encodeNull(ByteBuffer buffer);

  /**
   * Writes default minimum encoded value for this type.
   *
   * @param buffer
   */
  void encodeMin(ByteBuffer buffer);

  /**
   * Writes default maximum encoded value for this type.
   *
   * @param buffer
   */
  void encodeMax(ByteBuffer buffer);

  /**
   * Gets the size of this type in bytes.
   *
   * @return the size in bytes
   */
  @Override
  Integer getSize();

  default void setCharset(Charset charset) {
    throw new UnsupportedOperationException();
  }

}
