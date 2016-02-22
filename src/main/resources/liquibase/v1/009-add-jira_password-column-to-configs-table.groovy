package liquibase.v1

databaseChangeLog {
    changeSet(id: '008', author: 'Vladimir Martynyuk') {
        comment "Add jira_password column to configs table"
        addColumn(tableName: "configs") {

            column(name: 'jira_password', type: 'varchar') {
                constraints(nullable: true)
            }

        }

        rollback {
            dropColumn(tableName: "configs") {

                column(name: 'jira_password')

            }
        }

    }
}