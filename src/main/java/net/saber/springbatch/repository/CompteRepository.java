package net.saber.springbatch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.saber.springbatch.entity.Compte;

@Repository
public interface CompteRepository extends CrudRepository<Compte, Long>{

}
