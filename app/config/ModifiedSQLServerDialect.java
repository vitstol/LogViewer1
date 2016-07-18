package config;

import org.hibernate.dialect.SQLServer2008Dialect;

import java.sql.Types;

public class ModifiedSQLServerDialect extends SQLServer2008Dialect {
    public ModifiedSQLServerDialect(){
        super();
        registerColumnType(Types.TIMESTAMP,"datetime");
    }
}
