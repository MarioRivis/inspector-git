package org.dxworks.gitsecond.model


data class Change(var commit: Commit, var type: ChangeType, var file: File, var oldFilename: String, var newFileName: String, var lineChanges: List<LineChange>, var annotatedLines: List<AnnotatedLine>) {
    var parent: Change? = if (type == ChangeType.ADD || file.changes.isEmpty()) null else getParentChange(commit.parents)
    val isRenameChange: Boolean
        get() = type == ChangeType.RENAME

    private fun getParentChange(commits: List<Commit>): Change {
        return commits.flatMap { it.changes }.find { it.file == file }
                ?: getParentChange(commits.flatMap { it.parents })
    }

    init {
        println("Commit: ${commit.id}      beforeChange for ${file.fullyQualifiedName}")
        println(annotatedLines.joinToString("\n") { "${it.lineNumber} ${it.content}" })
        if (!commit.isMergeCommit) {
            apply()
        }
        println("Commit: ${commit.id}      after for ${file.fullyQualifiedName}")
        println(annotatedLines.joinToString("\n") { "${it.lineNumber} ${it.content}" })
        print("\n\n\n")
    }

    private fun apply() {
        val newAnnotatedLines = if (parent != null) ArrayList(parent!!.annotatedLines) else ArrayList()
        lineChanges.filter { it.operation == LineOperation.REMOVE }
                .forEach { removeChange -> newAnnotatedLines.removeIf { it.lineNumber == removeChange.lineNumber && it.content == removeChange.content } }

        lineChanges.filter { it.operation == LineOperation.ADD }.forEach {
            val annotatedLine = AnnotatedLine(commit, it.lineNumber, it.content)
            newAnnotatedLines.add(it.lineNumber - 1, annotatedLine)
        }

        reindex(newAnnotatedLines)

        annotatedLines = newAnnotatedLines
    }


    private fun reindex(annotatedLines: MutableList<AnnotatedLine>) {
        annotatedLines.forEachIndexed { index, annotatedLine -> annotatedLine.lineNumber = index + 1 }
    }

}