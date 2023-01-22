package org.example.repository;

import org.example.model.User;
import org.springframework.data.jpa.repository.Query;




public interface UserJpaRepository extends BaseRepository<User, Integer> {
    User findByUserName(String userName);
    User findById(int id);
    User findByEmail(String email);
    @Query("select u from User u left join fetch u.topics where u.id = ?1")
    User getUserByIdWithTopic(int id);
}
