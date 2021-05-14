
val articleRepository = ArticleRepository()
val memberRepository = MemberRepository()
val boardRepository = BoardRepository()

var loginedMember : Member? = null

fun main(){

    val articleController = ArticleController()
    val memberController = MemberController()
    val boardController = BoardController()

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