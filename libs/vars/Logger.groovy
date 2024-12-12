// vars/logger.groovy
// This is a Jenkins shared library function for logging

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

    if (!logLevels.contains(level.toUpperCase())) {
        error "Invalid log level: ${level}. Valid levels are: ${logLevels.join(', ')}"
    }

    if (level.toUpperCase() in ['ERROR', 'CRITICAL']) {
        // Use the error step for error and critical level messages
        error formattedMessage
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
