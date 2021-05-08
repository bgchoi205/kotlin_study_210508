import java.text.SimpleDateFormat

val articleRepository = ArticleRepository()
val memberRepository = MemberRepository()
val boardRepository = BoardRepository()

var loginedMember : Member? = null

fun main(){

    val articleController = ArticleController()
    val memberController = MemberController()
    val boardController = BoardController()

    articleRepository.makeTestArticles()
    memberRepository.makeTestMembers()
    boardRepository.makeTestBoards()

    println("==프로그램 시작==")

    while(true){

        loginedMember = memberRepository.getMemberById(1)

        val prompt = if(loginedMember == null){
            "명령어 입력 : "
        }else{
            "${loginedMember!!.nickName} )"
        }
        print(prompt)
        val cmd = readLineTrim()
        val rq = Rq(cmd)

        when(rq.actionPath){
            "/exit" -> {
                break
            }
            "/member/join" -> {
                memberController.join()
            }
            "/member/login" -> {
                memberController.login()
            }
            "/member/logout" -> {
                memberController.logout()
            }
            "/article/write" -> {
                articleController.add()
            }
            "/article/list" -> {
                articleController.list(rq)
            }
            "/article/detail" -> {
                articleController.detail(rq)
            }
            "/article/delete" -> {
                articleController.delete(rq)
            }
            "/article/modify" -> {
                articleController.modify(rq)
            }
            "/board/add" -> {
                boardController.add()
            }
            "/board/list" -> {
                boardController.list()
            }
            "/board/delete" -> {
                boardController.delete(rq)
            }
            "/board/modify" -> {
                boardController.modify(rq)
            }
        }


    }

    println("==프로그램 끝==")

}


// Board
data class Board(
    val id : Int,
    var name : String,
    var code : String,
    val regDate : String,
    var updateDate : String
)

class BoardRepository{
    val boards = mutableListOf<Board>()
    var lastBoardId = 0

    fun addBoard(name : String, code : String){
        val id = ++lastBoardId
        val regDate = Util.getDateNowStr()
        val updateDate = Util.getDateNowStr()
        boards.add(Board(id, name, code, regDate, updateDate))
    }

    fun makeTestBoards(){
        addBoard("공지", "notice")
        addBoard("자유", "free")
    }

    fun getBoardById(id: Int): Board? {
        for(board in boards){
            if(board.id == id){
                return board
            }
        }
        return null
    }
}

class BoardController{
    fun add() {
        print("게시판 이름 입력 : ")
        val name = readLineTrim()
        print("게시판 코드 입력 : ")
        val code = readLineTrim()
        boardRepository.addBoard(name, code)
        println("${name} 게시판이 추가되었습니다.")
    }

    fun list() {
        for(board in boardRepository.boards){
            println("번호 : ${board.id} / 게시판이름 : ${board.name} / 코드 : ${board.code}")
        }
    }

    fun delete(rq: Rq) {
        val id = rq.getIntParam("id",0)
        if(id == 0){
            println("게시물 번호를 입력해주세요")
            return
        }
        val board = boardRepository.getBoardById(id)
        if(board == null){
            println("없는 게시판 번호입니다.")
            return
        }
        boardRepository.boards.remove(board)
        println("$id 번 게시판 삭제완료")
    }

    fun modify(rq : Rq){
        val id = rq.getIntParam("id",0)
        if(id == 0){
            println("게시물 번호를 입력해주세요")
            return
        }
        val board = boardRepository.getBoardById(id)
        if(board == null){
            println("없는 게시판 번호입니다.")
            return
        }
        print("새 이름 입력 : ")
        val name = readLineTrim()
        print("새 코드 입력 : ")
        val code = readLineTrim()
        val updateDate = Util.getDateNowStr()
        board.name = name
        board.code = code
        board.updateDate = updateDate
        println("$id 번 게시판 수정완료")
    }

}



// Member
data class Member(
    val id : Int,
    val loginId : String,
    val loginPw : String,
    val name : String,
    val nickName : String
)

class MemberRepository{
    val members = mutableListOf<Member>()
    var lastMemberId = 0

    fun getMemberByLoginId(loginId: String): Member? {
        for(member in members){
            if(member.loginId == loginId){
                return member
            }
        }
        return null
    }

    fun addMember(loginId: String, loginPw: String, name: String, nickName: String): Int {
        val id = ++lastMemberId
        members.add(Member(id, loginId, loginPw, name, nickName))
        return id
    }

    fun makeTestMembers(){
        for(i in 1..10){
            addMember("user$i", "user$i", "철수$i", "짱구$i")
        }
    }

    fun getMemberById(memberId: Int): Member? {
        for(member in members){
            if(member.id == memberId){
                return member
            }
        }
        return null
    }

}


class MemberController{

    fun join() {
        print("사용할 아이디 입력 : ")
        val loginId = readLineTrim()
        val member = memberRepository.getMemberByLoginId(loginId)
        if(member != null){
            println("이미 사용중인 아이디입니다.")
            return
        }
        print("사용할 비밀번호 입력 : ")
        val loginPw = readLineTrim()
        print("이름 입력 : ")
        val name = readLineTrim()
        print("닉네임 입력 : ")
        val nickName = readLineTrim()
        val id = memberRepository.addMember(loginId, loginPw, name, nickName)
        println("$id 번 회원으로 가입 완료")
    }

