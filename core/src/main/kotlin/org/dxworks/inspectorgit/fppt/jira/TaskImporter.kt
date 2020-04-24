package org.dxworks.inspectorgit.fppt.jira

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.dxworks.inspectorgit.fppt.jira.dtos.TaskImportDTO
import org.dxworks.inspectorgit.model.Project
import org.dxworks.inspectorgit.transformers.TasksTransformer
import java.nio.file.Path

class TaskImporter(private val path: Path) {
    fun importToProject(project: Project, taskPrefixes: List<String>) {
        val importDTO = jacksonObjectMapper().readValue<TaskImportDTO>(path.toFile())
        TasksTransformer(project, importDTO.issueTypes, importDTO.users, importDTO.issues, taskPrefixes)
    }
}