@Notify ({ ( (currentBuild.currentResult == 'FAILURE' && context.library != null) ||  (currentBuild.currentResult != 'FAILURE' && context.library == null) ) && config.to})
//@Notify
void call(context) {

    emailext (
        subject: "${currentBuild.currentResult} Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
        body: """
            <p>Pipeline Status: ${currentBuild.currentResult}</p>
            <p>Job: '${env.JOB_NAME} [${env.BUILD_NUMBER}]'</p>
            <p>Library: ${context.library}
            <p>Step: ${context.step}
            
            <p>Check console output at <a href='${env.BUILD_URL}'>${env.JOB_NAME}]</a></p>""",
        //recipientProviders: [[$class: 'DevelopersRecipientProvider'], culprits(), developers(), requestor(), brokenBuildSuspects(), brokenTestsSuspects(), upstreamDevelopers()],
        to: config.destination
    )
}

//<p>Library: ${context.library}
//<p>Step: ${context.step}
