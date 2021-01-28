package wx.util;

import lombok.Data;

/*
* @wangxi
* 2021-01-26
* status 0==>成功
* status 1==>失败
* */
@Data
public class Result {
    private Object data;
    private String message;
    private Integer status;

    public Result(){}


    public Result(Object data){
        this.data=data;
    }
    public Result(Object data,String message,Integer status){
        this.data=data;
        this.message=message;
        this.status=status;
    }
}
