def call(){
    stage("Unit Test"){      
        try {
            //docker.image("maven").inside("-v /tmp/.m2:/root/.m2"){
                dir(config.pomDirectory){
                    mvn_proxy="-Dhttp.proxyHost=10.226.14.40 -Dhttp.proxyPort=8080 -Dhttp.nonProxyHosts=10.226.12.45"
                    mvn_proxy=""
                    sh "mvn test ${mvn_proxy} "
                }
            //}
        } catch (ex) {
            unstable('Unit Test failed')
            currentBuild.result = 'UNSTABLE'
        }
    }
}
