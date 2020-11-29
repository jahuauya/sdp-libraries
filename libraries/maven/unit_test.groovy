def call(){
    stage("Unit Test"){      
        try {
            sh "mvn test"
        } catch (ex) {
            unstable('Unit Test failed')
            currentBuild.result = 'UNSTABLE'
        }
    }
}
