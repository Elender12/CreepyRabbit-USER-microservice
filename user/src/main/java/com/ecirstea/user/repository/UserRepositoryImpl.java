package com.ecirstea.user.repository;



import com.ecirstea.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import java.util.List;
import java.util.UUID;


@Repository
public class UserRepositoryImpl implements UserRepository {

    private final MongoOperations mongoOperations;

    @Autowired
    public UserRepositoryImpl(MongoOperations mongoOperations ) {
        Assert.notNull(mongoOperations, "mongoOperations is not null");
        this.mongoOperations = mongoOperations;
    }

    @Override
    public User save(User user)
    {
        return this.mongoOperations.save(user);
    }

    @Override
    public void deleteById( UUID id ) {
        Query searchQuery = new Query(Criteria.where("id").is(id));
        this.mongoOperations.remove(searchQuery, User.class);
    }

    @Override
    public User findById(UUID id )
    {
        return this.mongoOperations.findById(id, User.class);
    }


    @Override
    public List<User> findAll() {
        return this.mongoOperations.findAll(User.class);
    }

    @Override
    public User findByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));

        return this.mongoOperations.findOne(query, User.class);
    }

    public boolean existsByColumn( UUID id, String column, String value ) {
        Query searchQuery;

        if( id == null ) {
            searchQuery = new Query(
                    Criteria.where(column).is(value)
            );
        }
        else {
            searchQuery = new Query(
                    Criteria.where("id").ne(id)
                            .and(column).is(value)
            );
        }
        User user = this.mongoOperations.findOne(searchQuery, User.class);
        return user != null;
    }
}
