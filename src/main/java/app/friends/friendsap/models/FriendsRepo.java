package app.friends.friendsap.models;

import org.springframework.data.repository.CrudRepository;

public interface FriendsRepo extends CrudRepository<Friend,Long> {
    Iterable<Friend> findAllByCreatedByOrderByRank(String createdBy);
}
