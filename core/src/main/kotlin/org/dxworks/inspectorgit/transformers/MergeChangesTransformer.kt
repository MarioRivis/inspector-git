package org.dxworks.inspectorgit.transformers

import org.dxworks.inspectorgit.gitClient.dto.ChangeDTO
import org.dxworks.inspectorgit.gitClient.enums.LineOperation
import org.dxworks.inspectorgit.model.Change
import org.dxworks.inspectorgit.model.Commit
import org.dxworks.inspectorgit.model.Project

class MergeChangesTransformer(private val changeDTOs: List<ChangeDTO>, val commit: Commit, val project: Project) {

    fun transform(): Change {
        val changes = changeDTOs.map { ChangeTransformer(it, commit, project).transform() }
        return mergeChanges(changes)
    }

    private fun mergeChanges(changes: List<Change>): Change {
        val annotatedFiles = changes.map { it.annotatedLines }
        val size = annotatedFiles.first().size
        for (i in 0 until size) {
            val currentAnnotatedLines = annotatedFiles.map { it[i] }
            val firstAnnotatedLine = currentAnnotatedLines[0]
            val annotatedLines = currentAnnotatedLines.subList(1, currentAnnotatedLines.size)
            if (firstAnnotatedLine.commit == commit)
                annotatedLines.find { it.commit != commit }?.let { firstAnnotatedLine.commit = it.commit }
        }
        val firstChange = changes.first()
        // This is done temporarily until we figure out how to manage line changes in a merge commit
        firstChange.lineChanges = firstChange.lineChanges + changes.subList(1, changes.size).flatMap { it.lineChanges }
        firstChange.lineChanges.filter { it.operation == LineOperation.ADD }.forEach { it.commit = it.annotatedLine.commit }
        firstChange.parentCommits = changes.flatMap { it.parentCommits }
        // till here
        return firstChange
    }
}
