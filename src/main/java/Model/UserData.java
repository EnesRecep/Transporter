package Model;

import java.io.Serializable;

/**
 * Created by Enes Recep on 23.12.2018.
 */
public class UserData implements Serializable{
    private Object object;

    public UserData(Object object){
        this.object = object;
    }


    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
