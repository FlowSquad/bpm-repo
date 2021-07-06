package io.miragon.bpmrepo.core.diagram.infrastructure.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Deployment")
public class DeploymentEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "deployment_id", unique = true, nullable = false, updatable = false, length = 36)
    private String id;

    @Column(name = "target")
    private String target;

    @Column(name = "user")
    private String user;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

}
