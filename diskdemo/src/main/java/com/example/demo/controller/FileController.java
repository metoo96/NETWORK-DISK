package com.example.demo.controller;

import com.example.demo.service.FileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class FileController {
    @Autowired
    FileService fileservice;
    /**
     *显示个人上传页面
     */
    @RequestMapping(value = "/File/index", method = RequestMethod.GET)
    public String file(Model model, @CookieValue("userid")String userid) {
        //先判断cookie是否存在并判断cookie是否对应用户名正确
        if(userid==null||("".equals(userid.trim()))){
            return "redirect:/login";
        }else {
            if(userid.equals(userid)) {
                String path = "d:/documents/" + userid;
                File dest = new File(path);
                if (dest.getParentFile().exists()) {
                    List<com.example.demo.domain.File> list = fileservice.find_file(userid);//文件的结果集
                    model.addAttribute("list", list);
                }
                model.addAttribute("name", userid);
                return "file";
            }else{
                return "redirect:/login";
            }
        }
    }
    /**
     *上传文件
     */
    @RequestMapping(value = "/File/upload", method = RequestMethod.POST)
    public String upload(HttpServletRequest request, @RequestParam("filename") MultipartFile file1, com.example.demo.domain.File file, @CookieValue("userid")String userid) {
        String username=userid;
        Date date=new Date();
        SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=simple.format(date);
        if (file1.isEmpty()) {
            return "false";
        }
        // 获取文件名
        String fileName = file1.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件上传路径
        String filePath = "d:/documents/"+username+"/";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file1.transferTo(dest);
            file.setUsername(username);//设置文件提交姓名
            file.setTime(time);//设置文件提交时间
            file.setFilepath("/"+username+"/"+fileName);//设置文件提交路径
            fileservice.insert_file(file);
            return "redirect:/File/index";//刷新个人文件页面
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false";
    }
    /**
     *  用来下载文件 用get的方式获取
     */
    @RequestMapping(value="/{username}/{filename:.+}", method=RequestMethod.GET)
    public void download(@PathVariable("username")String username, @PathVariable("filename") String filename, HttpServletRequest request, HttpServletResponse response) {
        // Get your file stream from wherever.
        String fullPath = "d:/documents/"+username+"/"+filename;
        File downloadFile = new File(fullPath);
        ServletContext context = request.getServletContext();
        // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
            System.out.println("context getMimeType is null");
        }
        System.out.println("MIME type: " + mimeType);
        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);
        // Copy the stream to the response's output strame.
        try {
            java.io.InputStream myStream = new FileInputStream(fullPath);
            IOUtils.copy(myStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *删除文件
     */
    @RequestMapping(value="/File/delete",method=RequestMethod.GET)
    public String delete(@RequestParam("id")int id,HttpServletRequest request,@CookieValue("userid")String userid){
        String username= userid;
        if(fileservice.delete_file(id)){
            return "redirect:/File/index";//刷新个人文件页面
        }else{
            return "false";
        }
    }

    /**
     *搜索文件页面
     */
    @RequestMapping(value="/File/search",method=RequestMethod.GET)
    public String search(Model model,@CookieValue("userid")String userid){
        String username=userid;
        model.addAttribute("username",username);
        return "searchfile";
    }
    @RequestMapping(value="/File/search",method=RequestMethod.POST)
    public String searchfile(@RequestParam("name")String name,Model model){
        List<com.example.demo.domain.File> list=fileservice.find_file_byname(name);
        model.addAttribute("list",list);
        return "searchfile";
    }
    @RequestMapping(value="/File/logout",method=RequestMethod.GET)
    public String logout(HttpServletResponse response){
        Cookie cookie=new Cookie("userid",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/login";
    }
}