package com.asimkilic.courier.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetailDtoHolder implements Serializable {


    @Serial
    private static final long serialVersionUID = 7988882848713231428L;

    private String message;

    private List<ErrorDetailDto> details;
}
