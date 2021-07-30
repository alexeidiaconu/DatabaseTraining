import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnect {
    private Connection connection;

    public void openConnection() {
        System.out.println("Attempting to connect to database.");
        try {
            connection = DriverManager.getConnection(DbInfo.DBURL.getValue(), DbInfo.USERNAME.getValue(), DbInfo.PASS.getValue());
            //statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on initialising connection: " + e.getMessage());
        }
    }

    public void closeConnection() {
        System.out.println("Closing the database connection...");
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on initialising connection: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        if (connection == null) {
            this.openConnection();
        }
        return this.connection;
    }





   /* public void setResultSet(String sql){

        try {
            resultSet = statement.executeQuery(sql);
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error on executing SQL query: " + e.getMessage());
        }

    }

    public void processUpdate(String sqlupdate, String sqlresult){

        try {
            statement.executeUpdate(sqlupdate);
            setResultSet(sqlresult);
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error on executing SQL query: " + e.getMessage());
        }
    }

    public void processResult(){
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();

            int nrcol = rsmd.getColumnCount();

            while (resultSet.next()) {

                String row = "| ";
                for(int i=1;i <= nrcol;i++) {
                    row += resultSet.getString(i) + " | ";
                }
                System.out.println(row);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error on printing result: " + e.getMessage());
        }
    }
   public void writeResult(){
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

                String row = "";
                for(int i=1;i <= nrcol;i++) {
                    row += resultSet.getString(i) + ",";
                }
                //System.out.println("row " + row);
                out.write(row + "\n");
            }
            out.close();
            fstream.close();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error on printing result: " + e.getMessage());
        }

    }*/
}
