package com.wabu.d2project.post;

import java.util.ArrayList;
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
    
    @Query(value="{'_id':{'$in':?0}}", sort="{date:-1}")
    List<PostDto> findBy_id(List<String> _id);
    
    @Query("{'userId': ?0}")
    void deleteByUserId(String userId);
     
    @DeleteQuery("{'_id': ?0}")
    List<Post> findBy_id(ObjectId _id);

}