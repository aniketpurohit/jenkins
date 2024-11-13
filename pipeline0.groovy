@Library('shared-lib') _  // Use the name you set in Jenkins configuration

pipeline {
    agent any
    stages {
        stage('Greet User') {
            steps {
                script {
                    greetUser('John Doe')  // Calls the greetUser function from vars/greetUser.groovy
                }
            }
        }
        stage('Display Build Info') {
            steps {
                script {
                    buildInfo()  // Calls the buildInfo function from vars/buildInfo.groovy
                }
            }
        }
        stage('Display Build Info') {
            steps {
                script {
                    buildInfo()  // Calls the buildInfo function from vars/buildInfo.groovy
                }
            }
        }
    }
}
