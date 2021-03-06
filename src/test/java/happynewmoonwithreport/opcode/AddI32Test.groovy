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
package happynewmoonwithreport.opcode

import happynewmoonwithreport.WasmInstanceInterface
import happynewmoonwithreport.WasmRuntimeException
import happynewmoonwithreport.type.Int32
import happynewmoonwithreport.type.Int64
import spock.lang.Specification

/**
 * Created on 2017-08-25.
 */
class AddI32Test extends Specification {
    void setup() {
    }

    void cleanup() {
    }

    def "Execute AddI32"() {
        setup: " a value of 3 and a value of 4"
        WasmInstanceInterface instance = new WasmInstanceStub();
        instance.stack().push(new Int32(val1));
        instance.stack().push(new Int32(val2));

        AddI32 function = new AddI32(instance);

        when: "run the opcode"
        function.execute();

        then: " a value of expected"

        new Int32(expected) == instance.stack().pop();

        where: ""
        val1        | val2 || expected
        3           | 4    || 7
        4           | 3    || 7
        0x7FFF_FFFE | 0x1  || 0x7FFF_FFFF
        0x7FFF_FFFE | 0x1  || new Int32(0).maxValue();
    }


    def "Execute AddI32 throw exception on incorrect Type on second param "() {
        setup: " a value of int64  of 3  and a value of int32 of 4"
        WasmInstanceInterface instance = new WasmInstanceStub();
        instance.stack().push(new Int64(3));
        instance.stack().push(new Int32(4));

        AddI32 function = new AddI32(instance);

        when: "run the opcode"
        function.execute();

        then: " Thrown Exception"
        WasmRuntimeException exception = thrown();
        exception.message.contains("Value2");  // not sure if this is the Wasm Spec. Maybe it should be "Value1"
    }

    def "Execute AddI32 throw exception on incorrect Type on first param "() {
        setup: " a value of int32  of 3  and a value of int64 of 4"
        WasmInstanceInterface instance = new WasmInstanceStub();
        instance.stack().push(new Int32(3));
        instance.stack().push(new Int64(4));

        AddI32 function = new AddI32(instance);

        when: "run the opcode"
        function.execute();

        then: " Thrown Exception"
        WasmRuntimeException exception = thrown();
        exception.message.contains("Value1");  // not sure if this is the Wasm Spec. Maybe it should be "Value1"
    }
}
