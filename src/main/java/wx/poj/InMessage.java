package wx.poj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InMessage {
    private String fromId;

    private String toId;

    private String content;

    private String avatar;

    private String time;

    public InMessage(String content){
        this.content=content;
    }

}
