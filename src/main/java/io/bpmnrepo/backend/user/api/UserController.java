package io.bpmnrepo.backend.user.api;


import io.bpmnrepo.backend.shared.Mapper;
import io.bpmnrepo.backend.user.domain.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final Mapper mapper;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser (@RequestBody @Validated final UserTO userTO){
        System.out.println("Creating new user " + userTO.getUserName());
        val user = this.mapper.toEntity(userTO);
        this.userService.saveToDb(user);
        return ResponseEntity.ok().build();
    }
}
