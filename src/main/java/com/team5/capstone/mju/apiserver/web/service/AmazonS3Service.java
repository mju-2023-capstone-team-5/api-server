package com.team5.capstone.mju.apiserver.web.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.team5.capstone.mju.apiserver.web.exceptions.IllegalImageFormatException;
import com.team5.capstone.mju.apiserver.web.exceptions.UploadImageCannotConvertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class AmazonS3Service {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImage(String prefixPath, String imageName, MultipartFile multipartFile) {
        byte[] bytes = new byte[0];
        try {
            bytes = IOUtils.toByteArray(multipartFile.getInputStream());
        } catch (IOException e) {
            throw new UploadImageCannotConvertException();
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        ObjectMetadata metadata = new ObjectMetadata();
        String fileExtension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        String uploadPathFile = prefixPath + imageName + "." + fileExtension;

        if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg")) {
            metadata.setContentType(MediaType.IMAGE_JPEG_VALUE);
        }
        else if (fileExtension.equalsIgnoreCase("png")) {
            metadata.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        else throw new IllegalImageFormatException();
        metadata.setContentLength(bytes.length);

        s3Client.putObject(new PutObjectRequest(bucket, uploadPathFile, byteArrayInputStream, metadata).withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, uploadPathFile).toString();
    }

    public ArrayList<String> uploadImages(String prefixPath, String imageName, MultipartFile[] multipartFiles) {
        ArrayList<String> urls = new ArrayList<>();
        int index = 0;
        for (MultipartFile file : multipartFiles) {
            urls.add(uploadImage(prefixPath, imageName + "-" + (++index), file));
        }
        return urls;
    }
}
