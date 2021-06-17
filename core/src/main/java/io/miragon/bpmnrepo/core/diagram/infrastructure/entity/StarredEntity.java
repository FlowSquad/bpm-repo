package io.miragon.bpmnrepo.core.diagram.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "starred")
public class StarredEntity {

    @EmbeddedId
    private StarredId starredId;
}
