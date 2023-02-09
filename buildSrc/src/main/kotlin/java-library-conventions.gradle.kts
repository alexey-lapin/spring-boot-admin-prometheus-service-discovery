plugins {
    `java-library`
    `maven-publish`
    id("java-base-conventions")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
//    repositories {
//        maven {
//            name = "myOrgPrivateRepo"
//            url = uri("build/my-repo")
//        }
//    }
}
