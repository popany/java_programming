package org.example.build_rest_api.persistence.dao;

import org.example.build_rest_api.persistence.model.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFooDao extends JpaRepository<Foo, Long> {
    
}
