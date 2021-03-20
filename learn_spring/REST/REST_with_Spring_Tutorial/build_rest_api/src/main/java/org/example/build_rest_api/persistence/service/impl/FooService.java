package org.example.build_rest_api.persistence.service.impl;

import java.util.List;

import com.google.common.collect.Lists;

import org.example.build_rest_api.persistence.dao.IFooDao;
import org.example.build_rest_api.persistence.model.Foo;
import org.example.build_rest_api.persistence.service.IFooService;
import org.example.build_rest_api.persistence.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FooService extends AbstractService<Foo> implements IFooService {

    @Autowired
    private IFooDao dao;

    public FooService() {
        super();
    }

    @Override
    protected PagingAndSortingRepository<Foo, Long> getDao() {
        return dao;
    }

    @Override
    public Page<Foo> findPaginated(Pageable pageable) {
        return dao.findAll(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Foo> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }
}
