package io.bpmnrepo.backend.user.domain.model;

import io.bpmnrepo.backend.user.api.transport.UserTO;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String userId;
    private String userName;
    private String apiKey;
    private String email;


    public User(final UserTO userTO){
        this.userName = userTO.getUserName();
        this.apiKey = this.newApiKey();
        this.email = userTO.getEmail();

    }
    public void updateApiKey(){
        this.apiKey = this.newApiKey();
    }

    public String newApiKey(){
        return UUID.randomUUID().toString();

    }
}
