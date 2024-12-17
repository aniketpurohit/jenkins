pipeline {
    agent any
    parameters {
        file(name: 'UPLOAD_FILE', description: 'Upload a file')
    }
    stages {
        stage('Debug') {
            steps {
                echo "Checking uploaded file..."
                sh 'ls -la'
            }
        }
    }
}

