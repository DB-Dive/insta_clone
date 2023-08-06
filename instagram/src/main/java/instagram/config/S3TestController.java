package instagram.config;

import instagram.api.feed.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class S3TestController {

    private final S3Uploader s3Uploader;

    @PostMapping("/images")
    public void upload(@RequestParam("image") List<MultipartFile> multipartFile) {
        multipartFile.stream().forEach(file -> {
            try {
                s3Uploader.upload(file, "static");
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        });
        log.info("images = {}", multipartFile);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam String url){
        s3Uploader.delete(url);
    }
}
