package io.bpmnrepo.backend.repository.domain;

import io.bpmnrepo.backend.repository.api.BpmnRepositoryTO;
import io.bpmnrepo.backend.repository.api.BpmnRepositoryTO.BpmnRepositoryTOBuilder;
import io.bpmnrepo.backend.repository.infrastructure.BpmnRepositoryEntity;
import io.bpmnrepo.backend.repository.infrastructure.BpmnRepositoryEntity.BpmnRepositoryEntityBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-03T17:15:34+0100",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.7.jar, environment: Java 15.0.1 (Oracle Corporation)"
)
@Component
public class RepositoryMapperImpl implements RepositoryMapper {

    @Override
    public BpmnRepositoryTO toTO(BpmnRepositoryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BpmnRepositoryTOBuilder bpmnRepositoryTO = BpmnRepositoryTO.builder();

        bpmnRepositoryTO.bpmnRepositoryId( entity.getBpmnRepositoryId() );
        bpmnRepositoryTO.bpmnRepositoryName( entity.getBpmnRepositoryName() );
        bpmnRepositoryTO.bpmnRepositoryDescription( entity.getBpmnRepositoryDescription() );

        return bpmnRepositoryTO.build();
    }

    @Override
    public BpmnRepositoryEntity toEntity(BpmnRepository model) {
        if ( model == null ) {
            return null;
        }

        BpmnRepositoryEntityBuilder bpmnRepositoryEntity = BpmnRepositoryEntity.builder();

        bpmnRepositoryEntity.bpmnRepositoryId( model.getBpmnRepositoryId() );
        bpmnRepositoryEntity.bpmnRepositoryName( model.getBpmnRepositoryName() );
        bpmnRepositoryEntity.bpmnRepositoryDescription( model.getBpmnRepositoryDescription() );
        bpmnRepositoryEntity.createdDate( model.getCreatedDate() );
        bpmnRepositoryEntity.updatedDate( model.getUpdatedDate() );

        return bpmnRepositoryEntity.build();
    }
}
