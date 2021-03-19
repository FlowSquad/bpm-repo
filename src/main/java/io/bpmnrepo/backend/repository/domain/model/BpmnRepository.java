package io.bpmnrepo.backend.repository.domain.model;

import io.bpmnrepo.backend.repository.api.transport.BpmnRepositoryTO;
import io.bpmnrepo.backend.repository.api.transport.NewBpmnRepositoryTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
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
        this.bpmnRepositoryId = bpmnRepositoryTO.getBpmnRepositoryId() == null || bpmnRepositoryTO.getBpmnRepositoryId().isBlank()
                                ? null
                                : bpmnRepositoryTO.getBpmnRepositoryId();
        this.bpmnRepositoryName = bpmnRepositoryTO.getBpmnRepositoryName();
        this.bpmnRepositoryDescription = bpmnRepositoryTO.getBpmnRepositoryDescription();
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    public void updateRepo(final NewBpmnRepositoryTO newBpmnRepositoryTO){
        if(newBpmnRepositoryTO.getBpmnRepositoryName() != null && !newBpmnRepositoryTO.getBpmnRepositoryName().isEmpty()){
            this.setBpmnRepositoryName(newBpmnRepositoryTO.getBpmnRepositoryName());
        }
        if(newBpmnRepositoryTO.getBpmnRepositoryDescription() != null && !newBpmnRepositoryTO.getBpmnRepositoryDescription().isEmpty()){
            this.setBpmnRepositoryDescription(newBpmnRepositoryTO.getBpmnRepositoryDescription());
        }
        this.setUpdatedDate(LocalDateTime.now());
    }
}
