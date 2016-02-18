package liquibase.v1

databaseChangeLog {
    changeSet(id: '003', author: 'Vladimir Martynyuk') {
        comment "Create table configs"
        createTable(tableName: "configs") {
            column(name: 'id', type: 'bigint', autoIncrement: true) {
                constraints(nullable: false)
            }

            column(name: "base_url", type: "varchar(150)") {
                constraints(nullable: true)
            }

            column(name: "jira_user", type: "varchar(150)") {
                constraints(nullable: true)
            }

            column(name: "story_point_name", type: "varchar(150)") {
                constraints(nullable: true)
            }

            column(name: "agile_done_name", type: "varchar(150)") {
                constraints(nullable: true)
            }

            column(name: "jira_dev_group_name", type: "varchar(150)") {
                constraints(nullable: true)
            }

            column(name: "bug_name", type: "varchar(150)") {
                constraints(nullable: true)
            }

            column(name: "path_to_ajax", type: "varchar(150)") {
                constraints(nullable: true)
            }

            column(name: "path_to_agile_rest", type: "varchar(150)") {
                constraints(nullable: true)
            }

            column(name: "non_working_days", type: "varchar(150)") {
                constraints(nullable: true)
            }

            column(name: "auto_sync_time", type: "varchar(150)") {
                constraints(nullable: true)
            }

        }
        addPrimaryKey( columnNames: "id", tableName: "configs", constraintName: "pk_config" )

        rollback {
            dropTable(tableName: 'configs')
        }

    }
}