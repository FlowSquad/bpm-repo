package io.miragon.bpmrepo.core.diagram.api.transport;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeplyomentTO {

    @NotEmpty
    private String target;

}
