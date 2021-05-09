import java.text.SimpleDateFormat

fun readLineTrim() = readLine()!!.trim()

object Util{
    fun getDateNowStr() : String{
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        return format.format(System.currentTimeMillis())
    }
}