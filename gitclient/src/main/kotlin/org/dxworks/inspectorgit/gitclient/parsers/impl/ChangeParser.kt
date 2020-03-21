import org.dxworks.inspectorgit.gitclient.dto.gitlog.ChangeDTO
        val (oldFileName, newFileName) = extractFileNames(lines, type)
    private fun extractFileNames(lines: List<String>, type: ChangeType): Pair<String, String> {
        val oldFilePrefix = if(type == ChangeType.RENAME) "rename from " else "--- a/"
        val newFilePrefix = if(type == ChangeType.RENAME) "rename to " else "+++ b/"

        val oldFileName = if (type == ChangeType.ADD) devNull else {
            extractFileName(lines, oldFilePrefix)
        }
        val newFileName = if (type == ChangeType.DELETE) devNull else {
            extractFileName(lines, newFilePrefix)
        }
        return Pair(oldFileName, newFileName)
    }

    private fun extractFileName(lines: List<String>, fileNamePrefix: String): String {
        return try {
            lines.find { it.startsWith(fileNamePrefix) }!!.removePrefix(fileNamePrefix)
        } catch (e: NullPointerException) {
            extractFileName(lines[0])
        }
    }

    private fun extractFileName(diffLine: String): String {
        val namesStartIndex = diffLine.indexOf(" a/") + 3
        val names = diffLine.substring(namesStartIndex)
        val namesParts = names.split(" b/")
        return namesParts.take(namesParts.size / 2).joinToString(" b/")