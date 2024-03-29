package fr.dawan.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.back.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("FROM User u WHERE u.email= :email")
	User findByEmail(@Param("email")String email);

}
