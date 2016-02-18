package liquibase.v1

databaseChangeLog {
    changeSet(id: '004', author: 'Vladimir Martynyuk') {
        comment "Create table admin_reports"
        createTable(tableName: "admin_reports") {
            column(name: 'id', type: 'int', autoIncrement: true) {
                constraints(nullable: false)
            }

            column(name: 'report_id', type: 'int', autoIncrement: true) {
                constraints(nullable: false)
            }

            column(name: "name", type: "varchar(150)") {
                constraints(nullable: true)
            }

            column(name: "display_name", type: "varchar(150)") {
                constraints(nullable: true)
            }

        }
        addPrimaryKey( columnNames: "id", tableName: "admin_reports", constraintName: "pk_admin_report" )

        rollback {
            dropTable(tableName: 'admin_reports')
        }

    }
}