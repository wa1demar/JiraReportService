package liquibase.v1

databaseChangeLog {
    changeSet(id: '006', author: 'Vladimir Martynyuk') {
        comment "Create table sprints"
        createTable(tableName: "sprints") {
            column(name: 'id', type: 'bigint', autoIncrement: true) {
                constraints(nullable: false)
            }

            column(name: 'report_id', type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: 'agile_sprint_id', type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: 'not_count_target', type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: "name", type: "varchar(150)") {
                constraints(nullable: true)
            }

            column(name: "state", type: "varchar(150)") {
                constraints(nullable: true)
            }

            column(name: "type", type: "varchar(150)") {
                constraints(nullable: true)
            }

            column(name: "start_date", type: "timestamp") {
                constraints(nullable: true)
            }

            column(name: "end_date", type: "timestamp") {
                constraints(nullable: true)
            }

            column(name: "complete_date", type: "timestamp") {
                constraints(nullable: true)
            }

            column(name: "show_uat", type: "bigint") {
                constraints(nullable: true)
            }

        }
        addPrimaryKey( columnNames: "id", tableName: "sprints", constraintName: "pk_sprint" )

        rollback {
            dropTable(tableName: 'sprints')
        }

    }
}