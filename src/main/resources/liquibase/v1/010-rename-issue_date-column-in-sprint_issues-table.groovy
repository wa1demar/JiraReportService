package liquibase.v1

databaseChangeLog {
    changeSet(id: '010', author: 'Vladimir Martynyuk') {
        comment "Rename issue_date column in sprint_issues table"
        renameColumn(
                tableName: "sprint_issues",
                columnDataType: "varchar",
                oldColumnName: "issue-date",
                newColumnName: "issue_date"
        )

        rollback {
            renameColumn(
                    tableName: "sprint_issues",
                    columnDataType: "varchar",
                    oldColumnName: "issue_date",
                    newColumnName: "issue-date"
            )
        }

    }
}