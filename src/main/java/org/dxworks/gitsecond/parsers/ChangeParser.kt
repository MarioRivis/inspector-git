    private fun extractFileNames(): Pair<String, String> {
        val oldFileNameLineIndex = lines.indexOfFirst { it.startsWith(oldFileNameLinePrefix) }
        return Pair(first = lines.removeAt(oldFileNameLineIndex).removePrefix(oldFileNameLinePrefix).removePrefix("a"),
                second = lines.removeAt(oldFileNameLineIndex).removePrefix(newFileNameLinePrefix).removePrefix("b"))
    }

            lines.forEach {