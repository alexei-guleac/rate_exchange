package com.example.schimb.rest.payload;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Cash update request. ")
public class CashUpdateResponse {

    @JsonProperty()
    @JsonAlias({"username"})
    @Column(name = "username", unique = true)
    @NotNull
    private String username;

    @JsonProperty()
    @JsonAlias({"currencyCode"})
    @ApiModelProperty(notes = "Currency code. ", required = true)
    @NonNull
    private String currencyCode;

    @Column(name = "amount")
    @JsonAlias({"amount"})
    @ApiModelProperty(notes = "Monetary amount")
    private Double amount;

    @Column(name = "performed_at")
    @JsonAlias({"date"})
    @ApiModelProperty(notes = "Cash update performed date")
    private Date performedAt;

}
