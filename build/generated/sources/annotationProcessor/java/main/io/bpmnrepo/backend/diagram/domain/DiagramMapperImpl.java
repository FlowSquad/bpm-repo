package io.bpmnrepo.backend.diagram.domain;

import io.bpmnrepo.backend.diagram.api.BpmnDiagramTO;
import io.bpmnrepo.backend.diagram.api.BpmnDiagramTO.BpmnDiagramTOBuilder;
import io.bpmnrepo.backend.diagram.domain.BpmnDiagram.BpmnDiagramBuilder;
import io.bpmnrepo.backend.diagram.infrastructure.BpmnDiagramEntity;
import io.bpmnrepo.backend.diagram.infrastructure.BpmnDiagramEntity.BpmnDiagramEntityBuilder;
import io.bpmnrepo.backend.repository.infrastructure.BpmnRepositoryEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-03T17:15:34+0100",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.7.jar, environment: Java 15.0.1 (Oracle Corporation)"
)
@Component
public class DiagramMapperImpl implements DiagramMapper {

    @Override
    public BpmnDiagram toModel(BpmnDiagramTO to) {
        if ( to == null ) {
            return null;
        }

        BpmnDiagramBuilder bpmnDiagram = BpmnDiagram.builder();

        bpmnDiagram.bpmnDiagramId( to.getBpmnDiagramId() );
        bpmnDiagram.bpmnDiagramName( to.getBpmnDiagramName() );
        bpmnDiagram.bpmnDiagramDescription( to.getBpmnDiagramDescription() );
        bpmnDiagram.bpmnDiagramRepositoryId( to.getBpmnDiagramRepositoryId() );

        return bpmnDiagram.build();
    }

    @Override
    public BpmnDiagramEntity toEntity(BpmnDiagram model, BpmnRepositoryEntity entity) {
        if ( model == null && entity == null ) {
            return null;
        }

        BpmnDiagramEntityBuilder bpmnDiagramEntity = BpmnDiagramEntity.builder();

        if ( model != null ) {
            bpmnDiagramEntity.bpmnDiagramId( model.getBpmnDiagramId() );
            bpmnDiagramEntity.bpmnDiagramName( model.getBpmnDiagramName() );
            bpmnDiagramEntity.bpmnDiagramDescription( model.getBpmnDiagramDescription() );
        }
        if ( entity != null ) {
            bpmnDiagramEntity.bpmnDiagramRepository( entity );
        }

        return bpmnDiagramEntity.build();
    }

    @Override
    public BpmnDiagramTO toTO(BpmnDiagramEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BpmnDiagramTOBuilder bpmnDiagramTO = BpmnDiagramTO.builder();

        bpmnDiagramTO.bpmnDiagramRepositoryId( entityBpmnDiagramRepositoryBpmnRepositoryId( entity ) );
        bpmnDiagramTO.bpmnDiagramId( entity.getBpmnDiagramId() );
        bpmnDiagramTO.bpmnDiagramName( entity.getBpmnDiagramName() );
        bpmnDiagramTO.bpmnDiagramDescription( entity.getBpmnDiagramDescription() );

        return bpmnDiagramTO.build();
    }

    private String entityBpmnDiagramRepositoryBpmnRepositoryId(BpmnDiagramEntity bpmnDiagramEntity) {
        if ( bpmnDiagramEntity == null ) {
            return null;
        }
        BpmnRepositoryEntity bpmnDiagramRepository = bpmnDiagramEntity.getBpmnDiagramRepository();
        if ( bpmnDiagramRepository == null ) {
            return null;
        }
        String bpmnRepositoryId = bpmnDiagramRepository.getBpmnRepositoryId();
        if ( bpmnRepositoryId == null ) {
            return null;
        }
        return bpmnRepositoryId;
    }
}
