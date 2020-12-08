def call(){
    stage("Build"){
        docker.image("maven").inside("-v /tmp/.m2:/root/.m2"){
            sh "mvn  clean package -DskipTests=true"
        }
    }
}
