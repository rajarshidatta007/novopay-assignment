package com.assignment.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * A DTO for the {@link com.assignment.domain.PassBook} entity.
 */
@ApiModel(description = "not an ignored comment")
public class PassBookDTO implements Serializable {
    
    private Long id;

    /**
     * A relationship
     */
    @ApiModelProperty(value = "A relationship")
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PassBookDTO)) {
            return false;
        }

        return id != null && id.equals(((PassBookDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PassBookDTO{" +
            "id=" + getId() +
            "}";
    }
}
