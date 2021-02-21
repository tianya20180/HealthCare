package wx.poj;

import java.util.Date;

public class OutMessage {
    private String from;

    private String content;

    private Date time=new Date();

    public OutMessage(String content){
        this.content=content;
    }

}
