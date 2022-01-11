package dev.yiray.livedatamvi.model

data class Task(val name: String?, val status: Status) {
    constructor(name: String?): this(name, Status.OPEN)
}