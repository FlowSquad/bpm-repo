package io.miragon.bpmrepo.core.repository.infrastructure.entity;

import io.miragon.bpmrepo.core.artifact.infrastructure.entity.ArtifactEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Repository")
public class RepositoryEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "repository_id", unique = true, nullable = false, updatable = false, length = 36)
    private String id;

    @Column(name = "repository_name")
    private String name;

    @Column(name = "repository_description")
    private String description;

    @Column(name = "created_date", updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Column(name = "existing_artifacts", columnDefinition = "integer default 0")
    private Integer existingArtifacts;

    @Column(name = "assigned_users", columnDefinition = "integer default 1")
    private Integer assignedUsers;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "repository_id")
    private List<ArtifactEntity> sharedArtifacts;
}
