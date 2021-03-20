package org.example.build_rest_api.persistence.service;

import org.example.build_rest_api.persistence.IOperations;
import org.example.build_rest_api.persistence.model.Foo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFooService extends IOperations<Foo> {
    Page<Foo> findPaginated(Pageable pageable);
}
