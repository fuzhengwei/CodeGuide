package 紫禁城.内务府.敬事房.利器库;

import org.springframework.stereotype.Component;
import 紫禁城.内务府.敬事房.膑.太监膑;
import 紫禁城.内务府.登记处.宦官;

@Component("切除")
public class 切除器具 {

    public 太监膑 军刺切(太监膑 太监){
        太监.set性别(宦官.太监.name());
        System.out.println("... 啊 ... ...老子被切面了！"+太监.get姓名());
        return 太监;
    }

}