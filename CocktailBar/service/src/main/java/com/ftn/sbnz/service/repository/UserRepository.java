package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
}
