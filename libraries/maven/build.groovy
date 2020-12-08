def call(){
    stage("Build"){
        docker.image("maven").inside("-v /tmp/.m2:/root/.m2"){
            //sleep(300)
            dir(config.pomDirectory){
                sh "mvn  clean package -DskipTests=true -X"
            }
        }
    }
}
