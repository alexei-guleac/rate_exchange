package com.example.schimb.rest.payload;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Set exchange rate general request. ")
public class ExchangeRateSetRequest {

    @JsonProperty()
    @JsonAlias({"currencyCode"})
    @ApiModelProperty(notes = "Currency code. ", required = true)
    @NonNull
    private String currencyCode;

    @JsonProperty()
    @JsonAlias({"factor"})
    @ApiModelProperty(notes = "Currency factor. ", required = true)
    @NonNull
    private Integer factor;

    @JsonProperty()
    @JsonAlias({"rate"})
    @ApiModelProperty(notes = "Exchange rate. ", required = true)
    @NonNull
    private Double rate;

}
