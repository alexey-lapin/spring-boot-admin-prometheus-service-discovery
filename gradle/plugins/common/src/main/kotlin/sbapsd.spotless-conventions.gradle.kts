plugins {
    id("com.diffplug.spotless")
}

spotless {

    format("documentation") {
        target("**/*.md")
        trimTrailingWhitespace()
        endWithNewline()
    }

    pluginManager.withPlugin("java") {
        java {
            targetExclude("build/generated/aotSources/**/*")
            licenseHeaderFile(rootProject.file("src/mit-license.java"), "(package|import|open|module) ")
            removeUnusedImports()
            trimTrailingWhitespace()
            endWithNewline()
        }
    }

}