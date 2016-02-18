package liquibase.v1

databaseChangeLog {
    changeSet(id: '005', author: 'Vladimir Martynyuk') {
        comment "Create table reports"
        createTable(tableName: "reports") {
            column(name: 'id', type: 'bigint', autoIncrement: true) {
                constraints(nullable: false)
            }

            column(name: "title", type: "varchar(150)") {
                constraints(nullable: true)
            }

            column(name: "url", type: "varchar(150)") {
                constraints(nullable: true)
            }

            column(name: "creator", type: "varchar(150)") {
                constraints(nullable: true)
            }

            column(name: "image", type: "varchar(150)") {
                constraints(nullable: true)
            }

            column(name: "board_id", type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: "creator_id", type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: "created_date", type: "timestamp") {
                constraints(nullable: true)
            }

            column(name: "updated_date", type: "timestamp") {
                constraints(nullable: true)
            }

            column(name: "sync_date", type: "timestamp") {
                constraints(nullable: true)
            }

            column(name: "is_closed", type: "boolean") {
                constraints(nullable: true)
            }

            column(name: "closed_date", type: "timestamp") {
                constraints(nullable: true)
            }

            column(name: "type_id", type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: "target_points", type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: "target_hours", type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: "target_qat_defect_min", type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: "target_qat_defect_max", type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: "target_qat_defect_hours", type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: "target_uat_defect_min", type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: "target_uat_defect_max", type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: "target_uat_defect_hours", type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: "actual_points", type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: "actual_hours", type: "double") {
                constraints(nullable: true)
            }

            column(name: "actual_qat_defect_points", type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: "actual_qat_defect_hours", type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: "actual_uat_defect_points", type: 'bigint') {
                constraints(nullable: true)
            }

            column(name: "actual_uat_defect_hours", type: 'bigint') {
                constraints(nullable: true)
            }
        }
        addPrimaryKey( columnNames: "id", tableName: "reports", constraintName: "pk_report" )

        rollback {
            dropTable(tableName: 'reports')
        }

    }
}