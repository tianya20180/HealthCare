package wx.poj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class InMessage {
    private String fromId;

    private String toId;

    private String content;

    private String avatar;

    private String time;

    private String orderId;



    public InMessage(String content){
        this.content=content;
    }

}
