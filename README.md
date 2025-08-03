# ColorUtil 🌈

Advanced Minecraft text formatting utility with support for legacy colors, hex colors, and gradients.

## Features

- **Full color support**: Legacy (`&a`), Hex (`&#FFFFFF`), and Gradients (`<#FF0000>text</#00FF00>`)
- **Optimized performance**: Pre-compiled patterns and efficient color interpolation
- **Simple API**: Just one main method to handle all your coloring needs
- **Batch processing**: Colorize individual strings or entire lists at once

## Installation

### Maven

```xml
<repository>
  <id>deban</id>
  <url>https://deban.ru/releases</url>
</repository>

<dependency>
  <groupId>su.daycube</groupId>
  <artifactId>ColorUtil</artifactId>
  <version>1.2.0</version>
</dependency>
```

### Gradle (Groovy)

```xml
repositories {
    maven {
        name "deban"
        url "https://deban.ru/releases"
    }
}

dependencies {
    implementation "su.daycube:ColorUtil:1.2.0"
}
```

## Usage

### Basic Coloring

```java
String colored = ColorUtil.colorize("&aHello &6world!");
// Returns "§aHello §6world!"
```

### Hex Colors (1.16+)

```java
String hexColored = ColorUtil.colorize("&#FF0000Red &#00FF00Green &#0000FFBlue");
```

### Text Gradients

```java
String gradient = ColorUtil.colorize("Title: <#FF0000>GRADIENT</#00FF00>");
// Creates smooth color transition from red to green
```

### List Processing

```java
List<String> messages = Arrays.asList(
"&aFirst line",
"<#FF0000>Gradient</#00FF00> text",
"&#FFFFFFWhite with hex"
);

List<String> colored = ColorUtil.colorize(messages);
```

## Advanced Formatting

### Combine all features together:

```java
String complex = ColorUtil.colorize(
"&l<#FF0000>Fancy &b&oformatted</#00FF00> &#AAAAAAtext"
);
```

## License

This project is licensed under Apache License 2.0 - see the [LICENSE](https://github.com/seetch/ColorUtil/blob/master/LICENSE) file for details.