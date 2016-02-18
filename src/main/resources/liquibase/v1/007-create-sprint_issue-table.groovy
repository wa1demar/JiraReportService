package liquibase.v1

databaseChangeLog {
    changeSet(id: '007', author: 'Vladimir Martynyuk') {
        comment "Create table sprint_issues"
        createTable(tableName: "sprint_issues") {

            column(name: 'id', type: 'bigint', autoIncrement: true) {
                constraints(nullable: false)
            }

            column(name: 'sprint_id', type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: 'agile_sprint_id', type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: 'key', type: 'varchar') {
                constraints(nullable: true)
            }

            column(name: 'point', type: 'double') {
                constraints(nullable: true)
            }

            column(name: 'type_name', type: 'varchar') {
                constraints(nullable: true)
            }

            column(name: 'assignee', type: 'varchar') {
                constraints(nullable: true)
            }

            column(name: 'status_name', type: 'varchar') {
                constraints(nullable: true)
            }

            column(name: 'issue-date', type: 'varchar') {
                constraints(nullable: true)
            }

            column(name: 'hours', type: 'double') {
                constraints(nullable: true)
            }

        }
        addPrimaryKey( columnNames: "id", tableName: "sprint_issues", constraintName: "pk_sprint_issue" )

        rollback {
            dropTable(tableName: 'sprint_issues')
        }

    }
}