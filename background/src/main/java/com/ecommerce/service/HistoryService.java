package com.ecommerce.service;

import com.ecommerce.model.vo.Res2History;
import com.ecommerce.model.vo.SimpleProduct;

import java.util.List;

public interface HistoryService {

    int add(String phoneNumber, Integer id);

    List<Res2History> getList(String phoneNumber);
}
