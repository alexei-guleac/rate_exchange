package com.example.schimb.rest.payload;

import com.example.schimb.model.Currency;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Exchange rate general response. ")
public class ExchangeRateResponse {

    @JsonProperty()
    @JsonAlias({"currencyCode"})
    @ApiModelProperty(notes = "Currency code. ", required = true)
    @NonNull
    private Currency currencyCode;

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

    @JsonProperty()
    @JsonAlias({"dateRate"})
    @ApiModelProperty(notes = "Exchange rate refresh date. ", required = true)
    @NonNull
    private Date dateRate;

}
