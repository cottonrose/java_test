//String类扩展功能实现
public class Strings{

    public  static void main(String[] args){
        System.out.println("重复某个字符方法测试如下：");
        System.out.println(repeat('b',5));
        System.out.println(repeat('b',0));
        System.out.println(repeat('b',-1));
        System.out.println(repeat('2',2));
        System.out.println("字符串前填充方法测试如下：");
        System.out.println(fillBefore("abc",'A',5)); //AAabc
        System.out.println(fillBefore("abc",'A',3));//abc
        System.out.println(fillBefore("",'A',5)); //AAAAA
        System.out.println(fillBefore("abcdef",'A',5));//abcdef
        System.out.println("字符串后填充方法测试如下：");
        System.out.println(fillAfter("abc",'A',5)); //abcAA
        System.out.println(fillAfter("abc",'A',3));//abc
        System.out.println(fillAfter("",'A',5)); //AAAAA
        System.out.println(fillAfter("abcdef",'A',5));//abcdef
        System.out.println("移除字符串中所有给定字符串方法测试如下：");
        System.out.println(removeAll("aa-bb-cc--dd","-"));
        System.out.println(removeAll("aa-bb-cc--dd","--"));
        System.out.println(removeAll("aa-bb-cc--dd","hh"));
        System.out.println(removeAll("","--"));
        System.out.println("反转字符串方法测试如下：");
        System.out.println(reverse("abcdefg"));
        System.out.println(reverse(""));
        System.out.println("a");


    }
     /**
     * 重复某个字符
     * 
     * 例如： 
     * 'a' 5   => "aaaaa"  
     * 'a' -1  => ""
     * 
     * @param c     被重复的字符
     * @param count 重复的数目，如果小于等于0则返回""
     * @return 重复字符字符串
     */
    public static String repeat(char c, int count) {
        if(count<=0) {
            return "";
        }
        StringBuffer str = new StringBuffer();
        while (count>0){
            str.append(String.valueOf(c));
            count--;
        }
       return str.substring(0);

//        测试用例
//        System.out.println(repeat('b',5));
//        System.out.println(repeat('b',0));
//        System.out.println(repeat('b',-1));
//        System.out.println(repeat('2',2));
    }
    
    
     /**
     * 将已有字符串填充为规定长度，如果已有字符串超过这个长度则返回这个字符串
     * 字符填充于字符串前
     *
     * 例如： 
     * "abc" 'A' 5  => "AAabc"
     * "abc" 'A' 3  => "abc"
     *
     * @param str        被填充的字符串
     * @param filledChar 填充的字符
     * @param len        填充长度
     * @return 填充后的字符串
     */
    public static String fillBefore(String str, char filledChar, int len) {
        if(str.length()>=len){
            return str;
        }
        int count = len - str.length();
        StringBuffer s = new StringBuffer();
        while(count>0){
            s.insert(0,filledChar);
            count--;
        }
       return s.substring(0)+str;
//        测试用例
//        System.out.println(fillBefore("abc",'A',5)); //AAabc
//        System.out.println(fillBefore("abc",'A',3));//abc
//        System.out.println(fillBefore("",'A',5)); //AAAAA
//        System.out.println(fillBefore("abcdef",'A',5));//abcdef
    }
    
    /**
     * 将已有字符串填充为规定长度，如果已有字符串超过这个长度则返回这个字符串<br>
     * 字符填充于字符串后
     * 例如： 
     * "abc" 'A' 5  => "abcAA"
     * "abc" 'A' 3  => "abc"
     *
     * @param str        被填充的字符串
     * @param filledChar 填充的字符
     * @param len        填充长度
     * @return 填充后的字符串
     */
    public static String fillAfter(String str, char filledChar, int len) {
        if(str.length()>=len){
            return str;
        }
        int count = len - str.length();
        StringBuffer s = new StringBuffer();
        while(count>0){
            s.append(filledChar);
            count--;
        }
        return str+s.substring(0);
//        测试用例
//        System.out.println(fillAfter("abc",'A',5)); //abcAA
//        System.out.println(fillAfter("abc",'A',3));//abc
//        System.out.println(fillAfter("",'A',5)); //AAAAA
//        System.out.println(fillAfter("abcdef",'A',5));//abcdef
    }

    /**
     * 移除字符串中所有给定字符串
     * 例：removeAll("aa-bb-cc-dd", "-") => aabbccdd
     *
     * @param str         字符串
     * @param strToRemove 被移除的字符串
     * @return 移除后的字符串
     */
    public static String removeAll(CharSequence str, CharSequence strToRemove) {
        if(str==null){
            return null;
        }
        String s = new String(str.toString());
        if(s.contains(strToRemove)){
            s = s.replace(strToRemove,"");
        }
        return s;
//        测试用例
//        System.out.println(removeAll("aa-bb-cc--dd","-")); //aabbccdd
//        System.out.println(removeAll("aa-bb-cc--dd","--")); //aa-bb-ccdd
//        System.out.println(removeAll("aa-bb-cc--dd","hh")); //aa-bb-cc--dd
//        System.out.println(removeAll("","--")); //
    }
    
    /**
     * 反转字符串
     * 例如：abcd => dcba
     *
     * @param str 被反转的字符串
     * @return 反转后的字符串
     */
    public static String reverse(String str) {
        if(str == null){
            return null;
        }
        if(str.length()<=1){
            return str;
        }
        char[] charArray = str.toCharArray();
        int left = 0;
        int right = charArray.length-1;
        char tmp;
        while(left<=right){
            tmp = charArray[left];
            charArray[left] = charArray[right];
            charArray[right] = tmp;
            left++;
            right--;
        }
        String s = new String(charArray);
        return s;
//        测试用例
//        System.out.println(reverse("abcdefg")); //gfedcba
//        System.out.println(reverse(""));//
//        System.out.println("a");//a
    }
}
