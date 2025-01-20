@Library('my-shared-library') _

pipeline {
    agent any
    environment {
        JDBC_PATH = ''
        DB_URL = 'jdbc:postgresql://localhost:5432/jenkinsdb'
        DB_USER = 'jenkins_user'
        DB_PASSWORD = 'your_password'
    }
    stages {
        stage('Database Operations') {
            steps {
                script {
                    dbUtils.connectToPostgres(env.JDBC_PATH, env.DB_URL, env.DB_USER, env.DB_PASSWORD) { sql ->
                        // Example Query: Fetch Data
                        def rows = sql.rows('SELECT * FROM example_table')
                        echo "Rows fetched: ${rows.size()}"
                        
                        // Example Query: Insert Data
                        sql.executeInsert('INSERT INTO example_table (name, value) VALUES (?, ?)', ['exampleName', 'exampleValue'])
                    }
                }
            }
        }
    }
}
