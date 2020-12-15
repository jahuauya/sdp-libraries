void call(app_env){

    stage 'Publish', {

        isPublishable   = config.isPublishable ?: false
        if (!isPublishable)
            return

dir(config.pomDirectory){
        pom = readMavenPom file: "pom.xml"
        // Find built artifact under target folder
        filesByGlob = findFiles(glob: "target/*.${pom.packaging}")
        // Print some info from the artifact found
        echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
        // Extract the path from the File found
        artifactPath = filesByGlob[0].path
        // Assign to a boolean response verifying If the artifact name exists
        artifactExists = fileExists artifactPath;
        
        if(!artifactExists)
            error "*** File: ${artifactPath}, could not be found";

        artifacts = [
            // Artifact generated such as .jar, .ear and .war files.
            [artifactId: pom.artifactId,
            classifier: '',
            file: artifactPath,
            type: pom.packaging],
            // Lets upload the pom.xml file for additional information for Transitive dependencies
            [artifactId: pom.artifactId,
            classifier: '',
            file: "pom.xml",
            type: "pom"]
        ]

        args = [
            repository : app_env.repository,
            groupId    : pom.groupId,
            version    : app_env.version,
            artifacts  : artifacts
        ]
            
        uploader(args)
        }
    }
}
