package uk.gov.ofwat.external.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DataInput entity.
 */
public class DataInputDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private String guidance;

    private LocalDate defaultDeadline;

    @NotNull
    private Long orderIndex;

    @NotNull
    private String fileName;

    @NotNull
    private String fileLocation;

    private Long statusId;

    private String statusStatus;

    private Long dataBundleId;

    private String dataBundleName;

    private Long ownerId;

    private String ownerFirstName;

    private String ownerLastName;

    private Long reviewerId;

    private String reviewerFirstName;

    private String reviewerLastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGuidance() {
        return guidance;
    }

    public void setGuidance(String guidance) {
        this.guidance = guidance;
    }

    public LocalDate getDefaultDeadline() {
        return defaultDeadline;
    }

    public void setDefaultDeadline(LocalDate defaultDeadline) {
        this.defaultDeadline = defaultDeadline;
    }

    public Long getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Long orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long publishingStatusId) {
        this.statusId = publishingStatusId;
    }

    public String getStatusStatus() {
        return statusStatus;
    }

    public void setStatusStatus(String publishingStatusStatus) {
        this.statusStatus = publishingStatusStatus;
    }

    public Long getDataBundleId() {
        return dataBundleId;
    }

    public void setDataBundleId(Long dataBundleId) {
        this.dataBundleId = dataBundleId;
    }

    public String getDataBundleName() {
        return dataBundleName;
    }

    public void setDataBundleName(String dataBundleName) {
        this.dataBundleName = dataBundleName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long userId) {
        this.ownerId = userId;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String userFirstName) {
        this.ownerFirstName = userFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String userLastName) {
        this.ownerLastName = userLastName;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long userId) {
        this.reviewerId = userId;
    }

    public String getReviewerFirstName() {
        return reviewerFirstName;
    }

    public void setReviewerFirstName(String userFirstName) {
        this.reviewerFirstName = userFirstName;
    }

    public String getReviewerLastName() {
        return reviewerLastName;
    }

    public void setReviewerLastName(String userLastName) {
        this.reviewerLastName = userLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataInputDTO)) return false;

        DataInputDTO that = (DataInputDTO) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getGuidance() != null ? !getGuidance().equals(that.getGuidance()) : that.getGuidance() != null)
            return false;
        if (getDefaultDeadline() != null ? !getDefaultDeadline().equals(that.getDefaultDeadline()) : that.getDefaultDeadline() != null)
            return false;
        if (getOrderIndex() != null ? !getOrderIndex().equals(that.getOrderIndex()) : that.getOrderIndex() != null)
            return false;
        if (getFileName() != null ? !getFileName().equals(that.getFileName()) : that.getFileName() != null)
            return false;
        if (getFileLocation() != null ? !getFileLocation().equals(that.getFileLocation()) : that.getFileLocation() != null)
            return false;
        if (getStatusId() != null ? !getStatusId().equals(that.getStatusId()) : that.getStatusId() != null)
            return false;
        if (getStatusStatus() != null ? !getStatusStatus().equals(that.getStatusStatus()) : that.getStatusStatus() != null)
            return false;
        if (getDataBundleId() != null ? !getDataBundleId().equals(that.getDataBundleId()) : that.getDataBundleId() != null)
            return false;
        if (getDataBundleName() != null ? !getDataBundleName().equals(that.getDataBundleName()) : that.getDataBundleName() != null)
            return false;
        if (getOwnerId() != null ? !getOwnerId().equals(that.getOwnerId()) : that.getOwnerId() != null) return false;
        if (getOwnerFirstName() != null ? !getOwnerFirstName().equals(that.getOwnerFirstName()) : that.getOwnerFirstName() != null)
            return false;
        if (getOwnerLastName() != null ? !getOwnerLastName().equals(that.getOwnerLastName()) : that.getOwnerLastName() != null)
            return false;
        if (getReviewerId() != null ? !getReviewerId().equals(that.getReviewerId()) : that.getReviewerId() != null)
            return false;
        if (getReviewerFirstName() != null ? !getReviewerFirstName().equals(that.getReviewerFirstName()) : that.getReviewerFirstName() != null)
            return false;
        return getReviewerLastName() != null ? getReviewerLastName().equals(that.getReviewerLastName()) : that.getReviewerLastName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getGuidance() != null ? getGuidance().hashCode() : 0);
        result = 31 * result + (getDefaultDeadline() != null ? getDefaultDeadline().hashCode() : 0);
        result = 31 * result + (getOrderIndex() != null ? getOrderIndex().hashCode() : 0);
        result = 31 * result + (getFileName() != null ? getFileName().hashCode() : 0);
        result = 31 * result + (getFileLocation() != null ? getFileLocation().hashCode() : 0);
        result = 31 * result + (getStatusId() != null ? getStatusId().hashCode() : 0);
        result = 31 * result + (getStatusStatus() != null ? getStatusStatus().hashCode() : 0);
        result = 31 * result + (getDataBundleId() != null ? getDataBundleId().hashCode() : 0);
        result = 31 * result + (getDataBundleName() != null ? getDataBundleName().hashCode() : 0);
        result = 31 * result + (getOwnerId() != null ? getOwnerId().hashCode() : 0);
        result = 31 * result + (getOwnerFirstName() != null ? getOwnerFirstName().hashCode() : 0);
        result = 31 * result + (getOwnerLastName() != null ? getOwnerLastName().hashCode() : 0);
        result = 31 * result + (getReviewerId() != null ? getReviewerId().hashCode() : 0);
        result = 31 * result + (getReviewerFirstName() != null ? getReviewerFirstName().hashCode() : 0);
        result = 31 * result + (getReviewerLastName() != null ? getReviewerLastName().hashCode() : 0);
        return result;
    }
}
