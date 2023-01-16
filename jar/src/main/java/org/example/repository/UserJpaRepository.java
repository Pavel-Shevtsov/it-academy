package org.example.repository;

import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends JpaRepository<User,Integer> {
    User findByUserName(String userName);
    User findById(int id);
    @Query("SELECT u from User u left join fetch u.topics where u.id = 1?")
    User getUserByIdWithTopic(Integer id);
}
