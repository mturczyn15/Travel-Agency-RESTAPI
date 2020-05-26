package com.travelagency.travelagency.repository;

import com.travelagency.travelagency.domain.TravelAgency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface TravelAgencyRepository extends CrudRepository<TravelAgency, Long> {
    @Override
    List<TravelAgency> findAll();

    @Override
    Optional<TravelAgency> findById(Long id);

    @Override
    TravelAgency save(TravelAgency user);

    @Override
    void deleteById(Long id);
}
