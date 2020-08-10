package com.example.schimb.model.exchange;

import com.example.schimb.model.users.User;
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
 * Cash model class
 */
@Entity(name = "cash")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@ApiModel(description = "Cash model")
public class Cash {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ApiModelProperty(notes = "User data")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id")
    @ApiModelProperty(notes = "Exchange currency")
    private CurrencyElement currency;

    @Column(name = "amount")
    @ApiModelProperty(notes = "Monetary amount")
    private Double amount;

    @Column(name = "performed_at")
    @ApiModelProperty(notes = "Cash update performed date")
    private Date performedAt;

}
