rootProject.name = "proof-of-concept"

include("access:access.consumer")
include("access:access.orchestration")

// too much repetition
rootDir.resolve("commons").listFiles()?.forEach {
    include("commons:${it.name}")
}
