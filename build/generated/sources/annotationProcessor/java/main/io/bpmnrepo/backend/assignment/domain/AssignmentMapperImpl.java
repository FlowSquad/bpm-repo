package io.bpmnrepo.backend.assignment.domain;

import io.bpmnrepo.backend.assignment.infrastructure.AssignmentEntity;
import io.bpmnrepo.backend.assignment.infrastructure.AssignmentEntity.AssignmentEntityBuilder;
import io.bpmnrepo.backend.assignment.infrastructure.AssignmentId;
import io.bpmnrepo.backend.assignment.infrastructure.AssignmentId.AssignmentIdBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-03T17:15:34+0100",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.7.jar, environment: Java 15.0.1 (Oracle Corporation)"
)
@Component
public class AssignmentMapperImpl implements AssignmentMapper {

    @Override
    public AssignmentEntity toEntity(Assignment model, AssignmentId assignmentId) {
        if ( model == null && assignmentId == null ) {
            return null;
        }

        AssignmentEntityBuilder assignmentEntity = AssignmentEntity.builder();

        if ( model != null ) {
            assignmentEntity.roleEnum( model.getRoleEnum() );
        }
        if ( assignmentId != null ) {
            assignmentEntity.assignmentId( assignmentId );
        }

        return assignmentEntity.build();
    }

    @Override
    public AssignmentId toEmbeddable(String userId, String bpmnRepositoryId) {
        if ( userId == null && bpmnRepositoryId == null ) {
            return null;
        }

        AssignmentIdBuilder assignmentId = AssignmentId.builder();

        if ( userId != null ) {
            assignmentId.userId( userId );
        }
        if ( bpmnRepositoryId != null ) {
            assignmentId.bpmnRepositoryId( bpmnRepositoryId );
        }

        return assignmentId.build();
    }
}
