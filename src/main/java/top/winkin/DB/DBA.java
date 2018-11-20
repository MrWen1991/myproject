package top.winkin.DB;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.SQL;
import top.winkin.util.JsonUtil;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

/**
 * @Description: TODO db工具类，链式拼接sql，未完成
 * @Author: wenjiajia
 * @Data: 2018/9/29 上午9:28
 */
public class DBA extends SQL {
    private static final String configure = "mybatis.properties";
    private static DataSource dataSource;
    private static List parames = new ArrayList();

    static {
        init();
    }

    private static void init() {
        try {
            Properties properties = new Properties();
            InputStream in = Resources.getResourceAsStream(configure);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            properties.load(br);
            String passwd = null;
            passwd = DESPlus.decrypt(properties.getProperty("password"));//w3ojvIFh3B/C4Yps98aU5w==
            properties.setProperty("password", passwd);
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement parareStatement() throws SQLException {
        String sql = super.toString();
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        for (int i = 0; i < parames.size(); i++) {
            Object value = parames.get(i);
            if (null == value) {
                statement.setObject(i + 1, null);
            } else if (value instanceof Double) {
                statement.setDouble(i + 1, (Double) value);
            } else if (value instanceof java.math.BigDecimal) {
                statement.setBigDecimal(i + 1, new BigDecimal(value.toString()));
            } else if (value instanceof Float) {
                statement.setDouble(i + 1, Float.valueOf(value.toString()));
            } else if (value instanceof Long) {
                statement.setLong(i + 1, Long.valueOf(value.toString()));
            } else if (value instanceof Number || value instanceof Integer) {
                statement.setBigDecimal(i + 1, new BigDecimal(value.toString()));
            } else if (value instanceof java.sql.Date) {
                statement.setTimestamp(i + 1, new java.sql.Timestamp(((java.sql.Date) value).getTime()));
            } else if (value instanceof java.util.Date) {
                statement.setTimestamp(i + 1, new java.sql.Timestamp(((java.util.Date) value).getTime()));
            } else {// String
                statement.setString(i + 1, value.toString());
            }
        }
        return statement;
    }

    public List parames() {
        return parames;
    }

    public DBA WHERE(String conditions, Object param) {
        super.WHERE(conditions);
        this.parames().add(param);
        return this;
    }


    public List<Map> getMultiRowMap() throws SQLException {
        PreparedStatement statement = parareStatement();
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        ResultSetMetaData rsmd = resultSet.getMetaData();
        List rowList = new ArrayList();
        while (resultSet.next()) {
            Map rowMap = new HashMap();
            for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
                rowMap.put(rsmd.getColumnName(i), resultSet.getObject(i));
            }
            rowList.add(rowMap);
        }
        return rowList;

    }


    public static void main(String[] args) throws SQLException {
        DBA db = new DBA();
        db.SELECT("*").FROM("TB_AL046");
        db.WHERE("ROWNUM < ?", 10);
        System.out.printf(db.toString());
        List result = db.getMultiRowMap();
        System.out.printf("\n" + JsonUtil.dump(result));


    }
}
