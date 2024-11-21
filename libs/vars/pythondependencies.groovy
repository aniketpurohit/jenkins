
@Library('shared-lib') import runPythonScript

def call(){
    try{
        def lib = "os"

        def output = runPythonScript("import ${lib}; print(dir(${lib}))")
    }
    catch (Exception e){
        echo "failure of the python"
    }

    env.PYTHONPATH = "${env.WORKSPACE}:${env.PYTHONPATH}"
    env.PYTHONUSERBASE = "${env.WORKSPACE}:${env.PYTHONUSERBASE}"
    
}