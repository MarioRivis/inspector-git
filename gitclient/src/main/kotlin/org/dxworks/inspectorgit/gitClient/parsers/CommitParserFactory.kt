package org.dxworks.inspectorgit.gitClient.parsers

import org.dxworks.inspectorgit.gitClient.GitClient
import org.dxworks.inspectorgit.gitClient.parsers.abstracts.CommitParser
import org.dxworks.inspectorgit.gitClient.parsers.impl.MergeCommitParser
import org.dxworks.inspectorgit.gitClient.parsers.impl.SimpleCommitParser

class CommitParserFactory {
    companion object {
        private const val parents = "parents: "

        fun create(lines: List<String>, gitClient: GitClient): CommitParser {
            return if (getParents(lines).size > 1) MergeCommitParser(gitClient) else SimpleCommitParser()
        }

        private fun getParents(lines: List<String>) =
                lines.find { it.startsWith(parents) }!!.removePrefix(parents).split(" ")
    }
}