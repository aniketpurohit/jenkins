def DIR 

@Library('shared-lib@main') _  // Use the name you set in Jenkins configuration

def call(Map parameter=[:]){
    stage('Stage One') {
        script{
    DIR = "${params.dirs}"
     echo 'This is stage One'

     buildInfo()
    }
    stage('Stage Two') {
     echo 'This is stage Two'
     echo "${env.WORKSPACE_PATH}"
     echo "${params.dirs}"
     echo "${DIR}"
    echo "${parameter.GLOBALVARIABLE}"

    }
}