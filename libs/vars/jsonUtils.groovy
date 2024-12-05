import groovy.json.JsonSlurper

// Function to check if a JSON field is empty
def isJsonFieldEmpty(String jsonFilePath, String fieldName) {
    try {
        // Load and parse the JSON file
        def jsonFile = new File(jsonFilePath)
        if (!jsonFile.exists()) {
            throw new FileNotFoundException("JSON file not found at path: ${jsonFilePath}")
        }

        def jsonContent = new JsonSlurper().parse(jsonFile)

        // Check if the field exists and is empty
        if (jsonContent.containsKey(fieldName)) {
            def fieldValue = jsonContent[fieldName]
            if (fieldValue == null || fieldValue.toString().trim().isEmpty()) {
                return true // Field is empty
            }
            return false // Field is not empty
        } else {
            throw new IllegalArgumentException("Field '${fieldName}' does not exist in the JSON.")
        }
    } catch (FileNotFoundException e) {
        error "File Error: ${e.getMessage()}"
    } catch (IllegalArgumentException e) {
        error "Argument Error: ${e.getMessage()}"
    } catch (Exception e) {
        error "Unexpected Error: ${e.getMessage()}"
    }
}
