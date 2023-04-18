package com.team5.capstone.mju.apiserver.web.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

@Service
public class AmazonS3Service {

    @Autowired
    private AmazonS3 s3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImageOriginalFileName(String prefixPath, MultipartFile multipartFile) {
        return uploadImage(prefixPath, multipartFile.getOriginalFilename(), multipartFile);
    }

    public String uploadImage(String prefixPath, String specifiedFileName, MultipartFile multipartFile) {
        byte[] bytes = new byte[0];
        try {
            bytes = IOUtils.toByteArray(multipartFile.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        String uploadPathFile = prefixPath + specifiedFileName;

        ObjectMetadata metadata = new ObjectMetadata();
        String fileExtension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());

        if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg")) {
            metadata.setContentType(MediaType.IMAGE_JPEG_VALUE);
        }
        else if (fileExtension.equalsIgnoreCase("png")) {
            metadata.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        metadata.setContentLength(bytes.length);

        s3.putObject(new PutObjectRequest(bucket, uploadPathFile, byteArrayInputStream, metadata).withCannedAcl(CannedAccessControlList.PublicRead));
        return s3.getUrl(bucket, uploadPathFile).toString();
    }
}
