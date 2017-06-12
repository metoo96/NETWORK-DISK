package com.example.demo.dao;

import com.example.demo.domain.File;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Lenovo on 2017/6/11.
 */
public interface FileDao {
    public List<File> find_file(@Param("username")String username);
    public boolean delete_file(@Param("id")int  id);
    public boolean insert_file(@Param("file")File file);
    public List<File> find_file_byname(@Param("username")String username);
}