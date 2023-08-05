package instagram.repository.user;

import instagram.api.search.response.SearchUserResponse;
import instagram.entity.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    @Query(value =
            "select new instagram.api.search.response.SearchUserResponse"+
                    "(u.id, u.profileImgUrl, u.username, u.nickname)"+
                    " from User u where u.nickname like %:keyword% or u.username like %:keyword%"
    )
    List<SearchUserResponse> findByNickname(@Param("keyword") String keyword, Pageable pageable);
}
