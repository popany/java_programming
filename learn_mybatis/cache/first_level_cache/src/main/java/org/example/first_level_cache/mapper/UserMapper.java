package org.example.first_level_cache.mapper;

import org.example.first_level_cache.entity.User;

public interface UserMapper {
    User selectByIdWithCache(Integer id);
    User selectByIdWithoutCache(Integer id);
}
