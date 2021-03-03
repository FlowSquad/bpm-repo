package io.bpmnrepo.backend.repository.domain;

import io.bpmnrepo.backend.repository.api.BpmnRepositoryTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BpmnRepository {

    private String bpmnRepositoryId;
    private String bpmnRepositoryName;
    private String bpmnRepositoryDescription;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public BpmnRepository(final BpmnRepositoryTO bpmnRepositoryTO){
        this.bpmnRepositoryId = bpmnRepositoryTO.getBpmnRepositoryId();
        this.bpmnRepositoryName = bpmnRepositoryTO.getBpmnRepositoryName();
        this.bpmnRepositoryDescription = bpmnRepositoryTO.getBpmnRepositoryDescription();
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }
}
