package com.caohao.weichat.util;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 */
@Component("fdfsUtil")
public class FastDFSUtil {
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    /**
     * 将传过来的multipartFile解析出上传fdfs所需要的属性
     * 并调用upload方法上传
     */
    public  String perfixMultipartFileToUploadImage(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        long fileSize = file.getSize();
        System.out.println(extension+"--"+fileSize+"--"+bytes);
        System.out.println(bytes==null);
        String uri = uploadImage(bytes,extension,fileSize);
        return uri;
    }
    /**
     * 将传过来的文件存到fastdfs服务器，并且返回可以直接访问图片的链接
     */
    public  String uploadImage(byte[] bytes,String extension,long fileSize) throws FileNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        System.out.println(fastFileStorageClient==null);
        StorePath storePath = fastFileStorageClient.uploadFile(byteArrayInputStream, fileSize, extension, null);
        System.out.println(storePath.getGroup() + "===" + storePath.getPath() + "======" + storePath.getFullPath());
        return storePath.getFullPath();
    }
    /**
     * 删除图片
     */
    public  boolean deleteImage(String uri){
        try {
            fastFileStorageClient.deleteFile(uri);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取缩略图url
     */
    public  String getThumpUrl(String url){
        String[] split = url.split(".");
        return split[0]+"_60x60."+split[1];

    }

}
