package liquibase.v1

databaseChangeLog {
    changeSet(id: '001', author: 'Vladimir Martynyuk') {
        comment "Create table users"
        createTable(tableName: "users") {
            column(name: 'id', type: 'bigint', autoIncrement: true) {
                constraints(nullable: false)
            }
            column(name: "login", type: "varchar(150)") {
                constraints(nullable: true)
            }
            column(name: "email", type: "varchar(150)") {
                constraints(nullable: true)
            }
            column(name: "password", type: "varchar(150)") {
                constraints(nullable: false)
            }
            column(name: "status", type: "varchar(10)") {
                constraints(nullable: false)
            }

        }
        addPrimaryKey( columnNames: "id", tableName: "users", constraintName: "pk_user" )

        rollback {
            dropTable(tableName: 'users')
        }

    }
}