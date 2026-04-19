package com.shopizer.images.autoconfigure;

import java.io.IOException;
import java.nio.file.Files;

import com.shopizer.images.ImageStore;
import com.shopizer.images.LocalImageStore;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass(LocalImageStore.class)
@ConditionalOnProperty(prefix = "shopizer.images.local", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(LocalImagesProperties.class)
public class LocalImagesAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ImageStore.class)
    LocalImageStore localImageStore(LocalImagesProperties properties) throws IOException {
        if (properties.isCreateDirectories()) {
            Files.createDirectories(properties.getStoragePath());
        }
        return new LocalImageStore(properties.getStoragePath());
    }
}
