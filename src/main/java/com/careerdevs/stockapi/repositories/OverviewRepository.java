package com.careerdevs.stockapi.repositories;

import com.careerdevs.stockapi.models.Overview;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Transactional

public interface OverviewRepository extends JpaRepository<Overview, Long > {
   public Optional<Overview> findBySymbol(String symbol);
   public List<Overview> findByExchange(String exchange);
   public List<Overview> findByAssetType(String assetType);
   public List<Overview> findByCurrency(String currency);
   public List<Overview> findByCountry(String country);
   public List<Overview> findBySector(String sector);

   public List<Overview> deleteBySymbol(String symbol);
   public List<Overview> deleteByExchange(String exchange);
   public List<Overview> deleteByAssetType(String assetType);
   public List<Overview> deleteByCurrency(String currency);
   public List<Overview> deleteByCountry(String country);
   public List<Overview> deleteBySector(String sector);

}
