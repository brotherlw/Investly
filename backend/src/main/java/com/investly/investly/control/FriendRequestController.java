//package com.investly.investly.control;
//
//import com.investly.investly.model.CreateFriendRequestDto;
//import com.investly.investly.model.FriendRequest;
//import com.investly.investly.service.FriendReqService;
//import com.investly.investly.service.SessionService;
//import com.investly.investly.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("friend-requests")
//@RequiredArgsConstructor
//public class FriendRequestController {
//    private final FriendReqService friendReqService;
//    private final SessionService sessionService;
//    private final UserService userService;
//
//
//    @PostMapping
//    public ResponseEntity<FriendRequest> createRequest(@RequestBody CreateFriendRequestDto createRequestDto,
//                                                       @RequestHeader String authorization){
//        var session = sessionService.getSession(authorization);
//
//        if (session == null) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//        if (userService.userExists(createRequestDto.getUsername())){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        var user = session.getUser();
//
//        if (createRequestDto.getUsername().equals(user.getUsername())) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<>(friendReqService.createFriendRequest())
//    }
//}
