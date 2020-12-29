void call(app_env){
  stage "Deploy to ${app_env.long_name}", {
    // validate required parameters

    // configuration repository storing the chart
    

   
    /*
       tiller namespace for this repository.
       can be specific in library spec or per application environment as "tiller_namespace"
    */
    def tiller_namespace = app_env.tiller_namespace ?:
                           config.tiller_namespace  ?:
                           {error "Tiller Namespace Not Defined"}()

    /*
       Jenkins credential for tiller (typically the service account running the tiller server).
       can be specific in library spec or per application environment.
    */
    def tiller_credential = app_env.tiller_credential ?:
                            config.tiller_credential  ?:
                            {error "Tiller Credential Not Defined"}()
    /*
       ocp url
       can be specific in library spec as "url"
       or per application environment as "openshift_url"
    */
    def ocp_url = app_env.openshift_url ?:
                  config.url            ?:
                  {error "OpenShift URL Not Defined"}()


    /*
       values file to be used when deploying chart
       can specify per application environment object "app_env.chart_values_file"
       otherwise "values.${app_env.short_name}.yaml" will be present if defined and exists
       otherwise - will fail
    */
    def values_file = app_env.chart_values_file ?:
                      app_env.short_name ? "values.${app_env.short_name}.yaml" :
                      {error "Values File To Use For This Chart Not Defined"}()

  
   

        //env.NODEJS_HOME = "oc oc"
        //env.PATH="${env.NODEJS_HOME}/bin:${env.PATH}"

      inside_sdp_image "openshift_helm", {
        //withCredentials([usernamePassword(credentialsId: tiller_credential, passwordVariable: 'token', usernameVariable: 'user')]) {
        withCredentials([string(credentialsId: tiller_credential, variable: 'token')]) {
            
            this.oc_login ocp_url, token
           
            sh "oc new-project ${tiller_namespace} || oc project ${tiller_namespace}"
            sh "oc new-build ${config.template} --name=${config.app} --binary=true || true"
            
            sh "oc start-build ${config.app}   --from-file=${config.archiveWar} --follow=true --wait=true "
            sh "oc new-app ${config.app}  -e HTTP_PROXY=10.226.14.59:8080 --name  ${config.app} --as-deployment-config|| true"
            //sh "oc annotate namespace ${app_env.tiller_namespace} openshift.io/node-selector=ambiente=qa"
            sh "oc expose svc/${config.app} || true"

            //this.create_volumen_yaml(config.app)
            sh "oc apply -f ${values_file} || true"
            mountPath="/var/opt/teradata/daas-log4j/claim-ticket/"
            sh "oc set volumes dc/${config.app}  --add --overwrite --name=${config.app} --mount-path=${mountPath}"
          
        }
      }
    
  }
}


void oc_login(ocp_url, token){
  try {
    
    echo "Trying to log in via token..."
    sh "oc login --insecure-skip-tls-verify ${ocp_url} --token=${token} > /dev/null"
  } catch (any){
    echo "Trying to log in via user/pass..."
    sh "oc login --insecure-skip-tls-verify ${ocp_url} -u ${user} -p ${token} > /dev/null"
  }
}

