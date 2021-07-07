package com.epam.esm.web;

import com.epam.esm.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAll();

    OrderDto getById(int id);
}
