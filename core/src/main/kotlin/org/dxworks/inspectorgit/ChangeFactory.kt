package org.dxworks.inspectorgit

import org.dxworks.inspectorgit.gitclient.enums.ChangeType
import org.dxworks.inspectorgit.model.Change
import org.dxworks.inspectorgit.model.Commit
import org.dxworks.inspectorgit.model.File
import org.dxworks.inspectorgit.model.LineChange

abstract class ChangeFactory {
    abstract fun create(commit: Commit,
                        type: ChangeType,
                        oldFileName: String,
                        newFileName: String,
                        file: File,
                        parentCommit: Commit?,
                        lineChanges: MutableList<LineChange>,
                        parentChange: Change?): Change
}