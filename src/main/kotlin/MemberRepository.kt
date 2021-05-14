class MemberRepository{

    fun getMembers(): List<Member>{
        val members = mutableListOf<Member>()
        val lastId = getLastMemberId()
        for(i in 1 .. lastId){
            val member = memberFromFile("data/member/$i.json")

            if(member != null){
                members.add(member)
            }
        }
        return members
    }

    private fun memberFromFile(filePath: String): Member? {
        val jsonStr = readStrFromFile(filePath)
        if(jsonStr == ""){
            return null
        }
        val map = mapFromJsonStr(jsonStr)
        val id = map["id"].toString().toInt()
        val loginId = map["loginId"].toString()
        val loginPw = map["loginPw"].toString()
        val name = map["name"].toString()
        val nickName = map["nickName"].toString()
        
        return Member(id, loginId, loginPw, name, nickName)
    }

    fun getLastMemberId() : Int{
        val lastMemberId = readIntFromFile("data/member/lastMemberId.txt", 0)
        return lastMemberId
    }

    fun getMemberByLoginId(loginId: String): Member? {
        for(member in getMembers()){
            if(member.loginId == loginId){
                return member
            }
        }
        return null
    }

    fun addMember(loginId: String, loginPw: String, name: String, nickName: String): Int {
        val id = getLastMemberId() + 1
        writeIntFile("data/member/lastMemberId.txt", id)
        val member = Member(id, loginId, loginPw, name, nickName)
        val jsonStr = member.toJson()
        writeStrFile("data/member/$id.json", jsonStr)
        return id
    }

    fun makeTestMembers(){
        for(i in 1..10){
            addMember("user$i", "user$i", "철수$i", "짱구$i")
        }
    }

    fun getMemberById(memberId: Int): Member? {
        val member = memberFromFile("data/member/$memberId.json")
        if(member != null){
            return member
        }
        return null
    }

}
