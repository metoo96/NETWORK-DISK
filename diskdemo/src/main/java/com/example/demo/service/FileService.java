package com.example.demo.service;

import com.example.demo.dao.FileDao;
import com.example.demo.domain.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Lenovo on 2017/6/11.
 */
@Service
public class FileService {
    @Autowired
    FileDao filedao;
    public List<File> find_file(String username){
        return filedao.find_file(username);
    }
    public boolean insert_file(File file){
        return filedao.insert_file(file);
    }
    public boolean delete_file(int id){
        return filedao.delete_file(id);
    }
    public List<File> find_file_byname(String username){
        return filedao.find_file_byname(username);
    }
}
