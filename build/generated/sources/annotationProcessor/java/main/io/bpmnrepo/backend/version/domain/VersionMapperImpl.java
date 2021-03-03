package io.bpmnrepo.backend.version.domain;

import io.bpmnrepo.backend.diagram.infrastructure.BpmnDiagramEntity;
import io.bpmnrepo.backend.version.api.BpmnDiagramVersionTO;
import io.bpmnrepo.backend.version.api.BpmnDiagramVersionTO.BpmnDiagramVersionTOBuilder;
import io.bpmnrepo.backend.version.infrastructure.BpmnDiagramVersionEntity;
import io.bpmnrepo.backend.version.infrastructure.BpmnDiagramVersionEntity.BpmnDiagramVersionEntityBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-03T17:15:34+0100",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.7.jar, environment: Java 15.0.1 (Oracle Corporation)"
)
@Component
public class VersionMapperImpl implements VersionMapper {

    @Override
    public BpmnDiagramVersionEntity toEntity(BpmnDiagramVersion model, BpmnDiagramEntity entity) {
        if ( model == null && entity == null ) {
            return null;
        }

        BpmnDiagramVersionEntityBuilder bpmnDiagramVersionEntity = BpmnDiagramVersionEntity.builder();

        if ( model != null ) {
            bpmnDiagramVersionEntity.bpmnDiagramVersionId( model.getBpmnDiagramVersionId() );
            bpmnDiagramVersionEntity.bpmnDiagramVersionName( model.getBpmnDiagramVersionName() );
            bpmnDiagramVersionEntity.bpmnDiagramVersionNumber( model.getBpmnDiagramVersionNumber() );
            bpmnDiagramVersionEntity.bpmnDiagramVersionFile( model.getBpmnDiagramVersionFile() );
        }
        if ( entity != null ) {
            bpmnDiagramVersionEntity.bpmnDiagramEntity( entity );
        }

        return bpmnDiagramVersionEntity.build();
    }

    @Override
    public BpmnDiagramVersionTO toTO(BpmnDiagramVersionEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BpmnDiagramVersionTOBuilder bpmnDiagramVersionTO = BpmnDiagramVersionTO.builder();

        bpmnDiagramVersionTO.bpmnDiagramId( entityBpmnDiagramEntityBpmnDiagramId( entity ) );
        bpmnDiagramVersionTO.bpmnDiagramVersionId( entity.getBpmnDiagramVersionId() );
        bpmnDiagramVersionTO.bpmnDiagramVersionName( entity.getBpmnDiagramVersionName() );
        bpmnDiagramVersionTO.bpmnDiagramVersionNumber( entity.getBpmnDiagramVersionNumber() );
        bpmnDiagramVersionTO.bpmnDiagramVersionFile( entity.getBpmnDiagramVersionFile() );

        return bpmnDiagramVersionTO.build();
    }

    private String entityBpmnDiagramEntityBpmnDiagramId(BpmnDiagramVersionEntity bpmnDiagramVersionEntity) {
        if ( bpmnDiagramVersionEntity == null ) {
            return null;
        }
        BpmnDiagramEntity bpmnDiagramEntity = bpmnDiagramVersionEntity.getBpmnDiagramEntity();
        if ( bpmnDiagramEntity == null ) {
            return null;
        }
        String bpmnDiagramId = bpmnDiagramEntity.getBpmnDiagramId();
        if ( bpmnDiagramId == null ) {
            return null;
        }
        return bpmnDiagramId;
    }
}
