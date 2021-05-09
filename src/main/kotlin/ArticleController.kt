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
        val boardId = rq.getIntParam("boardId",0)
        val filteredArticles = articleRepository.articlesFilter(keyword, boardId, page, 5)

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
