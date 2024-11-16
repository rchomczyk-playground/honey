## honey (*miód*)

A message library, which focuses on simplicity and flexibility.

### Intent

*honey* was created to allow for developers to create easily customizable messages for end users, by giving them flexibility with placeholders.

### Get started

#### Gradle (kts)

##### Add repository

```kotlin
maven("https://repo.shiza.dev/releases")
```

##### Add dependency

```kotlin
implementation("dev.shiza:honey:2.0.0")
```

#### Maven

##### Add repository
```xml
<repository>
  <id>shiza-releases</id>
  <url>https://repo.shiza.dev/releases</url>
</repository>
```

##### Add dependency
```xml
<dependency>
  <groupId>dev.shiza</groupId>
  <artifactId>honey</artifactId>
  <version>2.0.0</version>
</dependency>
```

### Use case

A showcase of how to use *honey*, can be found in [honey-test-plugin](honey-test-plugin) module.

![test-plugin showcase](assets/image.png)