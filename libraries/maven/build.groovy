def call(){
    
    stage("Build"){
        //docker.image("alirizasaral/maven-with-proxy").inside("-v /tmp/.m2:/root/.m2  -e PROXY_HOST=10.226.14.40 -e PROXY_PORT=8080 -e NO_PROXY=10.226.12.45 -e PROXY_PROTOCOL=http"){
          docker.image("maven").inside("-v /tmp/.m2:/root/.m2"){
            dir(config.pomDirectory){
                mvn_proxy="-Dhttp.proxyHost=10.226.14.40 -Dhttp.proxyPort=8080 -Dhttp.nonProxyHosts=localhost\|127.0.0.1\|10.226.12.45"
                sh "mvn  clean package -DskipTests=true ${mvn_proxy}"
            }
        }
    }
}
