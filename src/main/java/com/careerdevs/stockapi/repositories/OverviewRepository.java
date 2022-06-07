package com.careerdevs.stockapi.repositories;

import com.careerdevs.stockapi.models.Overview;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OverviewRepository extends CrudRepository<Overview, Long > {
   public Optional<Overview> findBySymbol(String symbol);
   public List<Overview> findByExchange(String exchange);
}
