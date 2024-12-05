def setup(){
    def pipelineName = env.JOB_NAME.replaceAll('/','-')
    def tempWorkspace = "${env.WORKSPACE}/temp/${env.pipelineName}/${env.BUILD_NUMBER}"

    env.TEMP_WORKSPACE = tempWorkspace
    env.WORKSPACE_PATH = tempWorkspace
    
}