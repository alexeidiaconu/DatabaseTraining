public enum DbInfo {
    DBURL("jdbc:sqlserver://localhost\\MSSQLSERVER:1433;databaseName=Tutorial1"),
    USERNAME("sa"),
    PASS("Miha10Gabi11");

    private final String value;

    DbInfo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}