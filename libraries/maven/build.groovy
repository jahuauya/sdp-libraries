def call(){
    stage("Build"){
        docker.image("alirizasaral/maven-with-proxy").inside("-v /tmp/.m2:/root/.m2  -e PROXY_HOST=10.226.14.40 -e PROXY_PORT=8080 -e NO_PROXY=10.226.12.45"){
            dir(config.pomDirectory){
                sh "mvn  clean package -DskipTests=true"
            }
        }
    }
}
