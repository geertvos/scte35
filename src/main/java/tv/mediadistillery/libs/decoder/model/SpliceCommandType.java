package tv.mediadistillery.libs.decoder.model;

public enum SpliceCommandType {

    SPLICE_NULL(0x00),
    SPLICE_SCHEDULE(0x4),
    SPLICE_INSERT(0x5),
    TIME_SIGNAL(0x6),
    BANDWIDTH_RESERVATION(0x7),
    PRIVATE_COMMAND(0xff),
    UNKNOWN(-1);
    
    private int value;
    
    private SpliceCommandType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    public static SpliceCommandType fromInt(int value) {
        for(SpliceCommandType id : SpliceCommandType.values()) {
            if(id.value == value) {
                return id;
            }
        }
        return UNKNOWN;
    }
    
}
