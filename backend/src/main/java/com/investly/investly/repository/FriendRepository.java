package com.investly.investly.repository;

import com.investly.investly.model.FriendRequest;
import com.investly.investly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository <FriendRequest, String> {
    List<FriendRequest> findFriendRequestByReceiverOrRequester(User requester, User receiver);
}
