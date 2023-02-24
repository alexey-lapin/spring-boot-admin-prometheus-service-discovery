plugins {
    id("net.researchgate.release") version "3.0.2"
    id("com.dorongold.task-tree") version "2.1.1"
}

release {
    tagTemplate.set("a${version}")
}

val tt by tasks.registering {
    doLast {
        println("tt")
    }
}

tasks.getByName("afterReleaseBuild") {
    dependsOn(tt)
}
