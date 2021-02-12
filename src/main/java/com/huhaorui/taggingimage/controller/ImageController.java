package com.huhaorui.taggingimage.controller;


import com.huhaorui.taggingimage.common.ApiResponse;
import com.huhaorui.taggingimage.common.Responses;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Component
@Controller(value = "/image")
public class ImageController {
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            System.out.printf("IO异常：%s%n", e.getMessage());
            throw e;
        } finally {
            if (null != bos) {
                bos.close();
            }
        }
        return bos.toByteArray();
    }

    @RequestMapping(value = "/image/get/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImageById(@PathVariable String id) throws IOException {
        return getImage(id);
    }

    /**
     * 读取服务器上的图片。
     *
     * @param id uuid值,将会以book.cover形式存取在数据库中
     * @return 操作的结果
     */
    @RequestMapping(value = "/image/get", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(String id) throws IOException {
        id = new String(Base64.getEncoder().encode(id.getBytes(StandardCharsets.UTF_8)));
        File file = new File("image/" + id);
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            inputStream = new FileInputStream("image/404.jpg");
        }
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

    /**
     * 在本地/服务器上保存图片。文件位置为image/+对uuid按照Base64进行编码
     *
     * @param file 上传的图片
     * @return 操作的结果
     */
    @PostMapping(value = "image/put")
    @ResponseBody
    public ApiResponse<Object> putImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Responses.fail("文件为空");
        }
        UUID uuid = UUID.randomUUID();
        Path path = Paths.get("image/" + new String(Base64.getEncoder().encode(uuid.toString().getBytes(StandardCharsets.UTF_8))));
        try {
            file.transferTo(path);
        } catch (IOException e) {
            e.printStackTrace();
            return Responses.fail("文件保存失败");
        }
        return Responses.ok(uuid);
    }

}


