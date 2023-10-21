package com.investly.investly.service;

import com.investly.investly.model.FriendConnection;
import com.investly.investly.model.User;
import com.investly.investly.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendConnectionService {
    private final FriendRepository friendRepository;

    public void createFriendConnection(FriendConnection friendConnection) {
        friendRepository.save(friendConnection);
    }

    public List<FriendConnection> listFriendConnections(User user){
        return friendRepository.findFriendByParty1OrParty2(user, user);
    }
}