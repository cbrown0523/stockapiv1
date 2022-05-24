package com.careerdevs.stockapi.repositories;

import com.careerdevs.stockapi.models.Overview;
import org.springframework.data.repository.CrudRepository;

public interface OverviewRepository extends CrudRepository<Overview, Long > {
}
