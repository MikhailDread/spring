package com.example.MyBookShopApp.data.dao;

import com.example.MyBookShopApp.data.dto.TestEntity;
import org.springframework.stereotype.Repository;

@Repository
public class TestEntityDao extends AbstractHibernateDao<TestEntity>{
    public TestEntityDao() {
        super();
        setClazz(TestEntity.class);
    }
}
