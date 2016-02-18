package liquibase.v1

databaseChangeLog {
    changeSet(id: '002', author: 'Vladimir Martynyuk') {
        comment "Create table comments"
        createTable(tableName: "comments") {
            column(name: 'id', type: 'bigint', autoIncrement: true) {
                constraints(nullable: false)
            }

            column(name: 'report_id', type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: 'sprint_id', type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: "text", type: "text") {
                constraints(nullable: true)
            }
            column(name: "creator", type: "varchar(150)") {
                constraints(nullable: true)
            }
            column(name: "created_date", type: "timestamp") {
                constraints(nullable: true)
            }

        }
        addPrimaryKey( columnNames: "id", tableName: "comments", constraintName: "pk_comment" )

        rollback {
            dropTable(tableName: 'comments')
        }

    }
}