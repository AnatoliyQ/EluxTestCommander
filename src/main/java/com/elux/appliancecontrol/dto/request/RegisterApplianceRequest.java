package com.elux.appliancecontrol.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Value;

@ApiModel
@Value
public class RegisterApplianceRequest {
    String applianceId;

    String applianceType;

    public RegisterApplianceRequest(@JsonProperty(value = "applianceId", required = true) String applianceId,
                                    @JsonProperty(value = "applianceType", required = true) String applianceType) {
        this.applianceId = applianceId;
        this.applianceType = applianceType;

    }
}
