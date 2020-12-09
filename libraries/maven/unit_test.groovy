def call(){
    stage("Unit Test"){      
        try {
            //docker.image("maven").inside("-v /root/.m2:/root/.m2 -v /usr/share/maven/conf/setting.xml:/usr/share/maven/conf/setting.xml "){
                dir(config.pomDirectory){
                    sh "mvn test"
                }
            //}
        } catch (ex) {
            unstable('Unit Test failed')
            currentBuild.result = 'UNSTABLE'
        }
    }
}
