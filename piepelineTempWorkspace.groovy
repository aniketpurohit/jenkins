@Library('shared-library-name') _  // Replace with your shared library name

pipeline {
    agent any
    environment {
        CUSTOM_WORKSPACE = ''
    }
    stages {
        stage('Load Global Variables') {
            steps {
                script {
                    // Load the global variables from the shared library
                    def jsonFile = 'resources/global_variables.json'  // Adjust path if necessary
                    def globals = CAFHelper.GlobalVarsUtils.loadGlobalVars(jsonFile)
                    echo "globals : ${globals}"
                    // Assign the custom workspace path from the loaded global variables
                    //CUSTOM_WORKSPACE = globals.customWorkspace
                    //echo "Custom workspace: ${CUSTOM_WORKSPACE}"
                }
            }
        }

        stage('Prepare Workspace') {
            steps {
                script {
                    // Create the custom workspace
                    com.yourcompany.WorkspaceUtils.createWorkspace(CUSTOM_WORKSPACE)
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
