def setup(){

    def tempWorkspace = tempWorspace()

    env.TEMP_WORKSPACE = tempWorkspace
    env.WORKSPACE_PATH = tempWorkspace

    sh "mkdir -p ${tempWorkspace}"
    return tempWorkspace
}

def call(){
    def pipelineName = env.JOB_NAME.replaceAll('/','-')
    def tempWorkspace = "/var/jenkins_home/workspace/${env.JOB_NAME}/temp/${pipelineName}/${env.BUILD_NUMBER}"

    return tempWorkspace
}

