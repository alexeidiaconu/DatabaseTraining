public enum PreparedQueries {
    SQLRESULT("select * from sales.orders"),
    SQLREAD("SELECT * FROM production.brands"),
    SQLUPDATE("update top (5) sales.orders\n" +
            "  set shipped_date = getdate() + 7\n" +
            "  from sales.orders as so join sales.customers as sc on so.customer_id = sc.customer_id\n" +
            "  where so.shipped_date IS NULL and sc.state = 'NY'\n" +
            "  select * from sales.orders"),
    SQLDELETE("delete top (5) from sales.orders\n" +
            " where sales.orders.customer_id in \n" +
            " (select sc.customer_id \n" +
            "  from sales.orders as so\n" +
            "  join sales.customers as sc on so.customer_id = sc.customer_id\n" +
            "  where sc.state = 'NY' and so.shipped_date IS NOT NULL )"),
    SQLINSERT("SET IDENTITY_INSERT sales.orders ON;\n"+
            "INSERT INTO sales.orders(order_id, customer_id, order_status, order_date, required_date, shipped_date, store_id,staff_id)\n" +
            "VALUES(1,259,4,'20160101','20160103','20160103',1,2)");
private final String value;

    PreparedQueries(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
