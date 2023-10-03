import testing.TestRunner

plugins {
    application
}

application {
    mainClass.set("main.FTree")
}

repositories {
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
            setSrcDirs(listOf("src/test"))
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

buildscript {
    repositories {
        mavenLocal()
    }

    dependencies {
        classpath("com.github.ingmarrr:jutil:1.0.0")
    }
}

tasks.register<JavaExec>("runTests") {
    group = "verification"
    description = "Run tests"
    mainClass.set("testing.TestRunner")
    args = listOf("$buildDir/classes/java/test/")
    classpath = sourceSets["test"].runtimeClasspath + sourceSets["main"].runtimeClasspath
    standardOutput = System.out
    standardInput = System.`in`
}

tasks.test {
    dependsOn(tasks["runTests"])
    exclude("**/*")
}




