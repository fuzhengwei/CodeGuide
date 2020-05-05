package 紫禁城.内务府.敬事房.刑房处;

import org.springframework.stereotype.Component;
import 紫禁城.内务府.敬事房.利器库.切除器具;
import 紫禁城.内务府.敬事房.膑.太监膑;

import javax.annotation.Resource;

@Component("太监刑")
public class 太监刑 {

    private 切除器具 切除;


    public 切除器具 get切除() {
        return 切除;
    }

    @Resource(name="切除")
    public void set切除(切除器具 切除) {
        this.切除 = 切除;
    }

    public 太监膑 执行切除(太监膑 太监){
        return 切除.军刺切(太监);
    }

}