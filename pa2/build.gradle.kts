
plugins {
    application
}

repositories {
    mavenCentral()
    mavenLocal()
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
            setSrcDirs(listOf("tests"))
        }
    }
    create("benchmarks") {
        java {
            setSrcDirs(listOf("benchmarks"))
        }
    }
}

dependencies {
    implementation("com.github.ingmarrr:jutil:1.0.0")
}


tasks.register<JavaExec>("bench") {
    group = "performance"
    description = "Run benchmarks"
    mainClass = "BenchRunner"
    classpath = sourceSets["benchmarks"].runtimeClasspath
}

tasks.test {
    group = "correctness"
    description = "Run tests"
    classpath = sourceSets["tests"].runtimeClasspath
}





