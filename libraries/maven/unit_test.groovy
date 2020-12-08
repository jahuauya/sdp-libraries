def call(){
    stage("Unit Test"){      
        try {
            dir(config.pomDirectory){
                sh "mvn test"
            }   
            docker.image("maven").inside("-v ~/.m2:/root/.m2"){
            sleep(300)
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
