def runPythonSript(String script){
    output=sh(script: "python3 ${script}", returnStdout: true).trim()
    echo output
    return output

}


def call(){
    try{
        def output = runPythonSript("-m pip install pandas")
    }
    catch (Exception e){
        echo "failure of the python"
    }
}