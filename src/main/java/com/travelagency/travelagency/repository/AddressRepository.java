package com.travelagency.travelagency.repository;

import com.travelagency.travelagency.domain.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
    @Override
    List<Address> findAll();

    @Override
    Address save(Address address);

    @Override
    Optional<Address> findById(Long id);

    @Override
    void deleteById(Long id);


}
