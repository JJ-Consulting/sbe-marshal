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
 * Corresponds to the {@code int8} and {@code uint8} type of SBE
 */
public abstract class ByteEncoder implements TypeEncoder {

  /**
   * {@inheritDoc}
   */
  public Integer getSize() {return 1;}

  /**
   * Corresponds to the {@code int8} type of SBE
   */
  public static class Signed extends ByteEncoder {

    /**
     * {@inheritDoc}
     */
    @Override
    public void encode(String value, ByteBuffer buffer) {
      buffer.put(Byte.valueOf(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeNull(ByteBuffer buffer) {
      buffer.put(Byte.MIN_VALUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMin(ByteBuffer buffer) {
      buffer.put((byte) (Byte.MIN_VALUE + 1)); // precomputed at compile time
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMax(ByteBuffer buffer) {
      buffer.put(Byte.MAX_VALUE);
    }
  }

  /**
   * Corresponds to the {@code uint8} type of SBE
   */
  public static class Unsigned extends ByteEncoder {

    // UINT8 may be used to write string as byte array
    @Setter
    private Charset charset;

    /**
     * {@inheritDoc}
     */
    @Override
    public void encode(String value, ByteBuffer buffer) {
      if (charset != null) { // SBE varStringEncoding type
        buffer.put(value.getBytes(charset));
      } else {
        short shortValue = Short.valueOf(value);
        buffer.put((byte) shortValue);
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeNull(ByteBuffer buffer) {
      buffer.put((byte) -1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMin(ByteBuffer buffer) {
      buffer.put((byte) 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMax(ByteBuffer buffer) {
      buffer.put((byte) -2);
    }
  }

}
