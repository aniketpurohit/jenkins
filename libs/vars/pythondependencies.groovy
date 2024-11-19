
@Library('shared-lib') _

def call(){
    try{
        def output = runPythonSript("print('Something is getting done')")
    }
    catch (Exception e){
        echo "failure of the python"
    }

    env.PYTHONPATH = "${env.WORKSPACE}:${env.PYTHONPATH}"
    env.PYTHONUSERBASE = "${env.WORKSPACE}:${env.PYTHONUSERBASE}"
    
}