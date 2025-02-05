
package tv.mediadistillery.libs.scte35.decoder;

import java.util.Base64;

import tv.mediadistillery.libs.decoder.log.LogCallback;
import tv.mediadistillery.libs.decoder.model.SegmentTypeId;
import tv.mediadistillery.libs.decoder.model.SegmentationDescriptor;
import tv.mediadistillery.libs.decoder.model.SpliceCommandType;
import tv.mediadistillery.libs.decoder.model.SpliceInfoSection;
import tv.mediadistillery.libs.decoder.model.SpliceInsert;
import tv.mediadistillery.libs.decoder.model.TimeSignal;
import tv.mediadistillery.libs.scte35.decoder.exception.DecodingException;

/**
 * Implements a SCTE35 Decoding mechanism.
 */
public final class Scte35Decoder {

    private static final int[] CrcTable = {
            0x00000000, 0x04C11DB7, 0x09823B6E, 0x0D4326D9, 0x130476DC, 0x17C56B6B, 0x1A864DB2, 0x1E475005, 0x2608EDB8, 0x22C9F00F,
            0x2F8AD6D6, 0x2B4BCB61, 0x350C9B64, 0x31CD86D3, 0x3C8EA00A, 0x384FBDBD, 0x4C11DB70, 0x48D0C6C7, 0x4593E01E, 0x4152FDA9,
            0x5F15ADAC, 0x5BD4B01B, 0x569796C2, 0x52568B75, 0x6A1936C8, 0x6ED82B7F, 0x639B0DA6, 0x675A1011, 0x791D4014, 0x7DDC5DA3,
            0x709F7B7A, 0x745E66CD, 0x9823B6E0, 0x9CE2AB57, 0x91A18D8E, 0x95609039, 0x8B27C03C, 0x8FE6DD8B, 0x82A5FB52, 0x8664E6E5,
            0xBE2B5B58, 0xBAEA46EF, 0xB7A96036, 0xB3687D81, 0xAD2F2D84, 0xA9EE3033, 0xA4AD16EA, 0xA06C0B5D, 0xD4326D90, 0xD0F37027,
            0xDDB056FE, 0xD9714B49, 0xC7361B4C, 0xC3F706FB, 0xCEB42022, 0xCA753D95, 0xF23A8028, 0xF6FB9D9F, 0xFBB8BB46, 0xFF79A6F1,
            0xE13EF6F4, 0xE5FFEB43, 0xE8BCCD9A, 0xEC7DD02D, 0x34867077, 0x30476DC0, 0x3D044B19, 0x39C556AE, 0x278206AB, 0x23431B1C,
            0x2E003DC5, 0x2AC12072, 0x128E9DCF, 0x164F8078, 0x1B0CA6A1, 0x1FCDBB16, 0x018AEB13, 0x054BF6A4, 0x0808D07D, 0x0CC9CDCA,
            0x7897AB07, 0x7C56B6B0, 0x71159069, 0x75D48DDE, 0x6B93DDDB, 0x6F52C06C, 0x6211E6B5, 0x66D0FB02, 0x5E9F46BF, 0x5A5E5B08,
            0x571D7DD1, 0x53DC6066, 0x4D9B3063, 0x495A2DD4, 0x44190B0D, 0x40D816BA, 0xACA5C697, 0xA864DB20, 0xA527FDF9, 0xA1E6E04E,
            0xBFA1B04B, 0xBB60ADFC, 0xB6238B25, 0xB2E29692, 0x8AAD2B2F, 0x8E6C3698, 0x832F1041, 0x87EE0DF6, 0x99A95DF3, 0x9D684044,
            0x902B669D, 0x94EA7B2A, 0xE0B41DE7, 0xE4750050, 0xE9362689, 0xEDF73B3E, 0xF3B06B3B, 0xF771768C, 0xFA325055, 0xFEF34DE2,
            0xC6BCF05F, 0xC27DEDE8, 0xCF3ECB31, 0xCBFFD686, 0xD5B88683, 0xD1799B34, 0xDC3ABDED, 0xD8FBA05A, 0x690CE0EE, 0x6DCDFD59,
            0x608EDB80, 0x644FC637, 0x7A089632, 0x7EC98B85, 0x738AAD5C, 0x774BB0EB, 0x4F040D56, 0x4BC510E1, 0x46863638, 0x42472B8F,
            0x5C007B8A, 0x58C1663D, 0x558240E4, 0x51435D53, 0x251D3B9E, 0x21DC2629, 0x2C9F00F0, 0x285E1D47, 0x36194D42, 0x32D850F5,
            0x3F9B762C, 0x3B5A6B9B, 0x0315D626, 0x07D4CB91, 0x0A97ED48, 0x0E56F0FF, 0x1011A0FA, 0x14D0BD4D, 0x19939B94, 0x1D528623,
            0xF12F560E, 0xF5EE4BB9, 0xF8AD6D60, 0xFC6C70D7, 0xE22B20D2, 0xE6EA3D65, 0xEBA91BBC, 0xEF68060B, 0xD727BBB6, 0xD3E6A601,
            0xDEA580D8, 0xDA649D6F, 0xC423CD6A, 0xC0E2D0DD, 0xCDA1F604, 0xC960EBB3, 0xBD3E8D7E, 0xB9FF90C9, 0xB4BCB610, 0xB07DABA7,
            0xAE3AFBA2, 0xAAFBE615, 0xA7B8C0CC, 0xA379DD7B, 0x9B3660C6, 0x9FF77D71, 0x92B45BA8, 0x9675461F, 0x8832161A, 0x8CF30BAD,
            0x81B02D74, 0x857130C3, 0x5D8A9099, 0x594B8D2E, 0x5408ABF7, 0x50C9B640, 0x4E8EE645, 0x4A4FFBF2, 0x470CDD2B, 0x43CDC09C,
            0x7B827D21, 0x7F436096, 0x7200464F, 0x76C15BF8, 0x68860BFD, 0x6C47164A, 0x61043093, 0x65C52D24, 0x119B4BE9, 0x155A565E,
            0x18197087, 0x1CD86D30, 0x029F3D35, 0x065E2082, 0x0B1D065B, 0x0FDC1BEC, 0x3793A651, 0x3352BBE6, 0x3E119D3F, 0x3AD08088,
            0x2497D08D, 0x2056CD3A, 0x2D15EBE3, 0x29D4F654, 0xC5A92679, 0xC1683BCE, 0xCC2B1D17, 0xC8EA00A0, 0xD6AD50A5, 0xD26C4D12,
            0xDF2F6BCB, 0xDBEE767C, 0xE3A1CBC1, 0xE760D676, 0xEA23F0AF, 0xEEE2ED18, 0xF0A5BD1D, 0xF464A0AA, 0xF9278673, 0xFDE69BC4,
            0x89B8FD09, 0x8D79E0BE, 0x803AC667, 0x84FBDBD0, 0x9ABC8BD5, 0x9E7D9662, 0x933EB0BB, 0x97FFAD0C, 0xAFB010B1, 0xAB710D06,
            0xA6322BDF, 0xA2F33668, 0xBCB4666D, 0xB8757BDA, 0xB5365D03, 0xB1F740B4
    };
    
