package dialect;

import org.hibernate.dialect.MySQLInnoDBDialect;

/**
 * InnoDB, UTF-8 dialect for Mysql
 */
public class MySQLDialect extends MySQLInnoDBDialect {

    public MySQLDialect() {
        super();
        this.registerColumnType(-2, "varchar($l)");
    }

    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
    
}