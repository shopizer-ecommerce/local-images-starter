package com.shopizer.images;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * Filesystem-backed image store.
 */
public final class LocalImageStore implements ImageStore {

    private final Path root;

    public LocalImageStore(Path root) {
        this.root = Objects.requireNonNull(root, "root").toAbsolutePath().normalize();
    }

    public Path root() {
        return root;
    }

    @Override
    public StoredImage save(String path, InputStream content, ImageMetadata metadata) throws IOException {
        Objects.requireNonNull(content, "content");
        ImageMetadata imageMetadata = metadata == null ? ImageMetadata.empty() : metadata;
        Path target = resolve(path);
        Files.createDirectories(target.getParent());

        Path tempFile = Files.createTempFile(target.getParent(), ".upload-", ".tmp");
        try {
            Files.copy(content, tempFile, StandardCopyOption.REPLACE_EXISTING);
            moveIntoPlace(tempFile, target);
        } catch (IOException ex) {
            Files.deleteIfExists(tempFile);
            throw ex;
        }

        return describe(path, target, imageMetadata);
    }

    @Override
    public Optional<StoredImageResource> get(String path) throws IOException {
        Path target = resolve(path);
        if (!Files.isRegularFile(target)) {
            return Optional.empty();
        }

        StoredImage image = describe(path, target, ImageMetadata.empty());
        return Optional.of(new StoredImageResource(image, Files.newInputStream(target)));
    }

    @Override
    public boolean exists(String path) throws IOException {
        return Files.isRegularFile(resolve(path));
    }

    @Override
    public boolean delete(String path) throws IOException {
        return Files.deleteIfExists(resolve(path));
    }

    @Override
    public Path resolve(String path) {
        String normalizedPath = normalizePath(path);
        Path resolved = root.resolve(normalizedPath).normalize();
        if (!resolved.startsWith(root)) {
            throw new IllegalArgumentException("Image path must stay inside the storage root");
        }
        return resolved;
    }

    private StoredImage describe(String path, Path target, ImageMetadata metadata) throws IOException {
        String fileName = metadata.fileName() == null ? target.getFileName().toString() : metadata.fileName();
        String contentType = metadata.contentType() == null ? Files.probeContentType(target) : metadata.contentType();
        Instant lastModified = Files.getLastModifiedTime(target).toInstant();
        return new StoredImage(normalizePath(path), fileName, contentType, Files.size(target), lastModified);
    }

    private void moveIntoPlace(Path tempFile, Path target) throws IOException {
        try {
            Files.move(tempFile, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException ex) {
            Files.move(tempFile, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private String normalizePath(String path) {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("Image path must not be blank");
        }

        String normalizedPath = path.replace('\\', '/');
        if (normalizedPath.startsWith("/") || normalizedPath.endsWith("/")) {
            throw new IllegalArgumentException("Image path must be a relative file path");
        }
        return normalizedPath;
    }
}
