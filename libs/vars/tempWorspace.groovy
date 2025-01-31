def setup(){

    def tempWorkspace = tempWorspace()

    env.TEMP_WORKSPACE = tempWorkspace
    env.WORKSPACE_PATH = tempWorkspace

    sh "mkdir -p ${tempWorkspace}"
    return tempWorkspace
}

def call(){
    def pipelineName = env.JOB_NAME.replaceAll('/','-')
    def rootpathval = rootpath()
    def tempWorkspace = "${rootpathval}/temp/${pipelineName}/${env.BUILD_NUMBER}"

    return tempWorkspace
}

def rootpath(){
    jsonContent = libraryResource('global_variables.json')
    return globalVarsUtils.getRootPath(jsonContent)
}

