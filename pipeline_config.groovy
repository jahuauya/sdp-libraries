merge                 = true
allow_scm_jenkinsfile = false

libraries{
    merge = true
    github
/*    sdp{
        images{
            registry    = "http://docker.pkg.github.com/boozallen/sdp-images"
            repository  = "boozallen/sdp-images"
            cred        = "github"
            docker_args = "-v /tmp/.m2:/root/.m2 -v /tmp/.sonar:/root/.sonar"
        }
    }*/
    sonarqube{
        merge=true
        installation_name ='sonarqube'
        credential_id     ='tokensonarqube'
    }
    nexus_sonatype{
        credential_id   = "nexus-credentials"
        nexusVersion    = "nexus3"
        protocol        = "http"
        nexusUrl        = "192.168.1.20:8083"
    }
    /*notification{
        override = true
        destination = ""
    }*/
    
}

stages{
    continuous_integration{
        install
        unit_test
        build
        static_code_analysis
        //scan_artifact
    }
}

keywords{
    master = '^[Mm]aster$'
    develop = '^[Dd]evelop(ment|er|)$'
    hotfix = '^[Hh]ot[Ff]ix/.*'
    release = '^[Rr]elease.*'
    feature = '^[Ff]eature/.*'
}

application_environments{
    merge = true
    dev{
        merge = true
        short_name  = 'dev'
        long_name   = 'develop'
        repository  = "maven-snapshots"
    }

    test{
        merge = true
        short_name = 'qa'
        long_name  = 'Certificación'
        repository = "maven-releases"

    }

    prod{
        merge = true
        short_name = 'prod'
        long_name  = 'production'
        repository = "maven-master"

    }
}
