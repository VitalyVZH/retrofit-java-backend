package ru.vitalyvzh;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.vitalyvzh.db.dao.CategoriesMapper;
import ru.vitalyvzh.db.dao.ProductsMapper;
import ru.vitalyvzh.db.model.Categories;
import ru.vitalyvzh.db.model.CategoriesExample;
import ru.vitalyvzh.db.model.Products;
import ru.vitalyvzh.db.model.ProductsExample;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    private static String resource = "mybatisConfig.xml";

    public static void main(String[] args) throws IOException {

        SqlSessionFactory sqlSessionFactory;

        InputStream is = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = sqlSessionFactory.openSession(true); // true включает autocommit

        /////////////////////////////////////////////////////

        CategoriesMapper categoriesMapper = session.getMapper(CategoriesMapper.class);
        long categoriesCount = categoriesMapper.countByExample(new CategoriesExample());
        System.out.println("Количество категорий товаров: " + categoriesCount);

        /////////////////////////////////////////////////////

        ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);
        Products productWithPrimaryKey = productsMapper.selectByPrimaryKey(3115L);
        System.out.println(productWithPrimaryKey.getTitle());

        ProductsMapper productsMapper1 = session.getMapper(ProductsMapper.class);

        Products product = new Products();
        product.setId(productWithPrimaryKey.getId());
        product.setTitle("Kiwi");
        product.setPrice(567);
        product.setCategory_id(1L);

        ProductsExample productsExample = new ProductsExample();
        productsExample.createCriteria().andIdEqualTo(3115L);

        productsMapper1.updateByExample(product, productsExample);

        Products productWithPrimaryKey1 = productsMapper.selectByPrimaryKey(productWithPrimaryKey.getId());

        System.out.println(productWithPrimaryKey1.getTitle());

    }
}
