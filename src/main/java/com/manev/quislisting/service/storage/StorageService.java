package com.manev.quislisting.service.storage;

import com.manev.quislisting.domain.AttachmentStreamResource;
import com.manev.quislisting.service.exception.AttachmentStreamResourceException;
import com.manev.quislisting.service.post.dto.AttachmentDTO;
import com.manev.quislisting.service.storage.components.StoreComponent;
import com.manev.quislisting.service.util.ImageResizeUtil;
import com.manev.quislisting.service.util.ImageWatermarkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.jcr.RepositoryException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class StorageService {

    public static final String DL_MEDIUM = "dl-medium";
    private static final String DL_MEDIUM_SIZE = "800x600";
    public static final String DL_BIG = "dl-big";
    private static final String DL_BIG_SIZE = "1024x768";
    public static final String DL_THUMBNAIL = "dl-thumbnail";
    public static final String DL_THUMBNAIL_SIZE = "242x200";
    private final Logger logger = LoggerFactory.getLogger(StorageService.class);
    private StoreComponent storeComponent;

    public StorageService(StoreComponent storeComponent) {
        this.storeComponent = storeComponent;
    }

    public AttachmentStreamResource loadAsResource(String filename) {
        try {
            return storeComponent.getResource(filename);
        } catch (RepositoryException | IOException e) {
            logger.error("Resource {} cannot be retrieved", filename);
            throw new AttachmentStreamResourceException("Resource cannot be retrieved", e);
        }
    }

    public AttachmentDTO store(MultipartFile file) throws IOException, RepositoryException {
        InputStream watermarkStream = this.getClass().getClassLoader()
                .getResourceAsStream("images/ql-logo-01-50x50.png");
        BufferedImage inputWatermarked = ImageWatermarkUtil.addImageWatermark(watermarkStream, file.getInputStream());

        ResizedImages resizedImages = new ResizedImages();
        resizedImages.setThumbnail(resizeImage(DL_THUMBNAIL_SIZE, inputWatermarked));
        resizedImages.setMedium(resizeImage(DL_MEDIUM_SIZE, inputWatermarked));
        resizedImages.setBig(resizeImage(DL_BIG_SIZE, inputWatermarked));

        return storeComponent.storeInRepository(file, inputWatermarked, resizedImages);
    }

    private BufferedImage resizeImage(String size, BufferedImage inputWatermarked) {
        String[] sizesArr = size.split("x");
        Integer width = Integer.valueOf(sizesArr[0]);
        Integer height = Integer.valueOf(sizesArr[1]);
        if (inputWatermarked.getWidth() >= width || inputWatermarked.getHeight() >= height) {
            return ImageResizeUtil.resizeImage(inputWatermarked, width, height);
        }
        return null;
    }

    public void delete(List<String> filePaths) throws IOException, RepositoryException {
        storeComponent.removeInRepository(filePaths);
    }
}
