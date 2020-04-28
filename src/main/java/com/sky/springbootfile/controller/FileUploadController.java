package com.sky.springbootfile.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class FileUploadController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 文件上传类
     * 文件会自动绑定到MultipartFile中
     *
     * @param request     获取请求信息
     * @param description 文件描述
     * @param file        上传的文件
     * @return 上传成功或失败结果
     * @throws IOException
     * @throws IllegalStateException
     */
    @PostMapping("/upload")
    @ResponseBody
    public String upload(HttpServletRequest request,
                         @RequestParam("description") String description,
                         @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {

        // 获取文件描述参数 description，纯粹测试使用
        System.out.println("description = " + description);

        // 测试MultipartFile接口的各个方法
        System.out.println("文件类型ContentType=" + file.getContentType());
        System.out.println("文件组件名称Name=" + file.getName());
        System.out.println("文件原名称OriginalFileName=" + file.getOriginalFilename());
        System.out.println("文件大小Size=" + file.getSize() / 1024 + "KB");

        // 如果文件不为空，写入上传路径，进行文件上传
        if (!file.isEmpty()) {

            // 构建上传文件的存放路径
//            String path = request.getServletContext().getRealPath("/upload/");

            String path ="E://file/upload";
            System.out.println("path = " + path);

            // 获取上传的文件名称，并结合存放路径，构建新的文件名称
            String filename = file.getOriginalFilename();
            File filepath = new File(path, filename);

            // 判断路径是否存在，不存在则新创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }

            // 将上传文件保存到目标文件目录
            file.transferTo(new File(path + File.separator + filename));
            return "success";
        } else {
            return "error";
        }
    }

}
