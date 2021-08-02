import com.microsoft.sqlserver.jdbc.SQLServerBulkCSVFileRecord;
import com.microsoft.sqlserver.jdbc.SQLServerBulkCopy;
import com.microsoft.sqlserver.jdbc.SQLServerBulkCopyOptions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;


public class DBUse {

    private PreparedStatement statement;
    private ResultSet resultSet;
    private DBConnect connection;

    public void openConnection(){
        this.connection = new DBConnect();
        this.connection.getConnection();
    }

    public void setStatement(String SQL) {
        try {
            this.statement = connection.getConnection().prepareStatement(SQL,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on creating statement: " + e.getMessage());
        }
    }

    public void closeConnection() {
        this.connection.closeConnection();
    }

    public void setResultSet() {

        try {
            this.resultSet = this.statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on executing SQL query: " + e.getMessage());
        }

    }


    public void processResult() {
        try {
            this.setResultSet();

            int nrCol = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {

                StringBuilder row = new StringBuilder("| ");
                for (int i = 1; i <= nrCol; i++) {
                    row.append(resultSet.getString(i)).append(" | ");
                }
                System.out.println(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on printing result: " + e.getMessage());
        }
    }
    public void writeResult(String resultPath) {
        File file = new File(resultPath);


        try {
            FileWriter fileStream = new FileWriter(file, true); // true, will append the file if already exist
            BufferedWriter out = new BufferedWriter(fileStream);

            this.setResultSet();
            resultSet.beforeFirst();

            int nrCol = statement.getMetaData().getColumnCount();

            while (resultSet.next()) {

                StringBuilder fileRow = new StringBuilder();
                for (int i = 1; i <= nrCol; i++) {
                    fileRow.append(resultSet.getString(i)).append(",");
                }
                //System.out.println("row " + row);
                out.write(fileRow + "\n");
            }
            out.close();
            fileStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on printing result: " + e.getMessage());
        }

    }
    public void processUpdate(String sqlUpdate , String sqlResult) {

        try {
            this.setStatement(sqlUpdate);
            int modRows = statement.executeUpdate();
            this.setStatement(sqlResult);
            //this.setResultSet();
            String currentOp = sqlUpdate.substring(0,sqlUpdate.indexOf(' '));
            if(currentOp.contains("SET")) {currentOp = "Insert";}
            System.out.println(currentOp + " operation successful! " + modRows + " rows affected");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on executing Update operation: " + e.getMessage());
        }
    }

    public void bulkInsert(String tableName, String inputPath) {
        SQLServerBulkCSVFileRecord fileRecord;

        String initQuery = "select top(1) * from " + tableName;

        try {
            fileRecord = new SQLServerBulkCSVFileRecord(inputPath, "UTF-8", ",", true);
            this.setStatement(initQuery);
            ResultSetMetaData bulkMetaData = statement.executeQuery().getMetaData();
            int nrCol = bulkMetaData.getColumnCount();

                for (int tableCount = 1; tableCount <= nrCol; tableCount++) {
                    fileRecord.addColumnMetadata(tableCount,
                            bulkMetaData.getColumnName(tableCount),
                            bulkMetaData.getColumnType(tableCount),
                            bulkMetaData.getPrecision(tableCount),
                            0);
                }

            SQLServerBulkCopyOptions copyOptions = new SQLServerBulkCopyOptions();
            copyOptions.setBatchSize(500);
            copyOptions.setTableLock(true);
            copyOptions.setKeepIdentity(true);

            SQLServerBulkCopy bulkCopy = new SQLServerBulkCopy(statement.getConnection());
            bulkCopy.setBulkCopyOptions(copyOptions);
            bulkCopy.setDestinationTableName(tableName);

            Date dataStart = new Date();

            bulkCopy.writeToServer(fileRecord);

            Date dataEnd = new Date();
            long diffMs = dataEnd.getTime() - dataStart.getTime();
            System.out.println("Bulk Insert completed in: "  + diffMs + " Ms");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Bulk Insert: " + e.getMessage());
        }
    }

}
