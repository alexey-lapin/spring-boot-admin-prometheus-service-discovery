import org.gradle.api.Project

fun Project.getSBAVersion(): Version {
    val ver = findProperty("sba.version") as String? ?: "0.0.0"
    return Version.parse(ver)
}

fun Project.isSBAVersion1(): Boolean {
    return getSBAVersion().major == 1
}

fun Project.isSBAVersion2(): Boolean {
    return getSBAVersion().major == 2
}

fun Project.isSBAVersion3(): Boolean {
    return getSBAVersion().major == 3
}

data class Version(val major: Int, val minor: Int, val patch: Int) {
    override fun toString(): String {
        return "${major}.${minor}.${patch}"
    }

    companion object {
        fun parse(ver: String): Version {
            val split = ver.split(".")
            return Version(split.get(0).toInt(), split.get(1).toInt(), split.get(2).toInt())
        }
    }
}