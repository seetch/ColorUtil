## Maven

```xml
<repository>
  <id>daycube-repo</id>
  <url>https://repo.daycube.su/releases</url>
</repository>

<dependency>
  <groupId>su.daycube</groupId>
  <artifactId>ColorUtil</artifactId>
  <version>1.1.0</version>
</dependency>
```

## Gradle

```xml
maven {
    url = uri("https://repo.daycube.su/releases")
}

implementation("su.daycube:ColorUtil:1.1.0")
```
