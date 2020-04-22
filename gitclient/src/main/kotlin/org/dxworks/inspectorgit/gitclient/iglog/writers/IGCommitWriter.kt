package org.dxworks.inspectorgit.gitclient.iglog.writers

import org.dxworks.inspectorgit.gitclient.dto.gitlog.CommitDTO
import org.dxworks.inspectorgit.gitclient.iglog.IGLogConstants

class IGCommitWriter(private val commitDTO: CommitDTO) : IGWriter() {
    override fun appendLines(responseBuilder: StringBuilder) {
        responseBuilder.appendln(getIdLine())
        responseBuilder.appendln(getParentsLine())
        responseBuilder.appendln(commitDTO.authorDate)
        responseBuilder.appendln(commitDTO.authorEmail)
        responseBuilder.appendln(commitDTO.authorName)
        if (commitDTO.authorDate != commitDTO.committerDate) {
            responseBuilder.appendln(commitDTO.committerDate)
            responseBuilder.appendln(commitDTO.committerEmail)
            responseBuilder.appendln(commitDTO.committerName)
        }
        responseBuilder.appendln(getMessageLine())

        commitDTO.changes.forEach { responseBuilder.append(IGChangeWriter(it).write()) }
    }

    private fun getMessageLine() = "${IGLogConstants.messagePrefix}${getFormattedMessage()}"

    private fun getFormattedMessage() = commitDTO.message.replace("\n", "\n${IGLogConstants.messagePrefix}")

    private fun getIdLine() = "${IGLogConstants.commitIdPrefix}${commitDTO.id}"

    private fun getParentsLine() = commitDTO.parentIds.joinToString(" ")

}