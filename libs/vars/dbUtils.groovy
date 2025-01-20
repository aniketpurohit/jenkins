import groovy.sql.Sql

def connectToPostgres(String jdbcPath, String dbUrl, String dbUser, String dbPassword, Closure queryBlock) {
    // Validate JDBC Path
    def jdbcFile = new File(jdbcPath)
    if (!jdbcFile.exists()) {
        error "JDBC driver not found at ${jdbcPath}"
    }

    // Add JDBC Driver to ClassLoader
    this.class.classLoader.rootLoader.addURL(jdbcFile.toURI().toURL())

    // Initialize Database Connection
    Sql sql = null
    try {
        sql = Sql.newInstance(dbUrl, dbUser, dbPassword, 'org.postgresql.Driver')

        // Execute the provided query block
        queryBlock(sql)
    } catch (Exception e) {
        error "Database connection or query failed: ${e.message}"
    } finally {
        sql?.close()
    }
}