    fun login() {
        print("아이디 입력 :")
        val loginId = readLineTrim()
        val member = memberRepository.getMemberByLoginId(loginId)
        if(member == null){
            println("없는 아이디 입니다.")
            return
        }
        print("비밀번호 입력 : ")
        val loginPw = readLineTrim()
        if(member.loginPw != loginPw){
            println("비밀번호가 틀립니다.")
            return
        }
        loginedMember = member
        println("${member.nickName}님 환영합니다.")
    }

    fun logout(){
        loginedMember = null
        println("로그아웃")
    }

}



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

class ArticleRepository{
    val articles = mutableListOf<Article>()
    var lastArticleId = 0

    fun addArticle(title: String, body: String, memberId : Int, boardId : Int): Int {
        val id = ++lastArticleId
        val regDate = Util.getDateNowStr()
        val updateDate = Util.getDateNowStr()
        articles.add(Article(id, title, body, memberId, boardId, regDate, updateDate))
        return id
    }

    fun makeTestArticles(){
        for(i in 1..30){
            addArticle("제목$i", "내용$i", i % 9 + 1, i % 2 + 1)
        }
    }

    fun getArticleById(id: Int): Article? {
        for(article in articles){
            if(article.id == id){
                return article
            }
        }
        return null
    }

    fun articlesFilter(keyword: String, page: Int, pageCount: Int): List<Article> {
        val filtered1Articles = articlesFilterByKeyword(keyword)
        val filtered3Articles = articlesFilterByPage(filtered1Articles, page, pageCount)

        return filtered3Articles
    }

    private fun articlesFilterByPage(filtered1Articles: List<Article>, page: Int, pageCount: Int): List<Article> {
        val startIndex = filtered1Articles.lastIndex - ((page - 1) * pageCount)
        var endIndex = startIndex - pageCount + 1
        if(endIndex < 0){
            endIndex = 0
        }
        val filtered3Articles = mutableListOf<Article>()
        for(i in startIndex downTo endIndex){
            filtered3Articles.add(filtered1Articles[i])
        }
        return filtered3Articles
    }

    private fun articlesFilterByKeyword(keyword: String): List<Article> {
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

class ArticleController{
    fun add() {
        if(loginedMember == null){
            println("로그인 후 이용해주세요")
            return
        }
        var boardList = ""
        for(board in boardRepository.boards){
            if(boardList.isNotEmpty()){
                boardList += ", "
            }
            boardList += "${board.id} : ${board.name}"
        }
        println(boardList)
        print("게시판 선택(번호) : ")
        val boardId = readLineTrim().toInt()
        val board = boardRepository.getBoardById(boardId)
        if(board == null){
            println("없는 게시판 번호입니다.")
            return
        }
        print("제목 입력 : ")
        val title = readLineTrim()
        print("내용 입력 : ")
        val body = readLineTrim()
        val memberId = loginedMember!!.id
        val id = articleRepository.addArticle(title, body, memberId, boardId)
        println("$id 번 게시물 등록완료")
    }

    fun list(rq: Rq) {
        val keyword = rq.getStringParam("keyword","")
        val page = rq.getIntParam("page",1)
        val filteredArticles = articleRepository.articlesFilter(keyword, page, 5)

        for(article in filteredArticles){
            val member = memberRepository.getMemberById(article.memberId)
            val nickName = member!!.nickName

            val board = boardRepository.getBoardById(article.boardId)
            val boardName = board!!.name

            println("${article.id} / ${boardName} / ${article.title} / ${article.regDate} / 작성자 : ${nickName}")
        }
    }

    fun detail(rq: Rq) {
        val id = rq.getIntParam("id",0)
        if(id == 0){
            println("게시물 번호를 입력해주세요")
            return
        }
        val article = articleRepository.getArticleById(id)
        if(article == null){
            println("없는 게시물 번호입니다.")
            return
        }
        println("번호 : ${article.id}")
        println("제목 : ${article.title}")
        println("내용 : ${article.body}")
        println("등록일 : ${article.regDate}")
        println("수정일 : ${article.updateDate}")
    }

    fun delete(rq: Rq) {
        if(loginedMember == null){
            println("로그인 후 이용해주세요")
            return
        }
        val id = rq.getIntParam("id",0)
        if(id == 0){
            println("게시물 번호를 입력해주세요")
            return
        }
        val article = articleRepository.getArticleById(id)
        if(article == null){
            println("없는 게시물 번호입니다.")
            return
        }
        if(loginedMember!!.id != article.memberId){
            println("권한이 없습니다.")
            return
        }
        articleRepository.articles.remove(article)
        println("$id 번 게시물이 삭제되었습니다.")
    }

    fun modify(rq: Rq) {
        if(loginedMember == null){
            println("로그인 후 이용해주세요")
            return
        }
        val id = rq.getIntParam("id",0)
        if(id == 0){
            println("게시물 번호를 입력해주세요")
            return
        }
        val article = articleRepository.getArticleById(id)
        if(article == null){
            println("없는 게시물 번호입니다.")
            return
        }
        if(loginedMember!!.id != article.memberId){
            println("권한이 없습니다.")
            return
        }
        print("새 제목 : ")
        val title = readLineTrim()
        print("새 내용 : ")
        val body = readLineTrim()
        val updateDate = Util.getDateNowStr()
        article.title = title
        article.body = body
        article.updateDate = updateDate
        println("$id 번 게시물 수정완료")
    }


}




fun readLineTrim() = readLine()!!.trim()

object Util{
    fun getDateNowStr() : String{
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        return format.format(System.currentTimeMillis())
    }
}