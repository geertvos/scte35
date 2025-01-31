package tv.mediadistillery.libs.decoder.model;

import java.util.Objects;

/**
 * Created by andres.aguilar on 6/16/16.
 */
public class BreakDuration {

    private boolean autoReturn;
    private long duration;

    public boolean getAutoReturn() {
        return autoReturn;
    }

    public void setAutoReturn(boolean autoReturn) {
        this.autoReturn = autoReturn;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(autoReturn, duration);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BreakDuration other = (BreakDuration) obj;
        return autoReturn == other.autoReturn && duration == other.duration;
    }

}
