package liquibase.v1

databaseChangeLog {
    changeSet(id: '008', author: 'Vladimir Martynyuk') {
        comment "Create table sprint_teams"
        createTable(tableName: "sprint_teams") {

            column(name: 'id', type: 'bigint', autoIncrement: true) {
                constraints(nullable: false)
            }

            column(name: 'report_id', type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: 'agile_sprint_id', type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: 'dev_name', type: 'varchar') {
                constraints(nullable: true)
            }

            column(name: 'engineer_level', type: 'int') {
                constraints(nullable: true)
            }

            column(name: 'participation_level', type: 'int') {
                constraints(nullable: true)
            }

            column(name: 'days_in_sprint', type: 'int') {
                constraints(nullable: true)
            }

            column(name: 'target_points', type: 'int') {
                constraints(nullable: true)
            }

            column(name: 'target_hours', type: 'int') {
                constraints(nullable: true)
            }

            column(name: 'defect_min', type: 'int') {
                constraints(nullable: true)
            }

            column(name: 'defect_max', type: 'int') {
                constraints(nullable: true)
            }

            column(name: 'defect_hours', type: 'int') {
                constraints(nullable: true)
            }

            column(name: 'uat_defect_min', type: 'int') {
                constraints(nullable: true)
            }

            column(name: 'uat_defect_max', type: 'int') {
                constraints(nullable: true)
            }

            column(name: 'uat_defect_hours', type: 'int') {
                constraints(nullable: true)
            }


        }
        addPrimaryKey( columnNames: "id", tableName: "sprint_teams", constraintName: "pk_sprint_team" )

        rollback {
            dropTable(tableName: 'sprint_teams')
        }

    }
}