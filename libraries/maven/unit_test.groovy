def call(){
    stage("Unit Test"){      
        try {
            docker.image("maven").inside("-v ~/.m2:/root/.m2 -v /usr/share/maven/conf:/usr/share/maven/conf "){
                dir(config.pomDirectory){
                    sh "mvn test"
                }
            }
        } catch (ex) {
            unstable('Unit Test failed')
            currentBuild.result = 'UNSTABLE'
        }
    }
}
