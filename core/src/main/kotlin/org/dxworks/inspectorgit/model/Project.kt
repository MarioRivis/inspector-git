package org.dxworks.inspectorgit.model

import org.dxworks.inspectorgit.registries.*
import org.dxworks.inspectorgit.registries.task.TaskRegistry
import org.dxworks.inspectorgit.registries.task.TaskStatusCategoryRegistry
import org.dxworks.inspectorgit.registries.task.TaskStatusRegistry
import org.dxworks.inspectorgit.registries.task.TaskTypeRegistry

class Project(val name: String) {
    val accountRegistry = AccountRegistry()
    val developerRegistry = DeveloperRegistry()

    val commitRegistry = CommitRegistry()
    val fileRegistry = FileRegistry()


    val taskRegistry = TaskRegistry()
    val taskTypeRegistry = TaskTypeRegistry()
    val taskStatusRegistry = TaskStatusRegistry()
    val taskStatusCategoryRegistry = TaskStatusCategoryRegistry()

    val pullRequestRegistry = PullRequestRegistry()
}
