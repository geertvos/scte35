package tv.mediadistillery.libs.scte35.decoder;

import tv.mediadistillery.libs.decoder.model.BreakDuration;
import tv.mediadistillery.libs.decoder.model.SpliceInfoSection;
import tv.mediadistillery.libs.decoder.model.SpliceInsert;
import tv.mediadistillery.libs.scte35.decoder.Scte35Decoder;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.util.Base64;


/**
 * Created by andres.aguilar on 6/17/16.
 */
public class Scte35DecoderTest {

    private Scte35Decoder scte35Decoder;

    @BeforeClass
    public void setUp() throws Exception {
        scte35Decoder = new Scte35Decoder(false);
    }


    /**
     * Given input:
     * {@code "/DAlAAAAAAAAAP/wFAUAAAPof+//SVqZrP4Ae5igAAEBAQAAQcfnVA=="}
     * Expected result:
     * {@code
     * <SpliceInfoSection tableID="252" sectionSyntaxIndicator="0" privateIndicator="0" reserved_0="3" sectionLength="37" protocolVersion="0" ptsAdjustment="0" cwIndex="0" tier="4095" spliceCommandLength="20">
     * <SpliceInsert spliceEventId="1000" spliceEventCancelIndicator="0" reserved_0="127" outOfNetworkIndicator="1" spliceImmediateFlag="0" reserved_1="15" uniqueProgramId="1" availNum="1" availsExpected="1">
     * <Program><SpliceTime ptsTime="5525641644"/></Program>
     * <BreakDuration autoReturn="1" reserved="63" duration="8100000"/>
     * </SpliceInsert>
     * <descriptorLoopLength>0</descriptorLoopLength>
     * <AlignmentStuffing>0</AlignmentStuffing>
     * <Crc_32>1103619924</Crc_32>
     * </SpliceInfoSection>
     * }
     * <p>
     * NOTE: reserved fields indices are shifted by 1, i.e. reserved_0 => reserved1
     *
     * @throws Exception
     */
    @Test
    public void testExample1() throws Exception {
        byte[] data = Base64.getDecoder().decode("/DAlAAAAAAAAAP/wFAUAAAPof+//SVqZrP4Ae5igAAEBAQAAQcfnVA==");
        SpliceInfoSection spliceInfoSection = scte35Decoder.decode35(data);
        assertEquals(252, spliceInfoSection.getTableID());
        assertEquals(3, spliceInfoSection.getReserved1());
        assertEquals(37, spliceInfoSection.getSectionLength());
        assertEquals(4095, spliceInfoSection.getTier());
        assertEquals(20, spliceInfoSection.getSpliceCommandLength());
        SpliceInsert spliceInsert = spliceInfoSection.getSpliceInsert();
        assertEquals(1000, spliceInsert.getSpliceEventID());
        //assertEquals(127,spliceInsert.reserved1); //Not set in decoder
        assertEquals(1, spliceInsert.getOutOfNetworkIndicator());
        //assertEquals(15,spliceInsert.reserved2); //Not set in decoder
        assertEquals(1, spliceInsert.getUniqueProgramID());
        assertEquals(1, spliceInsert.getAvailNum());
        assertEquals(1, spliceInsert.getAvailsExpected());
        BreakDuration breakDuration = spliceInsert.getBreakDuration();
        assertEquals(true, breakDuration.getAutoReturn());
        //assertEquals(63, breakDuration.reserved1); //Not set in decoder
        assertEquals(8100000L, breakDuration.getDuration());
        assertEquals(1103619924L, spliceInfoSection.getCRC32());

        System.out.println(spliceInfoSection);
    }


