public class ConnectUrl {
        public static void main(String[] args) {


                DBUse Db = new DBUse();
                Db.setStatement();
                Db.setResultSet(PreparedQueries.SQLREAD.getValue());
                Db.processResult();
                Db.writeResult();

                Db.processUpdate(PreparedQueries.SQLUPDATE.name(),PreparedQueries.SQLRESULT.getValue());
                Db.processResult();
                Db.writeResult();

                Db.processUpdate(PreparedQueries.SQLDELETE.getValue(),PreparedQueries.SQLRESULT.getValue());
                Db.processResult();
                Db.writeResult();

                Db.processUpdate(PreparedQueries.SQLINSERT.getValue(), PreparedQueries.SQLRESULT.getValue());
                Db.processResult();
                Db.writeResult();



        }
}