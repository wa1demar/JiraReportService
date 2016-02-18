package liquibase.v1

databaseChangeLog {
    changeSet(id: '002', author: 'Vladimir Martynyuk') {
        comment "Create table comments"
        createTable(tableName: "comments") {
            column(name: 'id', type: 'int', autoIncrement: true) {
                constraints(nullable: false)
            }

            column(name: 'report_id', type: 'int') {
                constraints(nullable: false)
            }

            column(name: 'sprint_id', type: 'int') {
                constraints(nullable: false)
            }

            column(name: "text", type: "text") {
                constraints(nullable: true)
            }
            column(name: "creator", type: "varchar(150)") {
                constraints(nullable: true)
            }
            column(name: "created_date", type: "timestamp") {
                constraints(nullable: false)
            }

        }
        addPrimaryKey( columnNames: "id", tableName: "comments", constraintName: "pk_comment" )

        rollback {
            dropTable(tableName: 'comments')
        }

    }
}