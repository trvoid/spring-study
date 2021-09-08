package trvoid.fileupload.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void init();
    public void deleteAll();
    void store(MultipartFile file);
}
