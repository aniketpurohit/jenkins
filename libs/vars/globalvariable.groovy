def call(){

    parameters {
    string(name: 'dirs', defaultValue: '~', description: '')
}

    environment {
        WORKSPACE_PATH = "${WORKSPACE}"
    }
}