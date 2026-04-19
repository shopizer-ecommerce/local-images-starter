package com.shopizer.images;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Stores images by relative path.
 */
public interface ImageStore {

    StoredImage save(String path, InputStream content, ImageMetadata metadata) throws IOException;

    Optional<StoredImageResource> get(String path) throws IOException;

    boolean exists(String path) throws IOException;

    boolean delete(String path) throws IOException;

    Path resolve(String path);
}
