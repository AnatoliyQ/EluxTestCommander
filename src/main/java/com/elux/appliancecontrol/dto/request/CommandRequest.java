package com.elux.appliancecontrol.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Value;


@ApiModel
@Value
public class CommandRequest {
    String applianceId;
    String command;

    @Builder
    public CommandRequest(@JsonProperty(value = "applianceId", required = true) String applianceId,
                          @JsonProperty(value = "command", required = true) String command) {
        this.applianceId = applianceId;
        this.command = command;
    }


}
