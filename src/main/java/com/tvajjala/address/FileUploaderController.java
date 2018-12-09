package com.tvajjala.address;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
public class FileUploaderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploaderController.class);


    @PostMapping("/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file) {

        LOGGER.info("file {} ", file.getOriginalFilename());

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Files.copy(file.getInputStream(), Paths.get("myoutput.pdf"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }


        LOGGER.info("fileName {} ", fileName);
    }

}
