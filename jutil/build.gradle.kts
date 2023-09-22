
plugins {
    `java-library`
    id("maven-publish")
}

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = "com.github.ingmarrr"
            artifactId = "jutil"
            version = "1.0.0"
        }
    }

    repositories {
        mavenLocal()
    }
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("src"))
        }
        resources {
            setSrcDirs(listOf("conf"))
        }
    }
    test {
        java {
            setSrcDirs(listOf("test"))
        }
    }
}

