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

/**
 * Corresponds to the {@code set} type of SBE
 */
public abstract class SetEncoder implements TypeEncoder {

  /**
   * Corresponds to the {@code uint8} {@code set} type of SBE
   */
  public static class U8 extends SetEncoder {

    /**
     * {@inheritDoc}
     */
    @Override
    public void encode(String value, ByteBuffer buffer) {
      short shortVal = Short.parseShort(value, 2);
      buffer.put((byte) shortVal);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeNull(ByteBuffer buffer) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMin(ByteBuffer buffer) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMax(ByteBuffer buffer) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getSize() {
      return 1;
    }
  }

  /**
   * Corresponds to the {@code uint16} {@code set} type of SBE
   */
  public static class U16 extends SetEncoder {

    /**
     * {@inheritDoc}
     */
    @Override
    public void encode(String value, ByteBuffer buffer) {
      int intValue = Integer.valueOf(value, 2);
      buffer.putShort((short) intValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeNull(ByteBuffer buffer) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMin(ByteBuffer buffer) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMax(ByteBuffer buffer) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getSize() {
      return 2;
    }
  }

  /**
   * Corresponds to the {@code uint32} {@code set} type of SBE
   */
  public static class U32 extends SetEncoder {

    /**
     * {@inheritDoc}
     */
    @Override
    public void encode(String value, ByteBuffer buffer) {
      buffer.putInt(Integer.parseUnsignedInt(value, 2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeNull(ByteBuffer buffer) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMin(ByteBuffer buffer) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMax(ByteBuffer buffer) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getSize() {
      return 4;
    }
  }

  /**
   * Corresponds to the {@code uint64} {@code set} type of SBE
   */
  public static class U64 extends SetEncoder {

    /**
     * {@inheritDoc}
     */
    @Override
    public void encode(String value, ByteBuffer buffer) {
      buffer.putLong(Long.parseLong(value, 2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeNull(ByteBuffer buffer) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMin(ByteBuffer buffer) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMax(ByteBuffer buffer) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getSize() {
      return 8;
    }
  }
}
