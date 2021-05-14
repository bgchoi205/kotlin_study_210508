import java.io.File
import java.text.SimpleDateFormat

fun readLineTrim() = readLine()!!.trim()

fun mapFromJsonStr(jsonStr : String): Map<String, Any>{
    val map = mutableMapOf<String, Any>()
    val jsonStr = jsonStr.drop(1).dropLast(1)

    val jsonItems = jsonStr.split(",\r\n")
    for(jsonItem in jsonItems){
        val jsonItemsBits = jsonItem.split(":", limit = 2)

        val key = jsonItemsBits[0].trim().drop(1).dropLast(1)
        val value = jsonItemsBits[1]

        if(value == "true"){
            map[key] = true
        }
        else if(value == "false"){
            map[key] = false
        }
        else if(value.startsWith("\"")){
            map[key] = value.trim().drop(1).dropLast(1)
        }
        else if(value.contains(".")){
            map[key] = value.toDouble()
        }
        else{
            map[key] = value.toInt()
        }
    }

    return map.toMap()
}

fun readStrFromFile(filePath : String) : String{
    if(!File(filePath).isFile){
        return ""
    }
    val jsonStr = File(filePath).readText(Charsets.UTF_8)
    return jsonStr
}

fun readIntFromFile(filePath : String, default: Int): Int{
    val fileContent = readStrFromFile(filePath)
    if(fileContent == ""){
        return default
    }
    return fileContent.toInt()
}

fun writeStrFile(filePath : String, fileContent: String){
    File(filePath).parentFile.mkdirs()
    File(filePath).writeText(fileContent)
}

fun writeIntFile(filePath : String, fileContent : Int){
    writeStrFile(filePath, fileContent.toString())
}


object Util{
    fun getDateNowStr() : String{
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        return format.format(System.currentTimeMillis())
    }
}