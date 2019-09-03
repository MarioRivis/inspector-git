import org.dxworks.gitsecond.dto.AnnotatedLineDTO
import org.dxworks.gitsecond.dto.ChangeDTO
import org.dxworks.gitsecond.dto.HunkDTO
abstract class ChangeParser(protected val lines: MutableList<String>) {
    private val oldFileNameLinePrefix = "--- a"
    private val newFileNameLinePrefix = "+++ b"
    lateinit var change: ChangeDTO
    fun parse(): ChangeParser {
        change = ChangeDTO(
                changeType = extractChangeType(),

                oldFileName = oldFileName,
                newFileName = newFileName,
                hunks = extractHunks(),
                annotatedAnnotatedLines = extractAnnotatedLines())
        return this
    }
    abstract fun extractHunks(): List<HunkDTO>

    abstract fun extractAnnotatedLines(): List<AnnotatedLineDTO>

    protected fun getHunks(): List<MutableList<String>> {
        val hunks: MutableList<MutableList<String>> = ArrayList()
        var currentHunkLines: MutableList<String> = ArrayList()
        lines.forEach {
            if (it.startsWith("@")) {
                currentHunkLines = ArrayList()
                hunks.add(currentHunkLines)
            }
            currentHunkLines.add(it)
        }
        return hunks
        return when {
            changeTypeLine.startsWith("new file mode") -> {
                lines.removeAt(0)
                ChangeType.ADD
            }
            changeTypeLine.startsWith("deleted file mode") -> {
                lines.removeAt(0)
                ChangeType.DELETE
            }
            changeTypeLine.startsWith("similarity index") -> {
                lines.removeAt(0)
                lines.removeAt(0)
                lines.removeAt(0)
                ChangeType.RENAME
            }
            else -> ChangeType.MODIFY
        }
        return Pair(first = lines.removeAt(oldFileNameLineIndex).removePrefix(oldFileNameLinePrefix),
                second = lines.removeAt(oldFileNameLineIndex).removePrefix(newFileNameLinePrefix))
}