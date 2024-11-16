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

```java
// * sending a message as title with all honey features
AdventureMessageDispatcher.createTitle()
    .recipient(event.getPlayer())
    .title(it -> it.template(formatter, "Hello!"))
    .subtitle(it -> it.template(formatter, "It is a pleasure to see you there {{player.getName}}")
    .variable("player", event.getPlayer()))
    .times(2, 4, 2)
    .dispatch();

// * sending a message as chat message with all honey features
AdventureMessageDispatcher.createChat()
    .recipient(Bukkit.getServer())
    .template(formatter, "{{player.getName}} has joined the server!")
    .variable("player", event.getPlayer())
    .dispatch();

// * sending a message as action bar with predefined message without any placeholder support
AdventureMessageDispatcher.createActionBar()
    .recipient(event.getPlayer())
    .template(Component.text("Honey is great, isn't it?"))
    .dispatch();
```

![test-plugin showcase](assets/image.png)
