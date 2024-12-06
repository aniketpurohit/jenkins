package CAFHelper

class GlobalVarsUtils {

    // Method to load global variables from JSON using readFile
    static Map loadGlobalVars(String jsonFilePath, Map parameters = [:]) {
        // List of Jenkins environment variable keywords to replace
        List<String> containsKeywordList = [
            "BUILD_ID", "BUILD_NUMBER", "BUILD_DISPLAY_NAME",
            "BUILD_TAG", "BUILD_URL", "JOB_NAME", "JOB_BASE_NAME",
            "JOB_URL", "NODE_LABELS", "WORKSPACE", "NODE_NAME",
            "GIT_BRANCH", "GIT_URL", "JENKINS_HOME", "JENKINS_URL"
        ]
        List<String> parametersKeys = parameters.keySet().toList()

        // Check if the file exists
        def jsonFile = new File(jsonFilePath)
        if (!jsonFile.exists()) {
            throw new FileNotFoundException("File not found: ${jsonFilePath}")
        }

        // Read and parse JSON content
        def jsonContent = readJSON(file: jsonFilePath)
        echo "Parsed JSON content: ${jsonContent}"

        // Iterate over each key-value pair and process replacements
        jsonContent.each { fieldName, fieldValue ->
            if (fieldValue instanceof String) {
                echo "Processing field '${fieldName}' with value '${fieldValue}'..."

                // Check for Jenkins environment keywords
                boolean containsKeyword = containsKeywordList.any { keyword ->
                    fieldValue.contains("\${${keyword}}")
                }

                // Check for custom parameter placeholders
                boolean containsCustomKeyword = parametersKeys.any { keyword ->
                    fieldValue.contains("\${${keyword}}")
                }

                // Replace environment variable placeholders
                if (containsKeyword) {
                    containsKeywordList.each { keyword ->
                        if (fieldValue.contains("\${${keyword}}")) {
                            fieldValue = fieldValue.replace("\${${keyword}}", env."${keyword}" ?: "")
                        }
                    }
                }

                // Replace custom parameter placeholders
                if (containsCustomKeyword) {
                    parameters.each { key, value ->
                        if (fieldValue.contains("\${${key}}")) {
                            fieldValue = fieldValue.replace("\${${key}}", value)
                        }
                    }
                }

                // Update the JSON content with the modified field value
                jsonContent[fieldName] = fieldValue
                echo "Updated field '${fieldName}' to value '${fieldValue}'"
            }
        }

        return jsonContent
    }

    // Method to save the global variables back to the JSON file using writeFile
    static void saveGlobalVars(String jsonFilePath, Map globalVars) {
        // Convert the map back to JSON format
        def jsonBuilder = new groovy.json.JsonBuilder(globalVars)
        def jsonContent = jsonBuilder.toPrettyString()

        // Write the JSON content to the file
        writeFile(file: jsonFilePath, text: jsonContent)
        echo "Global variables saved to: ${jsonFilePath}"
    }
}
