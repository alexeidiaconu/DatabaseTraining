import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;

public class DBUse {

    private Statement statement;
    private ResultSet resultSet;

    public void setStatement() {
        DBConnect connection = new DBConnect();
        try {
            statement = connection.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on creating statement: " + e.getMessage());
        }
    }

    public void setResultSet(String sql) {

        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on executing SQL query: " + e.getMessage());
        }

    }

    public void processUpdate(String sqlUpdate, String sqlResult) {

        try {
            statement.executeUpdate(sqlUpdate);
            setResultSet(sqlResult);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on executing SQL query: " + e.getMessage());
        }
    }

    public void processResult() {
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();

            int nrcol = rsmd.getColumnCount();

            while (resultSet.next()) {

                StringBuilder row = new StringBuilder("| ");
                for (int i = 1; i <= nrcol; i++) {
                    row.append(resultSet.getString(i)).append(" | ");
                }
                System.out.println(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on printing result: " + e.getMessage());
        }
    }

    public void writeResult() {
        String path = "C:\\Users\\alexdiaconu\\Desktop\\ExitFile.csv";
        File file = new File(path);


        try {
            FileWriter fstream = new FileWriter(file, true); // true, will append the file if already exist
            BufferedWriter out = new BufferedWriter(fstream);
            resultSet.beforeFirst();
            //resultSet.first();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int nrcol = rsmd.getColumnCount();


            while (resultSet.next()) {

                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= nrcol; i++) {
                    row.append(resultSet.getString(i)).append(",");
                }
                //System.out.println("row " + row);
                out.write(row + "\n");
            }
            out.close();
            fstream.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on printing result: " + e.getMessage());
        }

    }


}
