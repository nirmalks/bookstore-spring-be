package com.nirmalks.bookstore.address.mapper;

import com.nirmalks.bookstore.address.Address;
import com.nirmalks.bookstore.order.api.AddressRequest;

public class AddressMapper {
    public static Address toEntity(AddressRequest request) {
        Address address = new Address();

        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPinCode(request.getPinCode());
        address.setDefault(request.isDefault());
        address.setAddress(request.getAddress());

        return address;
    }
}
