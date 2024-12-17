// vars/logger.groovy
// This is a Jenkins shared library function for logging

// Map to track recursion for printMessage
private static Map<String, Boolean> failStates = [:]

def call(String level, String message) {
    def formattedMessage = formatMessage(level, message)
    printMessage(level, formattedMessage)
}

// Helper function to format the log message
def formatMessage(String level, String message) {
    def timestamp = new Date().format("yyyy-MM-dd HH:mm:ss,SSS")
    return "${timestamp} | ${level.toUpperCase()} | ${message}"
}

// Helper function to print the log message to the console
def printMessage(String level, String formattedMessage) {
    def logLevels = ['DEBUG', 'INFO', 'WARN', 'ERROR', 'CRITICAL', 'CUSTOM']
    def threadId = Thread.currentThread().getId()
    
    failStates[threadId] = failStates[threadId] ?: false

    if (!logLevels.contains(level.toUpperCase())) {
        throw new IllegalArgumentException("Invalid log level: ${level}. Valid levels are: ${logLevels.join(', ')}")
    }

    if (level.toUpperCase() in ['ERROR', 'CRITICAL']) {
        if (!failStates[threadId]) {
            failStates[threadId] = true
            echo formattedMessage
            error(formattedMessage) // Fail the build explicitly
        } else {
            echo "Recursive logging prevented for message: ${formattedMessage}"
        }
    } else {
        // Use echo for all other levels
        echo formattedMessage
    }
}

// Specific log level helper functions
def info(String message) {
    call('INFO', message)
}

def debug(String message) {
    call('DEBUG', message)
}

def critical(String message) {
    call('CRITICAL', message)
}

// Example for a custom log level
def custom(String message) {
    call('CUSTOM', message)
}
