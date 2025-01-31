package tv.mediadistillery.libs.decoder.model;

import java.util.Objects;

/**
 * Created by andres.aguilar on 6/16/16.
 */
public class SpliceInsert {

    private int spliceEventID;
    private int spliceEventCancelIndicator;
    private int reserved1;
    private int outOfNetworkIndicator;
    private int programSpliceFlag;
    private SpliceTime sisp = new SpliceTime();
    private int durationFlag;
    private int spliceImmediateFlag;
    private BreakDuration breakDuration = new BreakDuration();
    private int reserved2;
    private int uniqueProgramID;
    private int availNum;
    private int availsExpected;

    public int getSpliceEventID() {
        return spliceEventID;
    }

    public void setSpliceEventID(int spliceEventID) {
        this.spliceEventID = spliceEventID;
    }

    public int getSpliceEventCancelIndicator() {
        return spliceEventCancelIndicator;
    }

    public void setSpliceEventCancelIndicator(int spliceEventCancelIndicator) {
        this.spliceEventCancelIndicator = spliceEventCancelIndicator;
    }

    public int getReserved1() {
        return reserved1;
    }

    public void setReserved1(int reserved1) {
        this.reserved1 = reserved1;
    }

    public int getOutOfNetworkIndicator() {
        return outOfNetworkIndicator;
    }

    public void setOutOfNetworkIndicator(int outOfNetworkIndicator) {
        this.outOfNetworkIndicator = outOfNetworkIndicator;
    }

    public int getProgramSpliceFlag() {
        return programSpliceFlag;
    }

    public void setProgramSpliceFlag(int programSpliceFlag) {
        this.programSpliceFlag = programSpliceFlag;
    }

    public SpliceTime getSisp() {
        return sisp;
    }

    public void setSisp(SpliceTime sisp) {
        this.sisp = sisp;
    }

    public int getDurationFlag() {
        return durationFlag;
    }

    public void setDurationFlag(int durationFlag) {
        this.durationFlag = durationFlag;
    }

    public int getSpliceImmediateFlag() {
        return spliceImmediateFlag;
    }

    public void setSpliceImmediateFlag(int spliceImmediateFlag) {
        this.spliceImmediateFlag = spliceImmediateFlag;
    }

    public BreakDuration getBreakDuration() {
        return breakDuration;
    }

    public void setBreakDuration(BreakDuration breakDuration) {
        this.breakDuration = breakDuration;
    }

    public int getReserved2() {
        return reserved2;
    }

    public void setReserved2(int reserved2) {
        this.reserved2 = reserved2;
    }

    public int getUniqueProgramID() {
        return uniqueProgramID;
    }

    public void setUniqueProgramID(int uniqueProgramID) {
        this.uniqueProgramID = uniqueProgramID;
    }

    public int getAvailNum() {
        return availNum;
    }

    public void setAvailNum(int availNum) {
        this.availNum = availNum;
    }

    public int getAvailsExpected() {
        return availsExpected;
    }

    public void setAvailsExpected(int availsExpected) {
        this.availsExpected = availsExpected;
    }

    @Override
    public String toString() {
        return "SpliceInsert{" + "spliceEventID=" + spliceEventID + ", spliceEventCancelIndicator=" + spliceEventCancelIndicator + ", reserved1=" + reserved1 + ", outOfNetworkIndicator=" + outOfNetworkIndicator + ", programSpliceFlag="
                + programSpliceFlag + ", sisp=" + sisp + ", durationFlag=" + durationFlag + ", spliceImmediateFlag=" + spliceImmediateFlag + ", breakDuration=" + breakDuration + ", reserved2=" + reserved2 + ", uniqueProgramID="
                + uniqueProgramID + ", availNum=" + availNum + ", availsExpected=" + availsExpected + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(availNum, availsExpected, breakDuration, durationFlag, outOfNetworkIndicator, programSpliceFlag, reserved1, reserved2, sisp, spliceEventCancelIndicator, spliceEventID, spliceImmediateFlag, uniqueProgramID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SpliceInsert other = (SpliceInsert) obj;
        return availNum == other.availNum && availsExpected == other.availsExpected && Objects.equals(breakDuration, other.breakDuration) && durationFlag == other.durationFlag && outOfNetworkIndicator == other.outOfNetworkIndicator
                && programSpliceFlag == other.programSpliceFlag && reserved1 == other.reserved1 && reserved2 == other.reserved2 && Objects.equals(sisp, other.sisp) && spliceEventCancelIndicator == other.spliceEventCancelIndicator
                && spliceEventID == other.spliceEventID && spliceImmediateFlag == other.spliceImmediateFlag && uniqueProgramID == other.uniqueProgramID;
    }

}
