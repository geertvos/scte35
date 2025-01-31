package tv.mediadistillery.libs.decoder.model;

import java.util.Objects;

/**
 * Created by andres.aguilar on 6/16/16.
 */
public class SpliceInfoSection {

    private int tableID;
    private int sectionSyntaxIndicator;
    private int privateIndicator;
    private int reserved1;
    private int sectionLength;
    private int protocolVersion;
    private int encryptedPacket;
    private int encryptionAlgorithm;
    private long ptsAdjustment;
    private int cwIndex;
    private int tier;
    private int spliceCommandLength;
    private int spliceCommandType;
    private int descriptorLoopLength;
    private int alignmentStuffing;
    private int eCRC32;
    private int CRC32;
    private SpliceInsert spliceInsert;

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public int getSectionSyntaxIndicator() {
        return sectionSyntaxIndicator;
    }

    public void setSectionSyntaxIndicator(int sectionSyntaxIndicator) {
        this.sectionSyntaxIndicator = sectionSyntaxIndicator;
    }

    public int getPrivateIndicator() {
        return privateIndicator;
    }

    public void setPrivateIndicator(int privateIndicator) {
        this.privateIndicator = privateIndicator;
    }

    public int getReserved1() {
        return reserved1;
    }

    public void setReserved1(int reserved1) {
        this.reserved1 = reserved1;
    }

    public int getSectionLength() {
        return sectionLength;
    }

    public void setSectionLength(int sectionLength) {
        this.sectionLength = sectionLength;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public int getEncryptedPacket() {
        return encryptedPacket;
    }

    public void setEncryptedPacket(int encryptedPacket) {
        this.encryptedPacket = encryptedPacket;
    }

    public int getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public void setEncryptionAlgorithm(int encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    public long getPtsAdjustment() {
        return ptsAdjustment;
    }

    public void setPtsAdjustment(long ptsAdjustment) {
        this.ptsAdjustment = ptsAdjustment;
    }

    public int getCwIndex() {
        return cwIndex;
    }

    public void setCwIndex(int cwIndex) {
        this.cwIndex = cwIndex;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public int getSpliceCommandLength() {
        return spliceCommandLength;
    }

    public void setSpliceCommandLength(int spliceCommandLength) {
        this.spliceCommandLength = spliceCommandLength;
    }

    public int getSpliceCommandType() {
        return spliceCommandType;
    }

    public void setSpliceCommandType(int spliceCommandType) {
        this.spliceCommandType = spliceCommandType;
    }

    public int getDescriptorLoopLength() {
        return descriptorLoopLength;
    }

    public void setDescriptorLoopLength(int descriptorLoopLength) {
        this.descriptorLoopLength = descriptorLoopLength;
    }

    public int getAlignmentStuffing() {
        return alignmentStuffing;
    }

    public void setAlignmentStuffing(int alignmentStuffing) {
        this.alignmentStuffing = alignmentStuffing;
    }

    public int geteCRC32() {
        return eCRC32;
    }

    public void seteCRC32(int eCRC32) {
        this.eCRC32 = eCRC32;
    }

    public int getCRC32() {
        return CRC32;
    }

    public void setCRC32(int cRC32) {
        CRC32 = cRC32;
    }

    public SpliceInsert getSpliceInsert() {
        return spliceInsert;
    }

    public void setSpliceInsert(SpliceInsert spliceInsert) {
        this.spliceInsert = spliceInsert;
    }

    @Override
    public String toString() {
        return "SpliceInfoSection{" + "tableID=" + tableID + ", sectionSyntaxIndicator=" + sectionSyntaxIndicator + ", privateIndicator=" + privateIndicator + ", reserved1=" + reserved1 + ", sectionLength=" + sectionLength
                + ", protocolVersion=" + protocolVersion + ", encryptedPacket=" + encryptedPacket + ", encryptionAlgorithm=" + encryptionAlgorithm + ", ptsAdjustment=" + ptsAdjustment + ", cwIndex=" + cwIndex + ", tier=" + tier
                + ", spliceCommandLength=" + spliceCommandLength + ", spliceCommandType=" + spliceCommandType + ", descriptorLoopLength=" + descriptorLoopLength + ", alignmentStuffing=" + alignmentStuffing + ", eCRC32=" + eCRC32
                + ", CRC32=" + CRC32 + ", spliceInsert=" + spliceInsert + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(CRC32, alignmentStuffing, cwIndex, descriptorLoopLength, eCRC32, encryptedPacket, encryptionAlgorithm, privateIndicator, protocolVersion, ptsAdjustment, reserved1, sectionLength, sectionSyntaxIndicator,
                spliceCommandLength, spliceCommandType, spliceInsert, tableID, tier);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SpliceInfoSection other = (SpliceInfoSection) obj;
        return CRC32 == other.CRC32 && alignmentStuffing == other.alignmentStuffing && cwIndex == other.cwIndex && descriptorLoopLength == other.descriptorLoopLength && eCRC32 == other.eCRC32 && encryptedPacket == other.encryptedPacket
                && encryptionAlgorithm == other.encryptionAlgorithm && privateIndicator == other.privateIndicator && protocolVersion == other.protocolVersion && ptsAdjustment == other.ptsAdjustment && reserved1 == other.reserved1
                && sectionLength == other.sectionLength && sectionSyntaxIndicator == other.sectionSyntaxIndicator && spliceCommandLength == other.spliceCommandLength && spliceCommandType == other.spliceCommandType
                && Objects.equals(spliceInsert, other.spliceInsert) && tableID == other.tableID && tier == other.tier;
    }
    
    
}
