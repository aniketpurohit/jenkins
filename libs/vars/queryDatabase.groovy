def call(Map config) {
    def dbConnection = null
    def dbStatement = null
    def result = []
    try {
        // Validate inputs
        if (!config.DB_URL || !config.DB_USER || !config.DB_PASSWORD || !config.query) {
            error("Missing required parameters: DB_URL, DB_USER, DB_PASSWORD, or query.")
        }

        // Load the PostgreSQL driver dynamically
        def classLoader = new GroovyClassLoader()
        def driverFile = new File(config.driverPath ?: '/path/to/postgres.jar') // Default path to the driver
        classLoader.addURL(driverFile.toURI().toURL())
        Class.forName('org.postgresql.Driver', true, classLoader)

        // Establish the connection
        dbConnection = java.sql.DriverManager.getConnection(config.DB_URL, config.DB_USER, config.DB_PASSWORD)

        // Execute the query
        dbStatement = dbConnection.createStatement()
        def resultSet = dbStatement.executeQuery(config.query)

        // Process the results
        while (resultSet.next()) {
            def row = [:]
            def meta = resultSet.metaData
            (1..meta.columnCount).each { i ->
                row[meta.getColumnName(i)] = resultSet.getObject(i)
            }
            result << row
        }

        resultSet.close()
    } catch (Exception e) {
        error("Database Error: ${e.message}")
    } finally {
        // Close resources
        if (dbStatement != null) dbStatement.close()
        if (dbConnection != null) dbConnection.close()
    }

    return result
}
