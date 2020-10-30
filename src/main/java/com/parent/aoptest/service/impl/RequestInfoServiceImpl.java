package com.parent.aoptest.service.impl;

import com.parent.aoptest.mapper.RequestInfoMapper;
import com.parent.aoptest.pojo.RequestInfo;
import com.parent.aoptest.service.RequestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestInfoServiceImpl implements RequestInfoService {

    @Autowired
    private RequestInfoMapper mapper;

    @Override
    public void saveInfo(RequestInfo requestInfo) {
        this.mapper.insertSelective(requestInfo);
    }
}
