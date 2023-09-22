
plugins {
    `java-library`
}

repositories {
    mavenCentral()
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


// java {
//     toolchain {
//         languageVersion.set(JavaLanguageVersion.of(21))
//     }
// }
