package com.example.schimb.model.exchange;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;


/**
 * Exchange rate operation class
 */
@Entity(name = "exchange_operation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@ApiModel(description = "Exchange operation model")
public class ExchangeOperation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency")
    @ApiModelProperty(notes = "Exchange currency")
    private Currency currency;

    @Column(name = "rate")
    @ApiModelProperty(notes = "Currency exchange rate")
    private Double rate;

    @Column(name = "amount_received")
    @ApiModelProperty(notes = "Amount received")
    private Double amountReceived;

    @Column(name = "issued_amount")
    @ApiModelProperty(notes = "Amount issued")
    private Double issuedAmount;

    @Column(name = "performed_at")
    @ApiModelProperty(notes = "Currency exchange operation performed date")
    private Date performedAt;

}
