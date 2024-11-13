def DIR 

def call(Map parameter=[:]){
    stage('Stage One') {
        def DIR = "${params.dirs}"
     echo 'This is stage One'
    }
    stage('Stage Two') {
     echo 'This is stage Two'
     echo "${env.WORKSPACE_PATH}"
     echo "${params.dirs}"
     echo "${DIR}"
    echo "${parameter.GLOBALVARIABLE}"

    }
}