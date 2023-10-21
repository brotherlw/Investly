package com.investly.investly.service;

import com.investly.investly.model.CreateFriendRequestDto;
import com.investly.investly.model.CreateUserDto;
import com.investly.investly.model.FriendRequest;
import com.investly.investly.model.User;
import com.investly.investly.model.enums.FriendRequestState;
import com.investly.investly.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendReqService {

    private final UserService userService;
    private final FriendRepository friendRepository;

    public FriendRequest createFriendRequest(CreateFriendRequestDto createFriendRequestDto, User requester) {
        var requestedUser = userService.getUser(createFriendRequestDto.getUsername());
        var friendRequest = new FriendRequest();

        if (requestedUser == null) {
            return null;
        }
        friendRequest.setRequester(requester);
        friendRequest.setReceiver(requestedUser);
        friendRequest.setState(FriendRequestState.WAITING);
        return friendRepository.save(friendRequest);
    }
    public boolean requestExists(String requestId){ return friendRepository.existsById(requestId); }

    public FriendRequest getFriendRequest(String requestId) {
        return friendRepository.findById(requestId).orElse(null);
    }

    public List<FriendRequest> listRequest(User user){
        return friendRepository.findFriendRequestByReceiverOrRequester(user, user);
    }

    public FriendRequest answerRequest(FriendRequest friendRequest){
        var savedRequest = getFriendRequest(friendRequest.getId());

        if(savedRequest.getState() != FriendRequestState.WAITING){
            return null;
        }

        savedRequest.setState(friendRequest.getState());

        return friendRepository.save(savedRequest);
    }
}