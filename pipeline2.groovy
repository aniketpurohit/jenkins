@Library('shared-lib@main') _ // Use the shared library configured in Jenkins

pipeline {
    agent any

    parameters {
        string(name: 'branch_name', defaultValue: 'master', description: 'Default branch name')
        booleanParam(name: 'test_of_list', defaultValue: false, description: "Switch between test files and weekly mode")
    }

    environment {
        TEST_REPOSITORY_PATH = "${WORKSPACE}/test_repository" // Define test repository path
    }

    stages {
        stage('Prepare Directories') {
            steps {
                script {
                    // Ensure directories are created
                    def directories = ["${env.TEST_REPOSITORY_PATH}"]
                    directories.each { dir ->
                        if (!new File(dir).exists()) {
                            sh "mkdir -p ${dir}"
                            echo "Created directory: ${dir}"
                        } else {
                            echo "Directory already exists: ${dir}"
                        }
                    }
                }
            }
        }

        stage('Create JSON File and Check It') {
            steps {
                script {
                    // Write JSON file to the directory
                    def jsonContent = """
                        {
                            "name": "John Doe",
                            "age": 30,
                            "address": ""
                        }
                    """.stripIndent()

                    def jsonFilePath = "${env.TEST_REPOSITORY_PATH}/test.json"
                    writeFile(file: jsonFilePath, text: jsonContent)
                    echo "JSON file created at: ${jsonFilePath}"

                    // Check if the "name" field in the JSON file is empty
                    try {
                        def isEmpty = jsonUtils.isJsonFieldEmpty(jsonFilePath, "name")
                        if (isEmpty) {
                            echo "Field 'name' is empty."
                        } else {
                            echo "Field 'name' is not empty."
                        }
                    } catch (Exception e) {
                        echo "Error checking JSON field: ${e.getMessage()}"
                        error "Pipeline failed due to JSON validation error."
                    }
                }
            }
        }
    }
}
