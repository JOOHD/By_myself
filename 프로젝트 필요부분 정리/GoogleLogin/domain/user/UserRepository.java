package domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * User의 CRUD를 위한 UserRepository를 생성한다.
 */

public interface UserRepository extends JpaRepository<User,Long> {

    // findByEmail 소셜 로그인으로 반환되는 값 중에서 email을 통해 이미 생성된 사용자인지 첫 회원인지 판단
    Optional<User> findByEmail(String email);
    
}