    private final LogCallback logCallback;

    public Scte35Decoder(LogCallback logCallback) {
        this.logCallback = logCallback;
    }

    public Scte35Decoder() {
        this(null);
    }

    long crc32(byte[] b64, int startIdx, int endIdx) {
        int value = 0xFFFFFFFF;
        int ptr;

        for (int i = startIdx; i < endIdx; i++) {
            ptr = (((value >> 24) & 0x00ff) ^ b64[i]) & 0x00FF;
            value = (value << 8) ^ CrcTable[ptr];
        }

        return (value & 0xFFFFFFFFL);
    }

    public SpliceInfoSection decode35(byte[] b64) throws DecodingException {
        SpliceInfoSection spliceInfoSection = new SpliceInfoSection();
        SegmentationDescriptor[] seg = new SegmentationDescriptor[10];
        int i1;
        int i2;
        long l1;
        long l2;
        long l3;
        long l4;
        long l5;
        long l6;
        long l7;
        long l8;
        int bufptr;
        int desptr;
        int segptr = 0;

        String stemp = "";
        log("Hex=0x");

        for (int i = 0; i < b64.length; i++) {
            stemp += String.format("%02X", b64[i]);
        }
        log(stemp + "\nBase64=" + Base64.getEncoder().encode(b64) + "\n\n");

        log("Decoded length = " + b64.length + "\n");


        spliceInfoSection.setTableID(b64[0] & 0x00ff);
        if (spliceInfoSection.getTableID() != 0x0FC) {
            throw new DecodingException("Invalid Table ID != 0xFC");
        }
        log("Table ID = 0xFC\n");

        spliceInfoSection.setSectionSyntaxIndicator(((b64[1] >> 7) & 0x01) > 0);
        if (spliceInfoSection.getSectionSyntaxIndicator()) {
            log("ERROR Long section used\n");
        } else {
            log("MPEG Short Section\n");
        }

        spliceInfoSection.setPrivateIndicator(((b64[1] >> 6) & 0x01) > 0);
        if (spliceInfoSection.hasPrivateIndicator()) {
            log("ERROR Private section signaled\n");
        } else {
            log("Not Private\n");
        }

        spliceInfoSection.setReserved1((b64[1] >> 4) & 0x03);
        log(String.format("Reserved = 0x%x\n", spliceInfoSection.getReserved1()));

        i1 = b64[1] & 0x0f;
        i2 = b64[2] & 0x00ff;
        spliceInfoSection.setSectionLength((i1 << 8) + i2);
        log(("Section Length = " + spliceInfoSection.getSectionLength() + "\n"));

        spliceInfoSection.setProtocolVersion(b64[3]);
        log(("Protocol Version = " + spliceInfoSection.getProtocolVersion() + "\n"));

        spliceInfoSection.setEncryptedPacket(((b64[4] >> 7) & 0x01) > 0);
        spliceInfoSection.setEncryptionAlgorithm((b64[4] >> 1) & 0x3F);
        if (spliceInfoSection.getEncryptedPacket()) {
            log("Encrypted Packet\n");
            log(String.format("Encryption Algorithm = 0x%x\n", spliceInfoSection.getEncryptionAlgorithm()));
        } else {
            log("unencrypted Packet\n");
        }

        l1 = b64[4] & 0x01;
        l2 = b64[5] & 0x00ff;
        l3 = b64[6] & 0x00ff;
        l4 = b64[7] & 0x00ff;
        l5 = b64[8] & 0x00ff;
        spliceInfoSection.setPtsAdjustment((l1 << 32) + (l2 << 24) + (l3 << 16) + (l4 << 8) + l5);
        log(String.format("PTS Adjustment = 0x%09x\n", spliceInfoSection.getPtsAdjustment()));

        spliceInfoSection.setCwIndex(b64[9] & 0x00ff);
        if (spliceInfoSection.getEncryptedPacket()) {
            log(String.format("CW Index = 0x%x\n", spliceInfoSection.getCwIndex()));
        }

        i1 = b64[10] & 0x00ff;
        i2 = (b64[11] & 0x00f0) >> 4;
        spliceInfoSection.setTier((i1 << 4) + i2);
        log(String.format("Tier = 0x%x\n", spliceInfoSection.getTier()));

        i1 = b64[11] & 0x000f;
        i2 = b64[12] & 0x00ff;
        spliceInfoSection.setSpliceCommandLength((i1 << 8) + i2);
        log(String.format("Splice Command Length = 0x%x\n", spliceInfoSection.getSpliceCommandLength()));

        int command = b64[13] & 0x00ff;
        spliceInfoSection.setSpliceCommandType(SpliceCommandType.fromInt(command));
        bufptr = 14;
        SpliceInsert spliceInsert = new SpliceInsert();
        spliceInfoSection.setSpliceInsert(spliceInsert);
        switch (spliceInfoSection.getSpliceCommandType()) {
            case SPLICE_NULL:
                log("Splice Null\n");
                break;
            case SPLICE_SCHEDULE:
                log("Splice Schedule\n");
                break;
            case SPLICE_INSERT:
                log("Splice Insert\n");
                l1 = b64[bufptr] & 0x00ff;
                bufptr++;
                l2 = b64[bufptr] & 0x00ff;
                bufptr++;
                l3 = b64[bufptr] & 0x00ff;
                bufptr++;
                l4 = b64[bufptr] & 0x00ff;
                bufptr++;
                spliceInsert.setSpliceEventID((int) (((l1 << 24) + (l2 << 16) + (l3 << 8) + l4) & 0x00ffffffff));
                log(String.format("Splice Event ID = 0x%x\n", spliceInsert.getSpliceEventID()));

                i1 = b64[bufptr] & 0x080;
                bufptr++;
                if (i1 != 0) {
                    spliceInsert.setSpliceEventCancelIndicator(true);
                    log("Splice Event Canceled\n");
                } else {
                    spliceInsert.setSpliceEventCancelIndicator(false);
                }

                spliceInsert.setOutOfNetworkIndicator(((b64[bufptr] & 0x080) >> 7) > 0);
                spliceInsert.setProgramSpliceFlag(((b64[bufptr] & 0x040) >> 6) > 0);
                spliceInsert.setDurationFlag(((b64[bufptr] & 0x020) >> 5) > 0);
                spliceInsert.setSpliceImmediateFlag(((b64[bufptr] & 0x010) >> 4) > 0);
                bufptr++;
                log("Flags OON=" + spliceInsert.getOutOfNetworkIndicator() + " Prog=" + spliceInsert.getProgramSpliceFlag()
                        + " Duration=" + spliceInsert.getDurationFlag() + " Immediate=" + spliceInsert.getSpliceImmediateFlag() + "\n");

                if ((spliceInsert.getProgramSpliceFlag()) && (!spliceInsert.getSpliceImmediateFlag())) {
                    if ((b64[bufptr] & 0x080) != 0) {
                        // time specified
                        l1 = b64[bufptr] & 0x01;
                        bufptr++;
                        l2 = b64[bufptr] & 0x00ff;
                        bufptr++;
                        l3 = b64[bufptr] & 0x00ff;
                        bufptr++;
                        l4 = b64[bufptr] & 0x00ff;
                        bufptr++;
                        l5 = b64[bufptr] & 0x00ff;
                        spliceInsert.getSisp().setPtsTime((l1 << 32) + (l2 << 24) + (l3 << 16) + (l4 << 8) + l5);
                        log(String.format("Splice time = 0x%09x\n", spliceInsert.getSisp().getPtsTime()));
                    }
                    bufptr++;
                }

                if (spliceInsert.getDurationFlag()) {
                    spliceInsert.getBreakDuration().setAutoReturn((b64[bufptr] & 0x080) >> 7 > 0);
                    if (spliceInsert.getBreakDuration().getAutoReturn()) {
                        log("Auto Return\n");
                    }
                    l1 = b64[bufptr] & 0x01;
                    bufptr++;
                    l2 = b64[bufptr] & 0x00ff;
                    bufptr++;
                    l3 = b64[bufptr] & 0x00ff;
                    bufptr++;
                    l4 = b64[bufptr] & 0x00ff;
                    bufptr++;
                    l5 = b64[bufptr] & 0x00ff;
                    bufptr++;
                    spliceInsert.getBreakDuration().setDuration((l1 << 32) + (l2 << 24) + (l3 << 16) + (l4 << 8) + l5);
                    double bsecs = spliceInsert.getBreakDuration().getDuration();
                    bsecs /= 90000.0;
                    log(String.format("break duration = 0x%09x = %f seconds\n", spliceInsert.getBreakDuration().getDuration(), bsecs));
                }
                i1 = b64[bufptr] & 0x00ff;
                bufptr++;
                i2 = b64[bufptr] & 0x00ff;
                bufptr++;
                spliceInsert.setUniqueProgramID((i1 << 8) + i2);
                log("Unique Program ID = " + spliceInsert.getUniqueProgramID() + "\n");

                spliceInsert.setAvailNum(b64[bufptr] & 0x00ff);
                bufptr++;
                log("Avail Num = " + spliceInsert.getAvailNum() + "\n");

                spliceInsert.setAvailsExpected(b64[bufptr] & 0x00ff);
                bufptr++;
                log("Avails Expected = " + spliceInsert.getAvailsExpected() + "\n");

                break;
            case TIME_SIGNAL:
                log("Time Signal\n");
                TimeSignal timeSignal = new TimeSignal();
                timeSignal.tssp.setTimeSpecifiedFlag(((b64[bufptr] & 0x080) >> 7) > 0 );
                if (timeSignal.tssp.getTimeSpecifiedFlag()) {
                    // time specified
                    l1 = b64[bufptr] & 0x01;
                    bufptr++;
                    l2 = b64[bufptr] & 0x00ff;
                    bufptr++;
                    l3 = b64[bufptr] & 0x00ff;
                    bufptr++;
                    l4 = b64[bufptr] & 0x00ff;
                    bufptr++;
                    l5 = b64[bufptr] & 0x00ff;
                    timeSignal.tssp.setPtsTime((l1 << 32) + (l2 << 24) + (l3 << 16) + (l4 << 8) + l5);
                    log(String.format("Time = 0x%09x\n", timeSignal.tssp.getPtsTime()));
                }
                bufptr++;
                break;
            case BANDWIDTH_RESERVATION:
                log("Bandwidth Reservation\n");
                break;
            case PRIVATE_COMMAND:
                log("Private Command\n");
                break;
            default:
                log(String.format("ERROR Unknown command = 0x%x\n", spliceInfoSection.getSpliceCommandType()));
                // Unknown command, oops
                break;
        }

        if (spliceInfoSection.getSpliceCommandLength() != 0x0fff) { // legacy check
            if (bufptr != (spliceInfoSection.getSpliceCommandLength() + 14)) {
                log("ERROR decoded command length " + bufptr + " not equal to specified command length " + spliceInfoSection.getSpliceCommandLength() + "\n");
                //Some kind of error, or unknown command
                //bufptr = spliceInfoSection.spliceCommandLength + 14;
            }
        }

        i1 = b64[bufptr] & 0x00ff;
        bufptr++;
        i2 = b64[bufptr] & 0x00ff;
        bufptr++;
        spliceInfoSection.setDescriptorLoopLength((i1 << 8) + i2);
        log("Descriptor Loop Length = " + spliceInfoSection.getDescriptorLoopLength() + "\n");

        desptr = bufptr;

        if (spliceInfoSection.getDescriptorLoopLength() > 0) {
            while ((bufptr - desptr) < spliceInfoSection.getDescriptorLoopLength()) {
                int tag = b64[bufptr] & 0x00ff;
                bufptr++;
                int len = b64[bufptr] & 0x00ff;
                bufptr++;
                l1 = b64[bufptr] & 0x00ff;
                bufptr++;
                l2 = b64[bufptr] & 0x00ff;
                bufptr++;
                l3 = b64[bufptr] & 0x00ff;
                bufptr++;
                l4 = b64[bufptr] & 0x00ff;
                bufptr++;
                int identifier = (int) ((l1 << 24) + (l2 << 16) + (l3 << 8) + l4);
                if (identifier == 0x43554549) {
                    switch (tag) {
                        case 0:
                            log("Avail Descriptor - Length=" + len + "\n");
                            l1 = b64[bufptr] & 0x00ff;
                            bufptr++;
                            l2 = b64[bufptr] & 0x00ff;
                            bufptr++;
                            l3 = b64[bufptr] & 0x00ff;
                            bufptr++;
                            l4 = b64[bufptr] & 0x00ff;
                            bufptr++;
                            int availDesc = (int) (((l1 << 24) + (l2 << 16) + (l3 << 8) + l4) & 0x00ffffffff);
                            log(String.format("Avail Descriptor = 0x%08x\n", availDesc));
                            break;
                        case 1:
                            log("DTMF Descriptor - Length=" + len + "\n");
                            double preroll = b64[bufptr] & 0x00ff;
                            preroll /= 10;
                            log("Preroll = " + preroll + "\n");
                            bufptr++;
                            int dtmfCount = (b64[bufptr] & 0x00E0) >> 5;
                            bufptr++;
                            log(dtmfCount + "DTMF chars = ");
                            for (int i = 0; i < dtmfCount; i++) {
                                log(String.format("%c", b64[bufptr] & 0x00ff));
                                bufptr++;
                            }
                            log("\n");
                            break;
                        case 2:
                            log("Segmentation Descriptor - Length=" + len + "\n");
                            seg[segptr] = new SegmentationDescriptor();
                            l1 = b64[bufptr] & 0x00ff;
                            bufptr++;
                            l2 = b64[bufptr] & 0x00ff;
                            bufptr++;
                            l3 = b64[bufptr] & 0x00ff;
                            bufptr++;
                            l4 = b64[bufptr] & 0x00ff;
                            bufptr++;
                            seg[segptr].setSegmentationEventID((int) (((l1 << 24) + (l2 << 16) + (l3 << 8) + l4) & 0x00ffffffff));
                            log(String.format("Segmentation Event ID = 0x%08x\n", seg[segptr].getSegmentationEventID()));
                            seg[segptr].setSegmentationEventCancelIndicator((b64[bufptr] & 0x080) >> 7);
                            bufptr++;
                            if (seg[segptr].getSegmentationEventCancelIndicator() == 0) {
                                log("Segmentation Event Cancel Indicator NOT set\n");
                                seg[segptr].setProgramSegmentationFlag((b64[bufptr] & 0x080) >> 7);
                                seg[segptr].setSegmentationDurationFlag((b64[bufptr] & 0x040) >> 6);
                                seg[segptr].setDeliveryNotRestricted((b64[bufptr] & 0x020) >> 5);
                                log("Delivery Not Restricted flag = " + seg[segptr].getDeliveryNotRestricted() + "\n");
                                if (seg[segptr].getDeliveryNotRestricted() == 0) {
                                    seg[segptr].setWebDeliveryAllowedFlag((b64[bufptr] & 0x010) >> 4);
                                    log("Web Delivery Allowed flag = " + seg[segptr].getWebDeliveryAllowedFlag() + "\n");
                                    seg[segptr].setNoRegionalBlackoutFlag((b64[bufptr] & 0x008) >> 3);
                                    log("No Regional Blackout flag = " + seg[segptr].getNoRegionalBlackoutFlag() + "\n");
                                    seg[segptr].setArchiveAllowed((b64[bufptr] & 0x004) >> 2);
                                    log("Archive Allowed flag = " + seg[segptr].getArchiveAllowed() + "\n");
                                    seg[segptr].setDeviceRestriction((b64[bufptr] & 0x003));
                                    log("Device Restrictions = " + seg[segptr].getDeviceRestriction() + "\n");
                                }
                                bufptr++;
                                if (seg[segptr].getProgramSegmentationFlag() == 0) {
                                    log("Component segmention NOT IMPLEMENTED\n");
                                } else {
                                    log("Program Segmentation flag SET\n");
                                }
                                if (seg[segptr].getSegmentationDurationFlag() == 1) {
                                    l1 = b64[bufptr] & 0x0ff;
                                    bufptr++;
                                    l2 = b64[bufptr] & 0x00ff;
                                    bufptr++;
                                    l3 = b64[bufptr] & 0x00ff;
                                    bufptr++;
                                    l4 = b64[bufptr] & 0x00ff;
                                    bufptr++;
                                    l5 = b64[bufptr] & 0x00ff;
                                    bufptr++;
                                    seg[segptr].setSegmentationDuration((l1 << 32) + (l2 << 24) + (l3 << 16) + (l4 << 8) + l5);
                                    double secs = seg[segptr].getSegmentationDuration();
                                    secs /= 90000.0;
                                    log(String.format("Segmentation Duration = 0x%010x = %f seconds\n", seg[segptr].getSegmentationDuration(), secs));
                                }
                                seg[segptr].setSegmentationUPIDtype(b64[bufptr] & 0x00ff);
                                bufptr++;
                                seg[segptr].setSegmentationUPIDlength(b64[bufptr] & 0x00ff);
                                bufptr++;
                                switch (seg[segptr].getSegmentationUPIDtype()) {
                                    case 0x00:
                                        log("UPID Type = Not Used length = " + seg[segptr].getSegmentationUPIDlength() + "\n");
                                        break;
                                    case 0x01:
                                        log("UPID Type = User Defined (Deprecated) length =" + seg[segptr].getSegmentationUPIDlength() + "\nHex=0x");
                                        for (int j = bufptr; j < (bufptr + seg[segptr].getSegmentationUPIDlength()); j++) {
                                            log(String.format("%02X.", b64[j]));
                                        }
                                        log("\n");
                                        bufptr += seg[segptr].getSegmentationUPIDlength();
                                        break;
                                    case 0x02:
                                        log("UPID Type = ISCII (deprecated)length = " + seg[segptr].getSegmentationUPIDlength() + "\n");
                                        String siTemp = "ISCII=";
                                        for (int j = bufptr; j < (bufptr + seg[segptr].getSegmentationUPIDlength()); j++) {
                                            siTemp += (char) b64[j];
                                        }
                                        siTemp += "\n";
                                        log(siTemp);
                                        bufptr += seg[segptr].getSegmentationUPIDlength();
                                        break;
                                    case 0x03:
                                        log("UPID Type = Ad-IDlength = " + seg[segptr].getSegmentationUPIDlength() + "\n");
                                        String stTemp = "AdId=";
                                        for (int j = bufptr; j < (bufptr + seg[segptr].getSegmentationUPIDlength()); j++) {
                                            stTemp += (char) b64[j];
                                        }
                                        stTemp += "\n";
                                        log(stTemp);
                                        bufptr += seg[segptr].getSegmentationUPIDlength();
                                        break;
                                    case 0x04:
                                        log("UPID Type = UMID SMPTE 330M length = " + seg[segptr].getSegmentationUPIDlength() + "\n");
                                        bufptr += seg[segptr].getSegmentationUPIDlength();
                                        break;
                                    case 0x05:
                                        log("UPID Type = ISAN (Deprecated) length = " + seg[segptr].getSegmentationUPIDlength() + "\n");
                                        bufptr += seg[segptr].getSegmentationUPIDlength();
                                        break;
                                    case 0x06:
                                        log("UPID Type = ISAN length = " + seg[segptr].getSegmentationUPIDlength() + "\n");
                                        bufptr += seg[segptr].getSegmentationUPIDlength();
                                        break;
                                    case 0x07:
                                        log("UPID Type = Tribune ID length = " + seg[segptr].getSegmentationUPIDlength() + "\n");
                                        bufptr += seg[segptr].getSegmentationUPIDlength();
                                        break;
                                    case 0x08:
                                        log("UPID Type = Turner Identifier length = " + seg[segptr].getSegmentationUPIDlength() + "\n");
                                        l1 = b64[bufptr] & 0x0ff;
                                        bufptr++;
                                        l2 = b64[bufptr] & 0x00ff;
                                        bufptr++;
                                        l3 = b64[bufptr] & 0x00ff;
                                        bufptr++;
                                        l4 = b64[bufptr] & 0x00ff;
                                        bufptr++;
                                        l5 = b64[bufptr] & 0x00ff;
                                        bufptr++;
                                        l6 = b64[bufptr] & 0x00ff;
                                        bufptr++;
                                        l7 = b64[bufptr] & 0x00ff;
                                        bufptr++;
                                        l8 = b64[bufptr] & 0x00ff;
                                        bufptr++;
                                        seg[segptr].setTurnerIdentifier((l1 << 56) + (l2 << 48) + (l3 << 40) + (l4 << 32) + (l5 << 24) + (l6 << 16) + (l7 << 8) + l8);
                                        log(String.format("Turner Identifier = 0x%016x\n", seg[segptr].getTurnerIdentifier()));
                                        break;
                                    case 0x09:
                                        log("UPID Type = ADI length = " + seg[segptr].getSegmentationUPIDlength() + "\n");
                                        bufptr += seg[segptr].getSegmentationUPIDlength();
                                        break;
                                    case 0x0A:
                                        log("UPID Type = EIDR length = " + seg[segptr].getSegmentationUPIDlength() + "\n");
                                        bufptr += seg[segptr].getSegmentationUPIDlength();
                                        break;
                                    case 0x0B:
                                        log("UPID Type = ATSC Content Identifier length = " + seg[segptr].getSegmentationUPIDlength() + "\n");
                                        bufptr += seg[segptr].getSegmentationUPIDlength();
                                        break;
                                    case 0x0C:
                                        log("UPID Type = Managed Private UPID length = " + seg[segptr].getSegmentationUPIDlength() + "\n");
                                        bufptr += seg[segptr].getSegmentationUPIDlength();
                                        break;
                                    case 0x0D:
                                        log("UPID Type = Multiple UPID length = " + seg[segptr].getSegmentationUPIDlength() + "\nHex=0x");
                                        for (int j = bufptr; j < (bufptr + seg[segptr].getSegmentationUPIDlength()); j++) {
                                            log(String.format("%02X.", b64[j]));
                                        }
                                        log("\n");
                                        bufptr += seg[segptr].getSegmentationUPIDlength();
                                        break;
                                    default:
                                        log("UPID Type = UNKNOWN length = " + seg[segptr].getSegmentationUPIDlength() + "\nHex=0x");
                                        for (int j = bufptr; j < (bufptr + seg[segptr].getSegmentationUPIDlength()); j++) {
                                            log(String.format("%02X.", b64[j]));
                                        }
                                        log("\n");
                                        bufptr += seg[segptr].getSegmentationUPIDlength();
                                        break;
                                }
                                SegmentTypeId segmentTypeId = SegmentTypeId.fromInt(b64[bufptr] & 0x00ff);
                                seg[segptr].setSegmentationTypeID(segmentTypeId);
                                bufptr++;
                                log("Type = "+segmentTypeId);
                                seg[segptr].setSegmentNum(b64[bufptr] & 0x00ff);
                                bufptr++;
                                seg[segptr].setSegmentsExpected(b64[bufptr] & 0x00ff);
                                bufptr++;
                                log("Segment num = " + seg[segptr].getSegmentNum() + " Segments Expected = " + seg[segptr].getSegmentsExpected() + "\n");
                                segptr++;
                            } else {
                                log("Segmentation Event Cancel Indicator SET\n");
                            }
                            break;
                    }
                } else {
                    log(String.format("Private Descriptor tag=%d Length=%d identifier = 0x%08x  Value = 0x", tag, len, identifier));
                    for (int j = bufptr; j < (bufptr + (len - 4)); j++) {
                        log(String.format("%02X.", b64[j]));
                    }
                    log("\n");
                    bufptr += len - 4;
                }
            }
        }

        if (bufptr != (spliceInfoSection.getDescriptorLoopLength() + desptr)) {
            int dlen = bufptr - desptr;
            log("ERROR decoded descriptor length " + dlen + " not equal to specified descriptor length " + spliceInfoSection.getDescriptorLoopLength() + "\n");
            bufptr = desptr + spliceInfoSection.getDescriptorLoopLength();
            log("SKIPPING REST OF THE COMMAND!\n");
        } else {

            if (spliceInfoSection.getEncryptedPacket()) {
                spliceInfoSection.setAlignmentStuffing(0);
                spliceInfoSection.seteCRC32(0);
            }

            l1 = b64[bufptr] & 0x00ff;
            bufptr++;
            l2 = b64[bufptr] & 0x00ff;
            bufptr++;
            l3 = b64[bufptr] & 0x00ff;
            bufptr++;
            l4 = b64[bufptr] & 0x00ff;
            bufptr++;
            spliceInfoSection.setCRC32( (int) (((l1 << 24) + (l2 << 16) + (l3 << 8) + l4) & 0x00ffffffff));
            log(String.format("CRC32 = 0x%08x\n", spliceInfoSection.getCRC32()));
        }
        log(String.format("calc CRC32 = 0x%08x --- Should = 0x00000000\n", crc32(b64, 0, bufptr)));
        return spliceInfoSection;
    }

    private void log(String log) {
        if (logCallback != null) {
            logCallback.log(log);
        }
    }

}