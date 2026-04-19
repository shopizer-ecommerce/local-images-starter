package com.shopizer.images;

import java.time.Instant;

/**
 * Stored image details.
 *
 * @param path relative image path inside the configured storage root
 * @param fileName original file name, when known
 * @param contentType image media type, when known
 * @param contentLength size in bytes
 * @param lastModified last modification time
 */
public record StoredImage(
        String path,
        String fileName,
        String contentType,
        long contentLength,
        Instant lastModified) {
}
