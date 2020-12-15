void call(Map args = [:]){

    credential_id   = config.credential_id
    nexusVersion    = config.nexusVersion
    protocol        = config.protocol
    nexusUrl        = config.nexusUrl
    
    repository      = args.repository
    groupId         = args.groupId
    version         = args.version
    artifacts       = args.artifacts

    nexusArtifactUploader(
        nexusVersion  : nexusVersion,
        protocol      : protocol,
        nexusUrl      : nexusUrl,
        groupId       : groupId,
        version       : version,
        repository    : repository,
        credentialsId : credential_id,
        artifacts     : artifacts
    );

}
