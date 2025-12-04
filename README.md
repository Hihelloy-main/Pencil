Pencil
===========

high-performance Minecraft server that aims to fix gameplay and mechanics inconsistencies.




How To (Server Admins)
------
Paperclip is a jar file that you can download and run just like a normal jar file.

Downloading Pencil is not available to you right now, compile it yourself. Sorry!

Run the Paperclip jar directly from your server. Just like old times



How To (Plugin Developers)
------
* See our API [here]()
* See upcoming, pending, and recently added API [here]()
* Paper API javadocs here: [papermc.io/javadocs]()
#### Repository (for pencil-api)
##### Maven

```xml
<repository>
    <id>papermc</id>
    <url>https://repo.papermc.io/repository/maven-public/</url>
</repository>
```

```xml
<dependency>
    <groupId>io.papermc.paper</groupId>
    <artifactId>paper-api</artifactId>
    <version>1.21.10-R0.1-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>
```
##### Gradle
```kotlin
repositories {
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.10-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
```

How To (Compiling Jar From Source)
------
To compile Paper, you need JDK 21 and an internet connection.

Clone this repo, run `./gradlew applyPatches`, then `./gradlew createMojmapBundlerJar` from your terminal. You can find the compiled jar in the `pencil-server/build/libs` directory.

To get a full list of tasks, run `./gradlew tasks`.

How To (Pull Request)
------
See [Contributing](CONTRIBUTING.md)

[JetBrains](https://www.jetbrains.com/), creators of the IntelliJ IDEA, supports Paper with one of their [Open Source Licenses](https://www.jetbrains.com/opensource/). IntelliJ IDEA is the recommended IDE for working with Paper, and most of the Paper team uses it.

All our sponsors!  
[![Sponsor Image](https://raw.githubusercontent.com/PaperMC/papermc.io/data/sponsors.png)](https://papermc.io/sponsors)
