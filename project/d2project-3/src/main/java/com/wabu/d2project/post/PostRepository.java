package com.wabu.d2project.post;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    public List<Post> findByUserId(String userId);
    
    @Query("{'userId':{'$in':?0},'date':{'$lt':?1,'$gt':?2}}")
    List<Post> findByUserIdAndDateBetween(List<String> userId, Date to, Date from);
    
    @Query("{'date':{'$gt':?0}}")
    List<Post> findByDateGreaterThan(Date from);
}