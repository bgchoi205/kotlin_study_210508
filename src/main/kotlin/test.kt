import java.io.File

fun main(){
    val article = Article(1,"hi","hihi",1,1,"2021-05-14", "2021-05-14")
    val jsonStr = article.toJson()
    mapFromJsonStr(jsonStr)
}