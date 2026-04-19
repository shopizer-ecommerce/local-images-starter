package com.shopizer.images;

/**
 * Metadata supplied when an image is saved.
 *
 * @param fileName original file name, when known
 * @param contentType image media type, when known
 */
public record ImageMetadata(String fileName, String contentType) {

    public static ImageMetadata empty() {
        return new ImageMetadata(null, null);
    }
}
