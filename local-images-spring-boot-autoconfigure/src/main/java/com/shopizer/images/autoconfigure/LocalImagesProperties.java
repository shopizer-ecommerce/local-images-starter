package com.shopizer.images.autoconfigure;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("shopizer.images.local")
public class LocalImagesProperties {

    /**
     * Whether local image storage is enabled.
     */
    private boolean enabled = true;

    /**
     * Root directory where image files are stored.
     */
    private Path storagePath = Path.of("data/images");

    /**
     * Whether to create the storage root during application startup.
     */
    private boolean createDirectories = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Path getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(Path storagePath) {
        this.storagePath = storagePath;
    }

    public boolean isCreateDirectories() {
        return createDirectories;
    }

    public void setCreateDirectories(boolean createDirectories) {
        this.createDirectories = createDirectories;
    }
}
