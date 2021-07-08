package io.miragon.bpmrepo.core.diagram.domain.model;

import io.miragon.bpmrepo.core.diagram.domain.enums.SaveTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Builder
@ToString
@AllArgsConstructor
public class DiagramVersion {

    private final String id;

    private final SaveTypeEnum saveType;

    private final String diagramId;

    private final String repositoryId;

    private Integer milestone;

    private String xml;

    private String comment;

    private LocalDateTime updatedDate;

    private final List<Deployment> deployments = new ArrayList<>();

    public void updateVersion(final DiagramVersion diagramVersion) {
        if (StringUtils.isNotBlank((diagramVersion.getComment()))) {
            this.comment = diagramVersion.getComment();
        }
        this.milestone++;
        this.xml = diagramVersion.getXml();
        this.updatedDate = LocalDateTime.now();
    }

    public void updateMilestone(final Integer milestone) {
        this.milestone = milestone;
    }

    public void deploy(final String target, final String user) {
        final Deployment deployment = Deployment.builder()
                .target(target)
                .user(user)
                .timestamp(LocalDateTime.now())
                .build();
        this.deployments.add(deployment);
    }

}
