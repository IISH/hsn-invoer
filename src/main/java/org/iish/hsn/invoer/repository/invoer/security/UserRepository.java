package org.iish.hsn.invoer.repository.invoer.security;

import org.iish.hsn.invoer.domain.invoer.security.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Integer> {
    User findByInlognaam(String inlognaam);
}
