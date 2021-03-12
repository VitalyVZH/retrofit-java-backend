package ru.vitalyvzh.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.vitalyvzh.db.dao.CategoriesMapper;
import ru.vitalyvzh.db.dao.ProductsMapper;

import java.io.IOException;
import java.io.InputStream;

@UtilityClass
public class DbUtils {

    private static String resource = "mybatisConfig.xml";

    @SneakyThrows
    public static CategoriesMapper getCategoryMapper() {
        return getSqlSession().getMapper(CategoriesMapper.class);
    }


    @SneakyThrows
    public static ProductsMapper getProductsMapper() {
        return getSqlSession().getMapper(ProductsMapper.class);
    }

    private static SqlSession getSqlSession() throws IOException {
        SqlSessionFactory sqlSessionFactory;
        InputStream is = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        return sqlSessionFactory.openSession(true);
    }
}
