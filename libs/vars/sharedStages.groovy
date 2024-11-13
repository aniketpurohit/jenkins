def call(Map parameter=[:]){
    stage('Stage One') {
     echo 'This is stage One'
    }
    stage('Stage Two') {
     echo 'This is stage Two'
     echo "${WORKSPACE_PATH}"
     echo "${params.dirs}"
    echo "${parameter.GLOBALVARIABLE}"

    }
}