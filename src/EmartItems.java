
public class EmartItems {
	static String create_table_sql = "CREATE TABLE EmartItems " +
            "(stockno INTEGER not NULL, " +
            " name CHAR(20), " + 
            " price INTEGER, " +
            " quantity INTEGER, " +
            " PRIMARY KEY ( stockno ))";
}
