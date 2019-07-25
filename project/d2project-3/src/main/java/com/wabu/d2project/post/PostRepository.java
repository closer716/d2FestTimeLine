package com.wabu.d2project.post;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

	public List<Post> findByUserId(String userId);
    

    //db.post.find().limit(2);
    @Query()
    List<PostDto> findLast2ByUserId(String userId);
    
    @Query("{'userId':{'$in':?0},'date':{'$lt':?1,'$gt':?2}}")
    List<PostDto> findByUserIdAndDateBetween(List<String> userId, Date to, Date from);
    
    @Query("{'date':{'$gt':?0}}")
    List<PostDto> findByDateGreaterThan(Date from);
    
    @Query("{'userId': ?0}")
    void deleteByUserId(String userId);
     
    @DeleteQuery("{'_id': ?0}")
    void deleteBy_id(ObjectId _id);
}