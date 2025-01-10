package com.nirmalks.bookstore.address.repository;

import com.nirmalks.bookstore.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
