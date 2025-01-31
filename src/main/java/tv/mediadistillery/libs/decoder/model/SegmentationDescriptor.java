package tv.mediadistillery.libs.decoder.model;

import java.util.Objects;

/**
 * Created by andres.aguilar on 6/16/16.
 */
public class SegmentationDescriptor {

    private int spliceDescriptorTag;
    private int descriptorLength;
    private int identifier;
    private int segmentationEventID;
    private int segmentationEventCancelIndicator;
    private int programSegmentationFlag;
    private int segmentationDurationFlag;
    private int deliveryNotRestricted;
    private int webDeliveryAllowedFlag;
    private int noRegionalBlackoutFlag;
    private int archiveAllowed;
    private int deviceRestriction;
    private long segmentationDuration;
    private long turnerIdentifier;
    private int segmentationUPIDtype;
    private int segmentationUPIDlength;
    private SegmentTypeId segmentationTypeID;
    private int segmentNum;
    private int segmentsExpected;

    public int getSpliceDescriptorTag() {
        return spliceDescriptorTag;
    }

    public void setSpliceDescriptorTag(int spliceDescriptorTag) {
        this.spliceDescriptorTag = spliceDescriptorTag;
    }

    public int getDescriptorLength() {
        return descriptorLength;
    }

    public void setDescriptorLength(int descriptorLength) {
        this.descriptorLength = descriptorLength;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public int getSegmentationEventID() {
        return segmentationEventID;
    }

    public void setSegmentationEventID(int segmentationEventID) {
        this.segmentationEventID = segmentationEventID;
    }

    public int getSegmentationEventCancelIndicator() {
        return segmentationEventCancelIndicator;
    }

    public void setSegmentationEventCancelIndicator(int segmentationEventCancelIndicator) {
        this.segmentationEventCancelIndicator = segmentationEventCancelIndicator;
    }

    public int getProgramSegmentationFlag() {
        return programSegmentationFlag;
    }

    public void setProgramSegmentationFlag(int programSegmentationFlag) {
        this.programSegmentationFlag = programSegmentationFlag;
    }

    public int getSegmentationDurationFlag() {
        return segmentationDurationFlag;
    }

    public void setSegmentationDurationFlag(int segmentationDurationFlag) {
        this.segmentationDurationFlag = segmentationDurationFlag;
    }

    public int getDeliveryNotRestricted() {
        return deliveryNotRestricted;
    }

    public void setDeliveryNotRestricted(int deliveryNotRestricted) {
        this.deliveryNotRestricted = deliveryNotRestricted;
    }

    public int getWebDeliveryAllowedFlag() {
        return webDeliveryAllowedFlag;
    }

    public void setWebDeliveryAllowedFlag(int webDeliveryAllowedFlag) {
        this.webDeliveryAllowedFlag = webDeliveryAllowedFlag;
    }

    public int getNoRegionalBlackoutFlag() {
        return noRegionalBlackoutFlag;
    }

    public void setNoRegionalBlackoutFlag(int noRegionalBlackoutFlag) {
        this.noRegionalBlackoutFlag = noRegionalBlackoutFlag;
    }

    public int getArchiveAllowed() {
        return archiveAllowed;
    }

    public void setArchiveAllowed(int archiveAllowed) {
        this.archiveAllowed = archiveAllowed;
    }

    public int getDeviceRestriction() {
        return deviceRestriction;
    }

    public void setDeviceRestriction(int deviceRestriction) {
        this.deviceRestriction = deviceRestriction;
    }

    public long getSegmentationDuration() {
        return segmentationDuration;
    }

    public void setSegmentationDuration(long segmentationDuration) {
        this.segmentationDuration = segmentationDuration;
    }

    public long getTurnerIdentifier() {
        return turnerIdentifier;
    }

    public void setTurnerIdentifier(long turnerIdentifier) {
        this.turnerIdentifier = turnerIdentifier;
    }

    public int getSegmentationUPIDtype() {
        return segmentationUPIDtype;
    }

    public void setSegmentationUPIDtype(int segmentationUPIDtype) {
        this.segmentationUPIDtype = segmentationUPIDtype;
    }

    public int getSegmentationUPIDlength() {
        return segmentationUPIDlength;
    }

    public void setSegmentationUPIDlength(int segmentationUPIDlength) {
        this.segmentationUPIDlength = segmentationUPIDlength;
    }

    public SegmentTypeId getSegmentationTypeID() {
        return segmentationTypeID;
    }

    public void setSegmentationTypeID(SegmentTypeId segmentationTypeID) {
        this.segmentationTypeID = segmentationTypeID;
    }

    public int getSegmentNum() {
        return segmentNum;
    }

    public void setSegmentNum(int segmentNum) {
        this.segmentNum = segmentNum;
    }

    public int getSegmentsExpected() {
        return segmentsExpected;
    }

    public void setSegmentsExpected(int segmentsExpected) {
        this.segmentsExpected = segmentsExpected;
    }

    @Override
    public int hashCode() {
        return Objects.hash(archiveAllowed, deliveryNotRestricted, descriptorLength, deviceRestriction, identifier, noRegionalBlackoutFlag, programSegmentationFlag, segmentNum, segmentationDuration, segmentationDurationFlag,
                segmentationEventCancelIndicator, segmentationEventID, segmentationTypeID, segmentationUPIDlength, segmentationUPIDtype, segmentsExpected, spliceDescriptorTag, turnerIdentifier, webDeliveryAllowedFlag);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SegmentationDescriptor other = (SegmentationDescriptor) obj;
        return archiveAllowed == other.archiveAllowed && deliveryNotRestricted == other.deliveryNotRestricted && descriptorLength == other.descriptorLength && deviceRestriction == other.deviceRestriction && identifier == other.identifier
                && noRegionalBlackoutFlag == other.noRegionalBlackoutFlag && programSegmentationFlag == other.programSegmentationFlag && segmentNum == other.segmentNum
                && segmentationDuration == other.segmentationDuration && segmentationDurationFlag == other.segmentationDurationFlag && segmentationEventCancelIndicator == other.segmentationEventCancelIndicator
                && segmentationEventID == other.segmentationEventID && segmentationTypeID == other.segmentationTypeID && segmentationUPIDlength == other.segmentationUPIDlength && segmentationUPIDtype == other.segmentationUPIDtype
                && segmentsExpected == other.segmentsExpected && spliceDescriptorTag == other.spliceDescriptorTag && turnerIdentifier == other.turnerIdentifier && webDeliveryAllowedFlag == other.webDeliveryAllowedFlag;
    }

  
}
