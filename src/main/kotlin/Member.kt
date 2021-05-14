// Member
data class Member(
    val id : Int,
    val loginId : String,
    val loginPw : String,
    val name : String,
    val nickName : String
){
    fun toJson() : String{
        var jsonStr = ""
        jsonStr += "{"
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "id":$id """.trim() + ","
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "loginId":"$loginId" """.trim() + ","
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "loginPw":"$loginPw" """.trim() + ","
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "name":"$name" """.trim() + ","
        jsonStr += "\r\n"
        jsonStr += "\t" + """ "nickName":"$nickName" """.trim()
        jsonStr += "\r\n"
        jsonStr += "}"
        return jsonStr
    }

}