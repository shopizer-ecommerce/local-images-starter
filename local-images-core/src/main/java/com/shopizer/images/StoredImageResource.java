package com.shopizer.images;

import java.io.IOException;
import java.io.InputStream;

/**
 * Stored image content and metadata.
 */
public final class StoredImageResource implements AutoCloseable {

    private final StoredImage image;
    private final InputStream content;

    public StoredImageResource(StoredImage image, InputStream content) {
        this.image = image;
        this.content = content;
    }

    public StoredImage image() {
        return image;
    }

    public InputStream content() {
        return content;
    }

    @Override
    public void close() throws IOException {
        content.close();
    }
}
