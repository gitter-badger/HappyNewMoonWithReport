package happynewmoonwithreport.section;

import happynewmoonwithreport.BytesFile;
import happynewmoonwithreport.FunctionType;
import happynewmoonwithreport.type.UInt32;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SectionTypeTest {

    SectionType sectionType;

    @Before
    public void setUp() throws Exception {
        sectionType = new SectionType();
        assertNotNull(sectionType);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInstantiate() {
        byte[] byteAll = {(byte) 0x01, (byte) 0x60, (byte) 0x02, (byte) 0x7F, (byte) 0x7F, (byte) 0x01, (byte) 0x7F};
        BytesFile payload = new BytesFile(byteAll);
        // run
        sectionType.instantiate(payload);

        // verify
        assertEquals(1, sectionType.getFunctionSignatures().size());
        final ArrayList<FunctionType> functionSignatureAll = sectionType.getFunctionSignatures();
        FunctionType functionType = functionSignatureAll.get(0);
        assertEquals("func",  functionType.getForm().getValue());
        assertEquals(new UInt32(2L),  functionType.getParamCount());
        assertEquals(new UInt32(1),  functionType.getReturnCount());

        assertEquals( new UInt32(2L), functionType.getParamCount());
        assertEquals( new UInt32(1), functionType.getReturnCount());

        assertEquals(2, functionType.getParamTypeAll().size());
        assertEquals(1, functionType.getReturnTypeAll().size());

        assertEquals( "int32"  , functionType.getParamTypeAll().get(0).getValue());
        assertEquals( "int32"  , functionType.getParamTypeAll().get(1).getValue());
        assertEquals( "int32"  , functionType.getReturnTypeAll().get(0).getValue());


    }

}