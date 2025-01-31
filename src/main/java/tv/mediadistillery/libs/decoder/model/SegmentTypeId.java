package tv.mediadistillery.libs.decoder.model;

public enum SegmentTypeId {
    
    NOT_INCATED(0x00),
    CONTENT_IDENTIFICATION(0x01),
    PROGRAM_START(0x10),
    PROGRAM_END(0x11),
    PROGRAM_EARLY_TERMINATION(0x12),
    PROGRAM_BREAKAWAY(0x13),
    PROGRAM_RESUMPTION(0x14),
    PROGRAM_RUNOVER_PLANNED(0x15),
    PROGRAM_RUNOVER_UNPLANNED(0x16),
    PROGRAM_OVERLAP_START(0x17),
    CHAPTER_START(0x20),
    CHAPTER_END(0x21),
    PROVIDER_ADVERTISEMENT_START(0x30),
    PROVIDER_ADVERTISEMENT_END(0x31),
    DISTRIBUTOR_ADVERTISEMENT_START(0x32),
    DISTRIBUTOR_ADVERTISEMENT_END(0x33),
    PLACEMENT_OPPERTUNITY_START(0x34),
    PLACEMENT_OPPERTUNITY_END(0x35),
    UNSCHEDULED_EVENT_START(0x40),
    UNSCHEDULED_EVENT_END(0x41),
    NETWORK_START(0x50),
    NETWORK_END(0x51),
    UNKNOWN(-1);
    
    private int value;
    
    private SegmentTypeId(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    public static SegmentTypeId fromInt(int value) {
        for(SegmentTypeId id : SegmentTypeId.values()) {
            if(id.value == value) {
                return id;
            }
        }
        return UNKNOWN;
    }
    /**

case 0x50:
    log("Type = Network Start\n");
    break;
case 0x51:
    log("Type = Network End\n");
    break;
default:
    log("Type = Unknown = " + seg[segptr].getSegmentationTypeID() + "\n");
    break;
 **/   
}
