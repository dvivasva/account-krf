package com.dvivasva.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("account-krf")
public class Account {

	@Id
	private String id;
	private String number;
	private double availableBalance;

}
