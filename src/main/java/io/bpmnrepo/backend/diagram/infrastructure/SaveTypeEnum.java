package io.bpmnrepo.backend.diagram.infrastructure;

public enum SaveTypeEnum {

    /**
     * Will increase the integer value
     */
    RELEASE(),


    /**
     * Will increase the decimal digit
     */

    MILESTONE(),

    /**
     * Won't affect the version number
     */

    AUTOSAVE();

}
