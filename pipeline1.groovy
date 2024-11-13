@Library('shared-lib@main') _  // Use the name you set in Jenkins configuration

pipeline{

    agent any 

    parameters {
        string(name: 'branch_name', defaultValue: 'master', description: 'default branch name')
        booleanParam(name: 'test_of_list', defaultValue: false, description: "Indicator for swtich between tes files and weekly")
}
environment{
    WORKSPACE_PATH = "${WORKSPACE}"
}

stages {
    stage('Initialize pipeline global variable'){
        TEST_REPOSITORY_PATH = "${env.WORKSPACE_PATH}/test_repository"
    }

    stage('prepare directories'){
        script{
            def children  =["${TEST_REPOSITORY_PATH}"]
            def directory = [children: children]
            makeDirectory(directory)
        }
    }

    stage('Pyhton dependencies and workspace'){
        pythondependencies()
    }


    stage('Monitir'){
        when { expression { params.test_of_list == true  } }
        steps{
            echo "Monitoring"
        }
    }
}
}