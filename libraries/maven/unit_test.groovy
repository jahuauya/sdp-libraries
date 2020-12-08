def call(){
    stage("Unit Test"){      
        try {
            dir(config.pomDirectory){
                sh "mvn test"
            }    
        } catch (ex) {
            unstable('Unit Test failed')
            currentBuild.result = 'UNSTABLE'
        }
    }
}