    /**
     * Given input:
     * {@code "/DAlAAAAAAAAAP/wFAUAACtnf+/+s9z9LP4Ae5igAAEBAQAAwWSPdQ=="}
     * Expected result:
     * {@code
     * <SpliceInfoSection tableID="252" sectionSyntaxIndicator="0" privateIndicator="0" reserved_0="3" sectionLength="37" protocolVersion="0" ptsAdj ustment="0" cwIndex="0" tier="4095" spliceCommandLength="20">
     * <SpliceInsert spliceEventId="11111" spliceEventCancelIndicator="0" reserved_0="127" outOfNetworkIndicator="1" spliceImmediateFlag="0" reserved_1="15" uniqueProgramId="1" availNum="1" availsExpected="1">
     * <Program>
     * <SpliceTime ptsTime="3017604396"/>
     * </Program>
     * <BreakDuration autoReturn="1" reserved="63" duration="8100000"/>
     * </SpliceInsert>
     * <descriptorLoopLength>0</descriptorLoopLength>
     * <AlignmentStuffing>0</AlignmentStuffing>
     * <Crc_32>-1050374283</Crc_32>
     * </SpliceInfoSection>
     * }
     * <p>
     * NOTE: reserved fields indices are shifted by 1, i.e. reserved_0 => reserved1
     *
     * @throws Exception
     */
    @Test
    public void testExample2() throws Exception {
        byte[] data = Base64.getDecoder().decode("/DAlAAAAAAAAAP/wFAUAACtnf+/+s9z9LP4Ae5igAAEBAQAAwWSPdQ==");
        SpliceInfoSection spliceInfoSection = scte35Decoder.decode35(data);
        assertEquals(252, spliceInfoSection.getTableID());
        assertEquals(3, spliceInfoSection.getReserved1());
        assertEquals(37, spliceInfoSection.getSectionLength());
        assertEquals(4095, spliceInfoSection.getTier());
        assertEquals(20, spliceInfoSection.getSpliceCommandLength());
        SpliceInsert spliceInsert = spliceInfoSection.getSpliceInsert();
        assertEquals(11111, spliceInsert.getSpliceEventID());
        //assertEquals(127,spliceInsert.reserved1); //Not set in decoder
        assertEquals(1, spliceInsert.getOutOfNetworkIndicator());
        //assertEquals(15,spliceInsert.reserved2); //Not set in decoder
        assertEquals(1, spliceInsert.getUniqueProgramID());
        assertEquals(1, spliceInsert.getAvailNum());
        assertEquals(1, spliceInsert.getAvailsExpected());
        BreakDuration breakDuration = spliceInsert.getBreakDuration();
        assertEquals(true, breakDuration.getAutoReturn());
        //assertEquals(63, breakDuration.reserved1); //Not set in decoder
        assertEquals(8100000L, breakDuration.getDuration());
        assertEquals(-1050374283L, spliceInfoSection.getCRC32());

        System.out.println(spliceInfoSection);
    }


    /**
     * From Example on Python decoder: https://gist.github.com/use-sparingly/6517a8b94a52746af028
     *
     *
     * Given input:
     * {@code "/DAlAAAAAAAAAP/wFAUAAAABf+/+LRQrAP4BI9MIAAEBAQAAfxV6SQ=="}
     * Expected result:
     * {@code
     * <SpliceInfoSection tableID="252" sectionSyntaxIndicator="0" privateIndicator="0" reserved_0="3" sectionLength="37" protocolVersion="0" ptsAdjustment="0" cwIndex="0" tier="4095" spliceCommandLength="20">
     * <SpliceInsert spliceEventId="1" spliceEventCancelIndicator="0" reserved_0="127" outOfNetworkIndicator="1" spliceImmediateFlag="0" reserved_1="15" uniqueProgramId="1" availNum="1" availsExpected="1">
     * <Program><SpliceTime ptsTime="756296448"/></Program>
     * <BreakDuration autoReturn="1" reserved="63" duration="19125000"/>
     * </SpliceInsert>
     * <descriptorLoopLength>0</descriptorLoopLength>
     * <AlignmentStuffing>0</AlignmentStuffing>
     * <Crc_32>2132113993</Crc_32>
     * </SpliceInfoSection>
     * }
     * <p>
     * NOTE: reserved fields indices are shifted by 1, i.e. reserved_0 => reserved1
     *
     * @throws Exception
     */
    @Test
    public void testExample3() throws Exception {
        byte[] data = Base64.getDecoder().decode("/DAlAAAAAAAAAP/wFAUAAAABf+/+LRQrAP4BI9MIAAEBAQAAfxV6SQ==");
        SpliceInfoSection spliceInfoSection = scte35Decoder.decode35(data);
        assertEquals(252, spliceInfoSection.getTableID());
        assertEquals(3, spliceInfoSection.getReserved1());
        assertEquals(37, spliceInfoSection.getSectionLength());
        assertEquals(4095, spliceInfoSection.getTier());
        assertEquals(20, spliceInfoSection.getSpliceCommandLength());
        SpliceInsert spliceInsert = spliceInfoSection.getSpliceInsert();
        assertEquals(1, spliceInsert.getSpliceEventID());
        //assertEquals(127,spliceInsert.reserved1); //Not set in decoder
        assertEquals(1, spliceInsert.getOutOfNetworkIndicator());
        //assertEquals(15,spliceInsert.reserved2); //Not set in decoder
        assertEquals(1, spliceInsert.getUniqueProgramID());
        assertEquals(1, spliceInsert.getAvailNum());
        assertEquals(1, spliceInsert.getAvailsExpected());
        BreakDuration breakDuration = spliceInsert.getBreakDuration();
        assertEquals(true, breakDuration.getAutoReturn());
        //assertEquals(63, breakDuration.reserved1); //Not set in decoder
        assertEquals(19125000L, breakDuration.getDuration());
        assertEquals(2132113993L, spliceInfoSection.getCRC32());

        System.out.println(spliceInfoSection);
    }

}