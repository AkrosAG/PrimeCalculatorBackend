/*
 * Copyright: (c) 2017 AKROS AG
 */
package ch.akros.cc.primecalculator.repo;

import org.springframework.data.repository.CrudRepository;

import ch.akros.cc.primecalculator.model.Calculation;

/**
 * Crud-Repository to save the data in the database.
 */
public interface CalculationRepository extends CrudRepository<Calculation, Long> {

   Calculation findByPrimeNumber(long primeNumber);
}
