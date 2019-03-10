package com.Models;

import com.MyUI;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


import java.util.List;

@Mapper

public interface personService {

    public static List<person> findAll(){
        try(SqlSession session =
                MyBatisService.getSqlSessionFactory().openSession()){
            personMapper mapper =
                    session.getMapper(personMapper.class);
            return mapper.findAll();
        }
    }

    public static void save(person person){
        try(SqlSession session =
                    MyBatisService.getSqlSessionFactory().openSession()){
            personMapper mapper = session.getMapper(personMapper.class);
            mapper.save(person);
            session.commit();
        }
    }





/*
    @Select("SELECT * FROM person ORDER BY id")
    List<person> findAll();



    @Update(

            "UPDATE person SET name=#{name}, surname=#{surname} WHERE id=#{id}")

    void update(person person);
*/
}


