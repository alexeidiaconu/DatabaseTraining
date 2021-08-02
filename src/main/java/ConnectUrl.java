public class ConnectUrl {
        public static void main(String[] args) {

                String outputPath = System.getProperty("user.dir") + "\\WorkFiles\\ExitFile.csv";
                String inputPath = System.getProperty("user.dir") + "\\WorkFiles\\inputFile.csv";
                String tableName = "sales.customersdest";
                DBUse Db = new DBUse();
                Db.openConnection();

                Db.setStatement(PreparedQueries.SQLRESULT1.getValue());
                Db.processUpdate(PreparedQueries.SQLINIT.getValue(),PreparedQueries.SQLRESULT.getValue());
                Db.processUpdate(PreparedQueries.SQLINIT1.getValue(),PreparedQueries.SQLRESULT1.getValue());


                Db.setStatement(PreparedQueries.SQLRESULT1.getValue());
                Db.processResult();
                Db.writeResult(outputPath);

                Db.processUpdate(PreparedQueries.SQLUPDATE.getValue(),PreparedQueries.SQLRESULT.getValue());
                Db.processResult();
                Db.writeResult(outputPath);

               Db.processUpdate(PreparedQueries.SQLDELETE.getValue(),PreparedQueries.SQLRESULT.getValue());
                Db.processResult();
                Db.writeResult(outputPath);


                Db.processUpdate(PreparedQueries.SQLINSERT.getValue(), PreparedQueries.SQLRESULT.getValue());
                Db.processResult();
                Db.writeResult(outputPath);

                Db.bulkInsert(tableName,inputPath);
                Db.closeConnection();


        }
}