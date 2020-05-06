package org.dxworks.inspectorgit.core.model

import java.time.ZonedDateTime

data class Commit(var project: Project,
                  var id: String,
                  var message: String,
                  val authorDate: ZonedDateTime,
                  val committerDate: ZonedDateTime,
                  val author: Author,
                  val committer: Author,
                  var parents: List<Commit>,
                  var children: List<Commit>,
                  var changes: List<Change>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Commit

        if (id != other.id) return false
        if (message != other.message) return false
        if (authorDate != other.authorDate) return false
        if (committerDate != other.committerDate) return false
        if (author != other.author) return false
        if (committer != other.committer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + message.hashCode()
        result = 31 * result + authorDate.hashCode()
        result = 31 * result + committerDate.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + committer.hashCode()
        return result
    }

    val isMergeCommit: Boolean
        get() = parents.size > 1

    val isSplitCommit: Boolean
        get() = children.size > 1

    fun addChild(commit: Commit) {
        children = children + listOf(commit)
    }
}