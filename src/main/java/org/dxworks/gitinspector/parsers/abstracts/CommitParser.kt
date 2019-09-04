package org.dxworks.gitinspector.parsers.abstracts

import org.dxworks.dto.ChangeDTO
import org.dxworks.dto.CommitDTO
import org.dxworks.gitinspector.parsers.GitParser
import java.util.*

abstract class CommitParser : GitParser<CommitDTO> {
    override fun parse(lines: MutableList<String>): CommitDTO {
        val commitId = extractCommitId(lines)
        return CommitDTO(
                id = commitId,
                parentIds = extractParentIds(lines),
                authorName = extractAuthorName(lines),
                authorEmail = extractAuthorEmail(lines),
                date = extractDate(lines),
                message = extractMessage(lines),
                changes = extractChanges(lines, commitId))
    }

    abstract fun extractChanges(lines: MutableList<String>, commitId: String): List<ChangeDTO>

    protected fun getChanges(lines: MutableList<String>): List<MutableList<String>> {
        val changes: MutableList<MutableList<String>> = ArrayList()
        var currentChangeLines: MutableList<String> = ArrayList()
        lines.forEach {
            if (it.startsWith("diff ")) {
                currentChangeLines = ArrayList()
                changes.add(currentChangeLines)
            }
            currentChangeLines.add(it)
        }
        return changes
    }

    private fun extractCommitId(lines: MutableList<String>): String {
        return lines.removeAt(0).removePrefix("commit: ")
    }

    private fun extractParentIds(lines: MutableList<String>): List<String> {
        return lines.removeAt(0).removePrefix("parents: ").split(" ")
    }

    private fun extractAuthorName(lines: MutableList<String>): String {
        return lines.removeAt(0).removePrefix("author name: ")
    }

    private fun extractAuthorEmail(lines: MutableList<String>): String {
        return lines.removeAt(0).removePrefix("author email: ")
    }

    private fun extractDate(lines: MutableList<String>): Date {
        return Date(lines.removeAt(0).removePrefix("date: ").toLong())
    }

    private fun extractMessage(lines: MutableList<String>): String {
        lines.removeAt(0)
        var message: String = ""
        while (lines.isNotEmpty() && !lines[0].startsWith("diff ")) {
            message = "$message\n${lines.removeAt(0)}"
        }
        return message.trim()
    }
}