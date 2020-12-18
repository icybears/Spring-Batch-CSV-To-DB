package net.saber.springbatch.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.saber.springbatch.entity.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
