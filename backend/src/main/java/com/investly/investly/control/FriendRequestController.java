package com.investly.investly.control;

import com.investly.investly.model.CreateFriendRequestDto;
import com.investly.investly.model.FriendRequest;
import com.investly.investly.model.UpdateFriendRequestDto;
import com.investly.investly.model.enums.FriendRequestState;
import com.investly.investly.service.FriendRequestService;
import com.investly.investly.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("friend-requests")
@RequiredArgsConstructor
public class FriendRequestController {
    private final FriendRequestService friendRequestService;
    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<FriendRequest> createRequest(@RequestBody CreateFriendRequestDto createRequestDto,
                                                       @RequestHeader String authorization){
        var session = sessionService.getSession(authorization);

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var user = session.getUser();

        if (createRequestDto.getUsername().equals(user.getUsername())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var friendRequest = friendRequestService.createFriendRequest(createRequestDto, user);

        if (friendRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(friendRequest, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FriendRequest>> listRequests(@RequestHeader String authorization) {
        var session = sessionService.getSession(authorization);

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(friendRequestService.listAssociatedRequests(session.getUser()), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<FriendRequest> answerRequest(@RequestHeader String authorization, @PathVariable String id,
                                                       @RequestBody UpdateFriendRequestDto updateFriendRequestDto) {
        var session = sessionService.getSession(authorization);

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (updateFriendRequestDto.getState() == null || updateFriendRequestDto.getState() == FriendRequestState.PENDING) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var storedChallenge = friendRequestService.answerRequest(id, updateFriendRequestDto, session.getUser());

        if (storedChallenge == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(storedChallenge, HttpStatus.OK);
    }
}