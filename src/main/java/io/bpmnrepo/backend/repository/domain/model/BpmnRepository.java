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
    private Integer existingDiagrams;
    private Integer assignedUsers;


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
