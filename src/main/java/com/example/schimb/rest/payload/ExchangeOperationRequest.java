package com.example.schimb.rest.payload;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Exchange operation request. ")
public class ExchangeOperationRequest {

    @JsonProperty()
    @JsonAlias({"currencyCode"})
    @ApiModelProperty(notes = "Currency code. ", required = true)
    @NonNull
    private String currencyCode;

    @Column(name = "rate")
    @ApiModelProperty(notes = "Currency exchange rate")
    private Double rate;

    @Column(name = "amount_received")
    @ApiModelProperty(notes = "Amount received")
    private Double amountReceived;

    @Column(name = "issued_amount")
    @ApiModelProperty(notes = "Amount issued")
    private Double issuedAmount;

}
