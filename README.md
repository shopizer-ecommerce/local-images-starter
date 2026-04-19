# Shopizer Local Images Starter

Spring Boot starter modules for storing Shopizer images on a local filesystem.

## Modules

- `local-images-core`: storage API and filesystem implementation.
- `local-images-spring-boot-autoconfigure`: Spring Boot 4 auto-configuration.
- `local-images-spring-boot-starter`: dependency-only starter for applications.

## Usage

Add the starter to a Spring Boot application:

```xml
<dependency>
  <groupId>com.shopizer.images</groupId>
  <artifactId>local-images-spring-boot-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```

Configure the storage root:

```properties
shopizer.images.local.storage-path=/var/shopizer/images
shopizer.images.local.create-directories=true
```

Inject `LocalImageStore` or the `ImageStore` interface:

```java
@Service
class ProductImageService {

    private final ImageStore imageStore;

    ProductImageService(ImageStore imageStore) {
        this.imageStore = imageStore;
    }
}
```
# local-images-starter
