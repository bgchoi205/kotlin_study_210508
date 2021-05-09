// Article
data class Article(
    val id : Int,
    var title : String,
    var body : String,
    val memberId : Int,
    val boardId : Int,
    val regDate : String,
    var updateDate : String
)