package org.dxworks.inspectorgit.gitclient.iglog.readers

import org.dxworks.inspectorgit.gitclient.dto.gitlog.CommitDTO
import org.dxworks.inspectorgit.gitclient.dto.gitlog.GitLogDTO
import org.dxworks.inspectorgit.gitclient.iglog.IGLogConstants
import org.slf4j.LoggerFactory
import java.io.InputStream

class IGLogReader(private val commitReader: IGCommitReader = IGCommitReader()) {
    companion object {
        val LOG = LoggerFactory.getLogger(IGLogReader::class.java)
    }

    fun read(stream: InputStream): GitLogDTO {
        val reader = stream.bufferedReader()
        val iglogVersion = reader.readLine()
        var currentCommitLines: MutableList<String> = ArrayList()
        val commits: MutableList<CommitDTO> = ArrayList()
        var nrOfCommits = 0L
        reader.forEachLine {
            if (it.startsWith(IGLogConstants.commitIdPrefix)) {
                if (currentCommitLines.isNotEmpty()) {
                    LOG.info("Reading commit number ${nrOfCommits++} having ${currentCommitLines.size} lines")
                    commits.add(commitReader.read(currentCommitLines))
                }
                currentCommitLines = ArrayList()
            }
            currentCommitLines.add(it)
        }
        if (currentCommitLines.isNotEmpty()) {
            LOG.info("Reading commit number ${nrOfCommits++} having ${currentCommitLines.size} lines")
            commits.add(commitReader.read(currentCommitLines))
        }
        return GitLogDTO(commits)
    }
}