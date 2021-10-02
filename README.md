# UnchiOutputStream

!! caution !! caution !! caution !! caution !! caution !!  
これはジョークリポジトリです！  
必ずともあなたのアプリケーション開発を助けるものであるとは限りません！  
!! caution !! caution !! caution !! caution !! caution !!  

## Description
ひたすらうんちを垂れ流すOutputStreamです。  
至る所で見かける、↓のようなコピペにインスピレーションを受けて作りました。

```text
あああああああああああああああああああああああああああああああ！！！！！！！！！！！（ﾌﾞﾘﾌﾞﾘﾌﾞﾘﾌﾞﾘｭﾘｭﾘｭﾘｭﾘｭﾘｭ！！！！！！ﾌﾞﾂﾁﾁﾌﾞﾌﾞﾌﾞﾁﾁﾁﾁﾌﾞﾘﾘｲﾘﾌﾞﾌﾞﾌﾞﾌﾞｩｩｩｩｯｯｯ！！！！！！！ ） 
```

## Demo / Usage

```java
import java.io.IOException;

public class Demo {
    public static void main(String[] args) throws IOException {
        String testText = "本日は晴天でござる";

        var baos = new ByteArrayOutputStream();
        var uos = new UnchiOutputStream(baos);
        uos.write(testText.getBytes(StandardCharsets.UTF_8));
        uos.flush();
        uos.close();

        String actual = baos.toString(StandardCharsets.UTF_8);

        // ﾌﾞﾘﾘﾘﾘﾌﾞﾘﾌﾞﾌﾞｯｯｯｯｯﾌﾞﾘﾘﾘﾘﾘﾘﾘﾘ!!!!ﾌﾞﾘﾘﾘ!!!!ﾌﾞｯｯｯｯｯﾌﾞﾌﾞｯｯｯｯｯﾌﾞﾂｯｯｯｯｯﾌﾞｯｯｯｯｯｯﾌﾞﾘﾘﾘﾘﾘﾘﾘﾘ!!!!!!!!!!!!
        System.out.println(actual);
    }
}
```

## Installation