// Function to clone a Git repository using the Jenkins Git plugin
def call(String repoUrl, String credentialsId = '', String branch = 'main', boolean shallowClone = false) {
    echo "Cloning repository: ${repoUrl}"
    echo "Branch: ${branch}"
    echo "Using Credentials ID: ${credentialsId ?: 'No credentials provided'}"

    try {
        checkout([
            $class: 'GitSCM',
            branches: [[name: "*/${branch}"]],
            doGenerateSubmoduleConfigurations: false,
            extensions: [
                shallowClone ? [$class: 'CloneOption', depth: 1, noTags: true, shallow: true, reference: ''] : []
            ],
            submoduleCfg: [],
            userRemoteConfigs: [[
                url: repoUrl,
                credentialsId: credentialsId ?: null
            ]]
        ])
        echo "Repository cloned successfully!"
    } catch (Exception e) {
        error "Failed to clone repository: ${e.message}"
    }
}
