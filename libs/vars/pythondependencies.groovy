def runPythonSript(String script){
    output=sh(script: "python3 -c \"${script}\"", returnStdout: true).trim()
    echo output
    return output

}


def call(){
    try{
        def output = runPythonSript("print('Something is getting done')")
    }
    catch (Exception e){
        echo "failure of the python"
    }
}