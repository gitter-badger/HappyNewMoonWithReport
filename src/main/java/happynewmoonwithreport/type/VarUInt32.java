/*
 *  Copyright 2017 Whole Bean Software, LTD.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;

/**
 * A unsigned Integer of N bits.
 * <p>
 * Stored in the wasm file as a LEB128 variable-length integer, limited to N bits (i.e., the values [0,
 * 2^N-1]), represented by at most ceil(N/7) bytes that may contain padding 0x80
 * bytes.
 * <p>
 * <p>
 * Used to read and write to the wasm file. This project tends to use the 'main' integer types Int32, Int64, UInt32,
 * UInt64.  The recommend use is to convert to a 'main' type as soon as possible.
 * <p>
 * Usage:
 * <pre>
 *      {@code
 *          UInt32 number = new VarUInt32(bytesFile);
 *      }
 * </pre>
 * <p>
 * Source:  <a href="http://webassembly.org/docs/binary-encoding/#varuintn" target="_top">
 * http://webassembly.org/docs/binary-encoding/#varuintn
 * </a>
 */
public final class VarUInt32 extends UInt32 {

    @SuppressWarnings("unused")
    private VarUInt32() {
        super();
    }

    public VarUInt32(BytesFile bytesFile) {
        value = convert(bytesFile);
    }

    @Override
    public Long convert(BytesFile bytesFile) {
        Integer cur;
        Integer count = 0;
        Long result = 0L;

        do {
            cur = bytesFile.readByte() & 0xff;
            result |= (cur & 0x7f) << (count * 7);
            count++;
        } while (((cur & 0x80) != 0) && count < maxBytes());

        return result;
    }

    @Override
    public Integer maxBytes() {
        Integer maxBytes = new Double(Math.ceil((double) maxBits() / 7.0D)).intValue();
        return maxBytes;
    }

    @Override
    public String toString() {
        return "VarUInt32{" +
                "value=" + value +
                "} ";
    }
}
