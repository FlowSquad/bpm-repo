package io.bpmnrepo.backend.user.infrastructure.entity;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_id", unique = true, updatable = false, nullable = false, length = 36)
    private String userId;

    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Column(name = "api_key", unique = true, nullable = false)
    private String apiKey;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

}
