package uk.gov.ofwat.external.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DataFile entity.
 */
public class DataFileDTO implements Serializable {

    private Long id;

    private String name;

    private String location;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataFileDTO dataFileDTO = (DataFileDTO) o;
        if(dataFileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataFileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataFileDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
