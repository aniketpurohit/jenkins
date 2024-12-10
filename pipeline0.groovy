@Library('shared-lib@main') _  // Use the name you set in Jenkins configuration

def GLOBALVARIABLE = 'new variable'
def CONFIG_PATH

pipeline {
    agent any

    parameters {
        string(name: 'dirs', defaultValue: '~', description: '')
    }

    environment {
        WORKSPACE_PATH = "${WORKSPACE}"
    }

    stages {
        stage('Greet User') {
            steps {
                script {
                    globalhelper.greetUser('John Doe')  // Calls the greetUser function from vars/greetUser.groovy
                }
            }
        }
        stage('Display Build Info') {
            steps {
                script {
                    globalhelper.buildInfo()  // Calls the buildInfo function from vars/buildInfo.groovy
                }
            }
        }
        stage('Build dependencies') {
            steps {
                script {
                    pythondependencies()  // Calls the buildInfo function from vars/buildInfo.groovy
                }
            }
        }

        stage('create directory') {
            steps {
                script {
                    def children = ['~/child0', '~/child1']
                    def directory = [children: children] // Calls the buildInfo function from vars/buildInfo.groovy
                    makeDirectory(directory)
                }
            }
        }

        stage('create config directory ') {
            steps {
                script {
                    CONFIG_PATH = "${WORKSPACE}/temp/newPath"
                    sh "mkdir -p ${CONFIG_PATH}"
                }
            }
        }
        stage('Shared Stages') {
            steps {
                script {
                    dir(CONFIG_PATH) {
                        def parameter = [
                        GLOBALVARIABLE: GLOBALVARIABLE  // Pass the variable to the method
                    ]

                        sharedStages(parameter)
                    }
                }
            }
        }
    }
}
