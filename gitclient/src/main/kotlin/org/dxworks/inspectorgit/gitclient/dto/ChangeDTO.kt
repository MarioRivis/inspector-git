package org.dxworks.inspectorgit.gitclient.dto

import org.dxworks.inspectorgit.gitclient.enums.ChangeType

class ChangeDTO(val oldFileName: String,
                val newFileName: String,
                var type: ChangeType,
                val hunks: List<HunkDTO>,
                var parentCommitId: String,
                val isBinary: Boolean)
