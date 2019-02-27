package com.Models;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper

public interface personService {

    @Select("SELECT * FROM person ORDER BY id")

    List findAll();

    @Update(

            "UPDATE person SET name=#{name}, surname=#{surname} WHERE id=#{id}")

    void update(person person);

}