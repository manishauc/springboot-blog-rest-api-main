package com.springboot.blog.repository;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.blog.entity.FollowUser;



@Repository
public interface FollowRepository extends JpaRepository<FollowUser, Long> {
	
	//@Query("select F.id from FollowUser F where F.followedBy=:fromUserId AND F.followedTo= :toUserId")
	@Query(value = "SELECT count(id) FROM follow_user where followed_by = ?1 AND followed_to = ?2", nativeQuery = true)
     Long CheckIfAlreadyFollowed(long fromUserId,long toUserId);
	
	@Query("select Count(F.id) from FollowUser F JOIN User U on F.followedBy=U.id")
	Long findAllByFollowedTo(long id);
	
	
	@Query(value = "select US.id from FollowUser F JOIN User U on F.followedBy=U.id JOIN User US on F.followedTo=US.id  WHERE U.id like ?1")
	List<Long> findFollowedById(long user_id);
	
	@Query(value = "select F.id from FollowUser F JOIN User U on F.followedBy=U.id JOIN User US on F.followedTo=US.id  WHERE U.id like ?1 AND US.id like ?2")
	Long CheckIfAlreadyFollowedId(long fromUserId,long toUserId);
	 	
	//@Query("select Count(F.id) from FollowUser F JOIN User U on F.followedBy=U.id")
	//Long findByFollowedByAndFollowedTo(long id);
	
//	
//	@Query("FROM FollowUser where followedBy = :fromUserId AND followedTo =:toUserId")
//	Optional<FollowUser> CheckIfAlreadyFollowedList(long fromUserId,long toUserId);
//	
//	@Query("select F.id from FollowUser F where F.followedBy.id=:followedBy")
//	Optional<FollowUser> findByFollowedByAndFollowedTo(long followedBy,long followedTo);
//	
//	@Query("select F.id from FollowUser F where F.followedBy.id=:followedBy")
//	Optional<FollowUser> findByFollowedBy(long followedBy);
	
	//Optional<FollowUser> findByFollowedByAndFollowedTo(long fromUserId, long toUserId);
}
