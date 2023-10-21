package com.investly.investly.service;

import com.investly.investly.model.*;
import com.investly.investly.model.enums.FriendRequestState;
import com.investly.investly.repository.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendRequestService {
    private final FriendConnectionService friendConnectionService;
    private final FriendRequestRepository friendRequestRepository;
    private final UserService userService;

    public FriendRequest createFriendRequest(CreateFriendRequestDto createFriendRequestDto, User requester) {
        var requestedUser = userService.getUser(createFriendRequestDto.getUsername());

        if (requestedUser == null) {
            return null;
        }

        var friendRequest = new FriendRequest();
        friendRequest.setRequester(requester);
        friendRequest.setReceiver(requestedUser);
        friendRequest.setState(FriendRequestState.PENDING);

        return friendRequestRepository.save(friendRequest);
    }

    public FriendRequest getFriendRequest(String requestId) {
        return friendRequestRepository.findById(requestId).orElse(null);
    }

    public List<FriendRequest> listAssociatedRequests(User user){
        return friendRequestRepository.findFriendRequestByReceiverOrRequester(user, user);
    }

    public FriendRequest answerRequest(String id, UpdateFriendRequestDto updateFriendRequestDto, User user) {
        var savedRequest = getFriendRequest(id);

        if(savedRequest.getState() != FriendRequestState.PENDING) {
            return null;
        }

        if (savedRequest.getRequester().getId().equals(user.getId())) {
            return null;
        }

        savedRequest.setState(updateFriendRequestDto.getState());

        if (updateFriendRequestDto.getState() == FriendRequestState.ACCEPTED) {
            var friendConnection = new FriendConnection();
            friendConnection.setParty1(user);
            friendConnection.setParty2(savedRequest.getRequester());

            friendConnectionService.createFriendConnection(friendConnection);
        }

        return friendRequestRepository.save(savedRequest);
    }
}