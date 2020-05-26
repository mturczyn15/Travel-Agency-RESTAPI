package com.travelagency.travelagency.repository;

import com.travelagency.travelagency.domain.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface HotelRepository extends CrudRepository<Hotel, Long> {
    @Override
    List<Hotel> findAll();

    @Override
    Optional<Hotel> findById(Long id);

    @Override
    Hotel save(Hotel user);

    @Override
    void deleteById(Long id);
}
