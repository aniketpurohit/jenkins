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
                    def globalVars = loadGlobalVars(jsonPath)
                    echo "Loaded global vars: ${globalVars}"
                }
            }
        }
        stage('Prepare Workspace') {
            steps {
                script {
                    // Create the custom workspace
                    //com.yourcompany.WorkspaceUtils.createWorkspace(CUSTOM_WORKSPACE)
                }
            }
        }

        stage('Checkout Code') {
            steps {
                dir("${CUSTOM_WORKSPACE}") {
                    checkout scm
                }
            }
        }

        stage('Build') {
            steps {
                dir("${CUSTOM_WORKSPACE}") {
                    // Example build step inside the custom workspace
                    sh 'echo "Building in custom workspace" > build.log'
                    sh 'ls -l'  // List files in the custom workspace
                }
            }
        }

        stage('Save Global Variables') {
            steps {
                script {
                    // Optionally update global variables and save them back to the JSON file
                    def jsonFile = 'resources/mylibrary/global_vars.json'
                    def globals = com.yourcompany.GlobalVarsUtils.loadGlobalVars(jsonFile)
                    
                    // Modify global variables if necessary, e.g., add a new key-value pair
                    globals.teamName = "Updated Team"

                    // Save the updated global variables back to the JSON file
                    com.yourcompany.GlobalVarsUtils.saveGlobalVars(jsonFile, globals)
                }
            }
        }

        stage('Cleanup') {
            steps {
                script {
                    // Clean up the custom workspace
                    com.yourcompany.WorkspaceUtils.cleanWorkspace(CUSTOM_WORKSPACE)
                }
            }
        }
    }
}
