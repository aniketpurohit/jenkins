import logging
from enum import Enum
from typing import Optional


# Enum for custom logging levels
class Level(Enum):
    DEBUG = logging.DEBUG
    INFO = logging.INFO
    WARNING = logging.WARNING
    ERROR = logging.ERROR
    CRITICAL = logging.CRITICAL
    CUSTOM = 25  # Custom level between INFO and DEBUG


class Logger:
    def __init__(self, name: str, level: Level = Level.DEBUG, handler: Optional[logging.Handler] = None, formatter: Optional[logging.Formatter] = None):
        """
        Initialize the Logger instance.

        :param name: Name of the logger.
        :param level: Logging level (default is Level.DEBUG).
        :param handler: Optional handler to use (default is StreamHandler).
        :param formatter: Optional custom formatter (default format is provided).
        """
        # Register custom level if needed (optional, as Enum values are already mapped)
        logging.addLevelName(Level.CUSTOM.value, "CUSTOM")

        self.logger = logging.getLogger(name)
        self._set_logging_level(level)

        # Configurable Default Handler
        if handler is None:
            handler = logging.StreamHandler()
        if formatter is None:
            formatter = self._get_console_formatter()
        handler.setFormatter(formatter)
        self.logger.addHandler(handler)

    def _set_logging_level(self, level: Level):
        """
        Set the logging level with validation.

        :param level: Logging level to set.
        """
        if level not in Level:
            raise ValueError(f"Invalid logging level: {level}")
        self.logger.setLevel(level.value)

    @staticmethod
    def _get_console_formatter() -> logging.Formatter:
        """
        Returns a human-readable formatter for console logs with separators.
        """
        return logging.Formatter(
            '%(asctime)s | %(levelname)s | %(message)s'
        )

    @staticmethod
    def _get_json_formatter() -> logging.Formatter:
        """
        Returns a JSON-style formatter for file logs.
        """
        return logging.Formatter(
            '{"timestamp": "%(asctime)s", "level": "%(levelname)s", "logger": "%(name)s", "message": "%(message)s"}'
        )

    def add_file_handler(self, file_path: str, level: Level = Level.DEBUG, formatter: Optional[logging.Formatter] = None):
        """
        Add a FileHandler to the logger with JSON formatting.

        :param file_path: Path of the log file.
        :param level: Logging level for the file handler (default is DEBUG).
        :param formatter: Optional custom formatter for the file handler.
        """
        # Avoid adding duplicate handlers
        if not any(isinstance(handler, logging.FileHandler) and handler.baseFilename == file_path for handler in self.logger.handlers):
            try:
                file_handler = logging.FileHandler(file_path)
                file_handler.setLevel(level.value)
                if formatter is None:
                    formatter = self._get_json_formatter()
                file_handler.setFormatter(formatter)
                self.logger.addHandler(file_handler)
            except (IOError, OSError) as e:
                self.logger.error(f"Failed to add file handler. Error: {e}")

    def debug(self, message: str):
        """Log a debug message."""
        self.logger.debug(message)

    def info(self, message: str):
        """Log an info message."""
        self.logger.info(message)

    def warning(self, message: str):
        """Log a warning message."""
        self.logger.warning(message)

    def error(self, message: str):
        """Log an error message."""
        self.logger.error(message)

    def critical(self, message: str):
        """Log a critical message."""
        self.logger.critical(message)

    def result(self, success: bool, message: str):
        """
        Log a message indicating success or failure.

        :param success: Boolean indicating if the result was successful.
        :param message: The message to log.
        """
        if success:
            self.logger.info(f"SUCCESS: {message}")
        else:
            self.logger.error(f"FAILURE: {message}")

    # Custom log level
    def custom(self, message: str):
        """Log a custom level message."""
        if self.logger.isEnabledFor(Level.CUSTOM.value):
            self.logger.log(Level.CUSTOM.value, message)


# Example usage
if __name__ == "__main__":
    # Initialize the logger with a console handler (StreamHandler)
    app_logger = Logger("AppLogger", level=Level.CUSTOM)

    # Add a file handler with JSON formatting
    app_logger.add_file_handler("app.json.log", level=Level.CUSTOM)

    # Log messages with different severity
    app_logger.debug("This is a debug message.")
    app_logger.info("This is an info message.")
    app_logger.warning("This is a warning message.")
    app_logger.error("This is an error message.")
    app_logger.critical("This is a critical message.")
    app_logger.custom("This is a custom level message.")

    # Log results with success/failure
    app_logger.result(True, "The operation completed successfully.")
    app_logger.result(False, "The operation failed due to an error.")
