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
## With Formatter placeholder:

/* for titles::audience */
dispatcher.createTitle()
    .recipient(event.getPlayer())
    .title(it -> it.template("Hello!"))
    .subtitle(it -> it.template("It is a pleasure to see you there {{player.getName}}")
    .variable("player", event.getPlayer()))
    .times(2, 4, 2)
    .dispatch();

/* for chat::audience */
dispatcher.createChat()
    .recipient(Bukkit.getServer())
    .template("{{player.getName}} has joined the server!")
    .variable("player", event.getPlayer())
    .dispatch();

/* for actionbar::audience */
dispatcher.createActionBar()
    .recipient(event.getPlayer())
    .template("Honey is great, isn't it?")
    .dispatch();
```

## Without formatter, plain template:
```java
    AdventureMessageDispatcher.createChat()
        .recipient(Bukkit.getServer())
        .template(Component.text("Somebody joined to the server!").color(NamedTextColor.RED))
        .dispatch();

    AdventureMessageDispatcher.createActionBar()
        .recipient(event.getPlayer())
        .template(formatter, "Honey is great, isn't it?")
        .dispatch();
  }
```

![test-plugin showcase](assets/image.png)
