import org.dxworks.inspectorgit.utils.devNull
        val (oldFileName, newFileName) = extractFileNames(lines)
        LOG.info("Parsing $type change for $oldFileName -> $newFileName")
                oldFileName = oldFileName,
                newFileName = newFileName,
            lines.find { it.startsWith("similarity index") } != null -> ChangeType.RENAME
    private fun extractFileNames(lines: List<String>): Pair<String, String> {
        val oldFilePrefix = "--- a/"
        val newFilePrefix = "+++ b/"
        return Pair(lines.find { it.startsWith(oldFilePrefix) }!!.removePrefix(oldFilePrefix),
                lines.find { it.startsWith(newFilePrefix) }!!.removePrefix(newFilePrefix))