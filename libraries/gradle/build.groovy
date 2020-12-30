def call(){
    
    stage("Build"){
        echo "Nombre de la compañia: ${config.compania}"
        //docker.image("maven").inside("-v /tmp/.m2:/root/.m2"){
            dir(config.pomDirectory){
                mvn_proxy="-Dhttp.proxyHost=10.226.14.40 -Dhttp.proxyPort=8080 -Dhttp.nonProxyHosts=10.226.12.45"
                mvn_proxy=""
                sh "mvn  clean package -DskipTests=true ${mvn_proxy}"
            }
        //}
    }
}
