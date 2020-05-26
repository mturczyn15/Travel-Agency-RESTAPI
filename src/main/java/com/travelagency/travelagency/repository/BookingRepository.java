package com.travelagency.travelagency.repository;

import com.travelagency.travelagency.domain.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {
    @Override
    List<Booking> findAll();

    @Override
    Booking save(Booking address);

    @Override
    Optional<Booking> findById(Long id);

    @Override
    void deleteById(Long id);


}

