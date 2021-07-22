package io.miragon.bpmrepo.core.diagram.api.plugin;

import io.miragon.bpmrepo.core.diagram.api.transport.FileTypesTO;

import java.util.List;

public interface FileTypesPlugin {

    List<FileTypesTO> getFileTypes();

}
