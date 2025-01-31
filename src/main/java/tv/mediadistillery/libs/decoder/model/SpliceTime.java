package tv.mediadistillery.libs.decoder.model;

import java.util.Objects;

/**
 * Created by andres.aguilar on 6/16/16.
 */
public class SpliceTime {

    private boolean timeSpecifiedFlag;
    private int reserved1;
    private long ptsTime;
    private int reserved2;

    public boolean getTimeSpecifiedFlag() {
        return timeSpecifiedFlag;
    }

    public void setTimeSpecifiedFlag(boolean timeSpecifiedFlag) {
        this.timeSpecifiedFlag = timeSpecifiedFlag;
    }

    public int getReserved1() {
        return reserved1;
    }

    public void setReserved1(int reserved1) {
        this.reserved1 = reserved1;
    }

    public long getPtsTime() {
        return ptsTime;
    }

    public void setPtsTime(long ptsTime) {
        this.ptsTime = ptsTime;
    }

    public int getReserved2() {
        return reserved2;
    }

    public void setReserved2(int reserved2) {
        this.reserved2 = reserved2;
    }
    

    @Override
    public int hashCode() {
        return Objects.hash(ptsTime, reserved1, reserved2, timeSpecifiedFlag);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SpliceTime other = (SpliceTime) obj;
        return ptsTime == other.ptsTime && reserved1 == other.reserved1 && reserved2 == other.reserved2 && timeSpecifiedFlag == other.timeSpecifiedFlag;
    }

    @Override
    public String toString() {
        return "SpliceTime{" + "timeSpecifiedFlag=" + timeSpecifiedFlag + ", reserved1=" + reserved1 + ", ptsTime=" + ptsTime + ", reserved2=" + reserved2 + '}';
    }
}
