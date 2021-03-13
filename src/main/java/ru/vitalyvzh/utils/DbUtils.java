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

    public static CategoriesMapper getCategoryMapper() throws IOException {
        return getSqlSession().getMapper(CategoriesMapper.class);
    }

    public static ProductsMapper getProductsMapper() throws IOException {

        return getSqlSession().getMapper(ProductsMapper.class);
    }

    private static SqlSession getSqlSession() throws IOException {
        InputStream is = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = sqlSessionFactory.openSession(true);
        return session;
    }
}
