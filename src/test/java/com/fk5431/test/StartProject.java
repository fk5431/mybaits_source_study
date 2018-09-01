package com.fk5431.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by FK on 2018/8/28.
 */
public class StartProject {

    @Test
    public void testPropertyTokenizer(){
        String str = "1";
        System.out.println(str.toString());
    }

    public static void main(String[] args) {
        String resource = "mybatis-config.xml";
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        sqlSession.getMapper(PcassignBeanMapper.class);
        sqlSession.commit();
        sqlSession.close();
    }
}
