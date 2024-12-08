@Library('shared-lib') _  // Replace with your shared library name

pipeline {
    agent any
    environment {
        CUSTOM_WORKSPACE = ''
    }
    stages {
        stage('Load Global Vars') {
            steps {
                script {
                    // Using the loadGlobalVars function from vars
                    def jsonPath = libraryResource('global_variables.json')
                    echo "jsonPath : ${jsonPath}"
                    def globalVars = GlobalVarsUtils(jsonPath)
                    echo "Loaded global vars: ${globalVars}"
                }
            }
        } 
    }
}
