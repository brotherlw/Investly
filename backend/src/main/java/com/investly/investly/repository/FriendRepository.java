package com.investly.investly.repository;

import com.investly.investly.model.FriendConnection;
import com.investly.investly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository <FriendConnection, String> {
    List<FriendConnection> findFriendByParty1OrParty2(User requester, User receiver);
}
