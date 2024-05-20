package com.asimkilic.courier.courier.dto;

import com.asimkilic.courier.common.annotation.ValidIdentityNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

public record CourierRequest(@Schema(name = "fullName", example = "Asım Kılıç") @NotBlank String fullName,
                             @Schema(name = "identityNumber", example = "40236654820, 78919541778, 41036784946")
                             @ValidIdentityNumber String identityNumber)
        implements Serializable {

    @Serial
    private static final long serialVersionUID = -4672212058490252775L;

}
