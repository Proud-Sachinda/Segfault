package com.Models;


import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper

public interface personMapper {




    @Select("SELECT t.name FROM public.person t")
    List<person> findAll();



    /*

    @Select("SELECT * FROM person ORDER BY id")
    List<person> findAll();
*/

/*
    @Select("SELECT id. content FROM person")
    List<person> findAll();
*/

    @Insert("INSERT INTO person(content) VALUES (#{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(person person);


/*

    @Update(

            "UPDATE person SET name=#{name}, surname=#{surname} WHERE id=#{id}")

    void update(person person);
*/
}
