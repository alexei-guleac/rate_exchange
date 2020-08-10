package com.example.schimb.model.exchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "currency")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
// CurrencyUnit already exists
public class CurrencyElement {
    @Id
    private long id;

    @NotNull
    private String code;

    @Override
    public String toString() {
        return "Currency {" +
                "id=" + id +
                ", code='" + code + '\'' +
                '}';
    }
}
