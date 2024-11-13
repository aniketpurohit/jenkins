@Library('shared-lib@main') _  // Use the name you set in Jenkins configuration

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
                    def parent = "~"
                    def children = ["child0", "child1"]
                    def directory = [parent: parent, children: children] // Calls the buildInfo function from vars/buildInfo.groovy
                    makeDirectory(directory)
                }
            }
        }

        sharedStages()
    }
}
