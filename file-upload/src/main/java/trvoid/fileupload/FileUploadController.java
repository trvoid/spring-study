package trvoid.fileupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import trvoid.fileupload.storage.StorageService;

@RestController
public class FileUploadController {
    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/file/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("fileUsage") String fileUsage) {
        storageService.store(file);

        System.out.println(String.format("File Usage: %s", fileUsage));

        return "success";
    }
}
