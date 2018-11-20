package top.winkin.mybatis;

import top.winkin.DB.DESPlus;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/9/28 上午11:53
 */
public class NormalTest {
    public String deleteSql() {
        SQL sql = new SQL();
        sql.SELECT("*").FROM("TB_AL046").WHERE("ID = ?").WHERE("NAME=?");
        return sql.toString();
    }

    @Test
    public void test1() {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
//        builder.build(Resources.getResourceAsStream(""))
        System.out.printf(deleteSql());
    }

    @Test
    public void decrypt() throws Exception {
        System.out.printf(DESPlus.decrypt("c3cbba45f1eef32b"));
    }
}
