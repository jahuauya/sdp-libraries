def call(){
    stage("Build"){
        docker.image("maven").inside("-v /tmp:/tmp"){
            sh "mvn  clean package -DskipTests=true"
        }
    }
}
