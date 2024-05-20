package com.asimkilic.courier.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetailDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1531295328189141652L;

    private String type;

    private String code;

    @JsonIgnore
    private String message;


    @JsonIgnore
    private String localizedMessage;


    @JsonProperty("message")
    public String innerMessage() {
        if (StringUtils.isNotBlank(localizedMessage)) {
            return localizedMessage;
        }
        return getMessage();
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

}
