package io.bpmnrepo.backend.user.api.resource;


import io.bpmnrepo.backend.shared.mapper.Mapper;
import io.bpmnrepo.backend.user.api.transport.UserEmailTO;
import io.bpmnrepo.backend.user.api.transport.UserInfoTO;
import io.bpmnrepo.backend.user.api.transport.UserTO;
import io.bpmnrepo.backend.user.api.transport.UserUpdateTO;
import io.bpmnrepo.backend.user.domain.business.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody @Valid final UserTO userTO){
        log.debug("Creating new user " + userTO.getUserName());
        this.userService.createUser(userTO);
        log.debug("Successfully created a new user");
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody @Valid final UserUpdateTO userUpdateTO){
        log.debug(String.format("updating user with id %s", userUpdateTO.getUserId()));
        this.userService.updateUser(userUpdateTO);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/currentUser")
    public ResponseEntity<UserInfoTO> getUserInfo(){
        log.debug("Returning information about logged in user");
        return ResponseEntity.ok().body(this.userService.getUserInfo());
    }
    @GetMapping("/registeredEmail")
    public ResponseEntity<UserEmailTO> getUserEmail(){
        log.debug("Returning email registered at Flowsquad");
        return ResponseEntity.ok().body(this.userService.getUserEmail());
    }

    @GetMapping("/apiKey")
    public ResponseEntity<UserTO> getApiKey() {
        log.debug("Returning new Api key");
        return ResponseEntity.ok().body(this.userService.getApiKey());
    }

}
