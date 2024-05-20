package com.asimkilic.courier.courier.dto;

import java.io.Serial;
import java.io.Serializable;

public record CourierDto(Long id,
                         String fullName,
                         String identityNumber) implements Serializable {

    @Serial
    private static final long serialVersionUID = 3456367915532024363L;

}
