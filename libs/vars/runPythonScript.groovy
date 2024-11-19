def call(String script) {
    def output = sh(script: "python3 -c \"${script}\"", returnStdout: true).trim()
    echo output
    return output
}
