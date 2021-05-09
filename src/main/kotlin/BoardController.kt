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