package com.dvivasva.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDto {
    private String id;
    private String number;
    private double availableBalance;
}
