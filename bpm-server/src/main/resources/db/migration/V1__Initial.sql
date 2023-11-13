-- ARTIFACT_ definition
CREATE TABLE ARTIFACT_ (
    ARTIFACT_ID_ VARCHAR(36) NOT NULL,
    CREATED_DATE_ TIMESTAMP,
    DESCRIPTION_ VARCHAR(255),
    FILE_TYPE_ VARCHAR(255),
    LOCKED_BY_ VARCHAR(255),
    LOCKED_UNTIL_ TIMESTAMP,
    NAME_ VARCHAR(255),
    REPOSITORY_ID_ VARCHAR(255),
    UPDATED_DATE_ TIMESTAMP,
    CONSTRAINT PK_ARTIFACT_ID_ PRIMARY KEY (ARTIFACT_ID_)
);


-- ARTIFACT_MILESTONE_ definition
CREATE TABLE ARTIFACT_MILESTONE_ (
    MILESTONE_ID_ VARCHAR(36) NOT NULL,
    ID_ VARCHAR(255) NOT NULL,
    COMMENT_ VARCHAR(255),
    FILE_ CLOB,
    LATEST_MILESTONE BOOLEAN NOT NULL,
    MILESTONE_ INTEGER NOT NULL,
    REPOSITORY_ID_ VARCHAR(255) NOT NULL,
    UPDATED_DATE_ TIMESTAMP,
    CONSTRAINT PK_MILESTONE_ID_ PRIMARY KEY (MILESTONE_ID_)
);


-- ASSIGNMENT_ definition
CREATE TABLE ASSIGNMENT_ (
    REPOSITORY_ID VARCHAR(255) NOT NULL,
    USER_ID VARCHAR(255) NOT NULL,
    ROLE_ VARCHAR(255),
    CONSTRAINT PK_REPOSITORY_ID_USER_ID PRIMARY KEY (REPOSITORY_ID,USER_ID)
);


-- REPOSITORY_ definition
CREATE TABLE REPOSITORY_ (
    REPOSITORY_ID_ VARCHAR(36) NOT NULL,
    ASSIGNED_USERS_ INTEGER DEFAULT 1,
    CREATED_DATE_ TIMESTAMP NOT NULL,
    REPOSITORY_DESCRIPTION_ VARCHAR(255),
    EXISTING_ARTIFACTS_ INTEGER DEFAULT 0,
    REPOSITORY_NAME_ VARCHAR(255),
    UPDATED_DATE_ TIMESTAMP NOT NULL,
    CONSTRAINT PK_REPOSITORY_ID_ PRIMARY KEY (REPOSITORY_ID_)
);


-- SHARED_REPOSITORY definition
CREATE TABLE SHARED_REPOSITORY (
    ARTIFACT_ID_ VARCHAR(255) NOT NULL,
    REPOSITORY_ID_ VARCHAR(255) NOT NULL,
    ROLE_ VARCHAR(255) NOT NULL,
    CONSTRAINT PK_ARTIFACT_ID_REPOSITORY_ID_ PRIMARY KEY (ARTIFACT_ID_,REPOSITORY_ID_)
);


-- STARRED_ definition
CREATE TABLE STARRED_ (
    ARTIFACT_ID VARCHAR(255) NOT NULL,
    USER_ID VARCHAR(255) NOT NULL,
    CONSTRAINT PK_ARTIFACT_ID_USER_ID PRIMARY KEY (ARTIFACT_ID,USER_ID)
);


-- USER_ definition
CREATE TABLE USER_ (
    USER_ID_ VARCHAR(36) NOT NULL,
    USER_NAME_ VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT PK_USER_ID_ PRIMARY KEY (USER_ID_)
);


-- DEPLOYMENT_ definition
CREATE TABLE DEPLOYMENT_ (
    DEPLOYMENT_ID_ VARCHAR(36) NOT NULL,
    ARTIFACT_ID_ VARCHAR(255),
    MESSAGE_ VARCHAR(255),
    REPOSITORY_ID_ VARCHAR(255),
    STATUS_ VARCHAR(255),
    TARGET_ VARCHAR(255),
    TIMESTAMP_ TIMESTAMP,
    USER_ VARCHAR(255),
    MILESTONE_ID VARCHAR(36),
    CONSTRAINT CONSTRAINT_PK_DEPLOYMENT_ PRIMARY KEY (DEPLOYMENT_ID_),
    CONSTRAINT FK_MILESTONE_ID_ARTIFACT_MILESTONE_ FOREIGN KEY (MILESTONE_ID) REFERENCES ARTIFACT_MILESTONE_(MILESTONE_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT
);
