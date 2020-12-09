def call(){
    stage("Build"){
        //docker.image("maven").inside("-v /root/.m2:/root/.m2 -v /usr/share/maven/conf/setting.xml:/usr/share/maven/conf/setting.xml "){
            dir(config.pomDirectory){
                sh "mvn  clean package -DskipTests=true"
            }
        //}
    }
}
