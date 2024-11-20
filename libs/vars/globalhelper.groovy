// buildInfo.groovy
def buildInfo() {
    echo "Job Name: ${env.JOB_NAME}"
    echo "Build Number: ${env.BUILD_NUMBER}"
    echo "Build URL: ${env.BUILD_URL}"
}

// greetUser.groovy
def greetUser(String name) {
    echo "Hello, ${name}! Welcome to the Jenkins pipeline."
    if(false) {
        echo "Some thing is false"
    }
    else if(true){
        echo "In the else if block"
    }
    else{
        echo "In the else block"
    }
}

def makeDirectory(Map directory) {
    if (directory.parent) {  // Check if parent is not null or empty
        for (child in directory.children) {
            def temp = "${directory.parent}/${child}"  // Correct variable interpolation
            sh "mkdir -p ${temp}"  // Create the directory
        }
    } 
    else if(directory.children){
        for(child in directory.children){
        sh "mkdir -p ${child}"
        }
    }
    else {
        error "Parent directory path is not provided."
    }
}



def runPythonScrip(String script) {
    def output = sh(script: "python3 -c \"${script}\"", returnStdout: true).trim()
    echo output
    return output
}




