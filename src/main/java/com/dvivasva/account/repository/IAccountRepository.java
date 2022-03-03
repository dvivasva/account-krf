package com.dvivasva.account.repository;

import com.dvivasva.account.model.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends ReactiveMongoRepository<Account, String> {

}
