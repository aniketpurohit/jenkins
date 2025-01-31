def call(String jsonContent, Map parameters = [:]) {
    List containsKeywordList = [
        "BUILD_ID", "BUILD_NUMBER", "BUILD_DISPLAY_NAME",
        "BUILD_TAG", "BUILD_URL", "JOB_NAME", "JOB_BASE_NAME",
        "JOB_URL", "NODE_LABELS", "WORKSPACE", "NODE_NAME",
        "GIT_BRANCH", "GIT_URL", "JENKINS_HOME", "JENKINS_URL"
    ]
    List parametersKeys = parameters.keySet().toList()

    // Parse JSON content
    def jsonContentMap = readJSON(text: jsonContent)
    echo "Parsed JSON content: ${jsonContentMap}"

    // Iterate over each key-value pair and process replacements
    jsonContentMap.each { fieldName, fieldValue ->
        
            echo "Processing field '${fieldName}' with value '${fieldValue}'..."

            // Replace Jenkins environment variables
            containsKeywordList.each { keyword ->
                if (fieldValue.contains("\${${keyword}}")) {
                    fieldValue = fieldValue.replace("\${${keyword}}", env."${keyword}" ?: "")
                }
            }

            // Replace custom parameter placeholders
            parameters.each { key, value ->
                if (fieldValue.contains("\${${key}}")) {
                    fieldValue = fieldValue.replace("\${${key}}", value)
                }
            }

            // Update the JSON content with the modified field value
            jsonContentMap[fieldName] = fieldValue
            echo "Updated field '${fieldName}' to value '${fieldValue}'"
        
    }

    return jsonContentMap
}

def getRootPath(String jsonContent, Map parameters = [:]) {
    List containsKeywordList = [
        "BUILD_ID", "BUILD_NUMBER", "BUILD_DISPLAY_NAME",
        "BUILD_TAG", "BUILD_URL", "JOB_NAME", "JOB_BASE_NAME",
        "JOB_URL", "NODE_LABELS", "WORKSPACE", "NODE_NAME",
        "GIT_BRANCH", "GIT_URL", "JENKINS_HOME", "JENKINS_URL"
    ]

    def jsonContentMap = readJSON(text: jsonContent)
    echo "Parsed JSON content: ${jsonContentMap}"

    def fieldValue = jsonContentMap.get("ROOT_FOLDER", '/var/jenkins_home/workspace/${JOB_NAME}')
    echo "Processing field 'ROOT_FOLDER' with value '${fieldValue}'..."

    // Replace Jenkins environment variables
    containsKeywordList.each { keyword ->
        if (fieldValue.contains("\${${keyword}}")) {
            fieldValue = fieldValue.replace("\${${keyword}}", env."${keyword}" ?: "")
        }
    }
    env.ROOT_FOLDER = fieldValue
    return fieldValue
}
