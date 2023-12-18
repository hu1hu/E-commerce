package com.ecommerce.service.impl;

import com.ecommerce.mapper.HistoryMapper;
import com.ecommerce.model.entity.History;
import com.ecommerce.model.vo.Res2History;
import com.ecommerce.model.vo.SimpleProduct;
import com.ecommerce.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryMapper historyMapper;

    @Override
    public int add(String phoneNumber, Integer id) {
        historyMapper.add(phoneNumber,id, LocalDateTime.now());
        return 0;
    }

    @Override
    public List<Res2History> getList(String phoneNumber) {

        return historyMapper.getList(phoneNumber);
    }
}
