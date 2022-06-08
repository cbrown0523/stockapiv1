package com.careerdevs.stockapi.repositories;

import com.careerdevs.stockapi.models.Overview;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OverviewRepository extends CrudRepository<Overview, Long > {
   public Optional<Overview> findBySymbol(String symbol);
   public List<Overview> findByExchange(String exchange);
   public List<Overview> findByAssetType(String assetType);
   public List<Overview> findByCurrency(String currency);
   public List<Overview> findByCountry(String country);
   public List<Overview> findBySector(String sector);

}
