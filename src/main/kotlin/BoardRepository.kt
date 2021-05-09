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
