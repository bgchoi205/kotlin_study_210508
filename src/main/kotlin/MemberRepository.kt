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
