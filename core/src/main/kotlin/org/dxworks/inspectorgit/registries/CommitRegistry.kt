package org.dxworks.inspectorgit.registries

import org.dxworks.inspectorgit.model.Commit

class CommitRegistry : AbstractRegistry<Commit, String>() {

    override fun getByID(id: String): Commit? {
        return if (id.startsWith("^")) findByPrefix(id.removePrefix("^")) else super.getByID(id)
    }

    private fun findByPrefix(prefix: String) = all.find { it.id.startsWith(prefix) }

    override fun getID(entity: Commit): String {
        return entity.id
    }
}