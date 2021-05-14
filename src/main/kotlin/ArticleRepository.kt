class ArticleRepository{

    fun getArticles() : List<Article>{
        val articles = mutableListOf<Article>()
        val lastId = getLastArticleId()
        for(i in 1..lastId){
            val article = articleFromFile("data/article/$i.json")
            if(article != null){
                articles.add(article)
            }
        }
        return articles
    }

    fun articleFromFile(jsonFilePath : String): Article? {
        val jsonStr = readStrFromFile(jsonFilePath)
        if(jsonStr == ""){
            return null
        }
        val map = mapFromJsonStr(jsonStr)

        val id = map["id"].toString().toInt()
        val title = map["title"].toString()
        val body = map["body"].toString()
        val memberId = map["memberId"].toString().toInt()
        val boardId = map["boardId"].toString().toInt()
        val regDate = map["regDate"].toString()
        val updateDate = map["updateDate"].toString()

        return Article(id, title, body, memberId, boardId, regDate, updateDate)
    }

    fun getLastArticleId(): Int{
        val lastId = readIntFromFile("data/article/lastArticleId.txt",0)
        return lastId
    }

    fun addArticle(title: String, body: String, memberId : Int, boardId : Int): Int {
        val id = getLastArticleId() + 1
        val regDate = Util.getDateNowStr()
        val updateDate = Util.getDateNowStr()
        val article = Article(id, title, body, memberId, boardId, regDate, updateDate)
        writeStrFile("data/article/$id.json", article.toJson())
        writeIntFile("data/article/lastArticleId.txt", id)
        return id
    }

//    fun makeTestArticles(){
//        for(i in 1..30){
//            addArticle("제목$i", "내용$i", i % 9 + 1, i % 2 + 1)
//        }
//    }

    fun getArticleById(id: Int): Article? {
        for(article in getArticles()){
            if(article.id == id){
                return article
            }
        }
        return null
    }

    fun articlesFilter(keyword: String, boardId : Int, page: Int, pageCount: Int): List<Article> {
        val filtered1Articles = articlesFilterByKeyword(keyword)
        val filtered2Articles = articlesFilterByBoardId(filtered1Articles, boardId)
        val filtered3Articles = articlesFilterByPage(filtered2Articles, page, pageCount)

        return filtered3Articles
    }

    private fun articlesFilterByPage(filtered2Articles: List<Article>, page: Int, pageCount: Int): List<Article> {
        val startIndex = filtered2Articles.lastIndex - ((page - 1) * pageCount)
        var endIndex = startIndex - pageCount + 1
        if(endIndex < 0){
            endIndex = 0
        }
        val filtered3Articles = mutableListOf<Article>()
        for(i in startIndex downTo endIndex){
            filtered3Articles.add(filtered2Articles[i])
        }
        return filtered3Articles
    }

    private fun articlesFilterByBoardId(filtered1Articles: List<Article>, boardId: Int): List<Article> {
        if(boardId == 0){
            return filtered1Articles
        }
        val filtered2Articles = mutableListOf<Article>()
        for(article in filtered1Articles){
            if(article.boardId == boardId){
                filtered2Articles.add(article)
            }
        }
        return filtered2Articles
    }

    private fun articlesFilterByKeyword(keyword: String): List<Article> {
        val articles = getArticles()
        if(keyword.isEmpty()){
            return articles
        }
        val filtered1articles = mutableListOf<Article>()
        for(article in articles){
            if(article.title.contains(keyword)){
                filtered1articles.add(article)
            }
        }
        return filtered1articles
    }


}