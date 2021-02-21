package wx.enums;



public enum ChatTypeEnum {

    doctorListChatType("doctor_list","医生列表"),
    doctorChatType("doctor_chat","医生聊天"),
    userChatType("user_chat","用户聊天"),
    chatCountType("chat_count","聊天数目");


    private String key;

    public String value;

    ChatTypeEnum(){

    }

    ChatTypeEnum(String key,String value){
        this.key=key;
        this.value=value;
    }

    public void setKey(String key){
        this.key=key;
    }


    public String getKey(){
        return key;
    }

    public String getValue(){
        return value;
    }

    public void setValue(){
        this.value=value;
    }


}
