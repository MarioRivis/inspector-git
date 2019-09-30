package org.dxworks.inspectorgit.parsers.impl

import org.dxworks.inspectorgit.GitClient
import org.dxworks.inspectorgit.dto.ChangeDTO
import org.dxworks.inspectorgit.parsers.abstracts.CommitParser

class MergeCommitParser(private val gitClient: GitClient) : CommitParser() {

    override fun extractChanges(lines: MutableList<String>, commitId: String, parentIds: List<String>): List<ChangeDTO> {
        val parentAndFile: List<Pair<String, String>> = getAffectedFilesByParent(commitId, parentIds)
        val blameChanges = getBlameChanges(parentAndFile, commitId)
        val mergeChanges = if (lines.isNotEmpty()) getMergeChanges(lines, parentIds) else emptyList()
        return blameChanges + mergeChanges
    }

    private fun getBlameChanges(parentAndFile: List<Pair<String, String>>, commitId: String): List<ChangeDTO> {
        return parentAndFile.map { Pair(it.first, gitClient.diff(it.first, commitId, it.second)) }
                .filter { it.second.isNotEmpty() }
                .map { BlameParser(gitClient, commitId, it.first).parse(it.second.toMutableList()) }
    }

    private fun getMergeChanges(lines: MutableList<String>, parentIds: List<String>): List<ChangeDTO> {
        val changes = getChanges(lines)
        return parentIds.mapIndexed { index, parentCommitId -> changes.map { MergeChangeParser(index, parentIds.size, parentCommitId).parse(it) } }.flatten()
    }

    private fun getAffectedFilesByParent(commitId: String, parentIds: List<String>): List<Pair<String, String>> {
        val affectedFiles = gitClient.affectedFiles(commitId)
        val parentAndFile: MutableList<Pair<String, String>> = ArrayList()
        var index = 0
        for (fileName in affectedFiles) {
            if (fileName.isNotBlank())
                parentAndFile.add(Pair(parentIds[index], fileName))
            else
                index++
        }
        return parentAndFile
    }
}