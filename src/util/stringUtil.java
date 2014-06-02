package util;
public class stringUtil {
    public String codeToString(String str)
    {//处理中文字符串的函数
      String s=str;
      try
        {
        byte tempB[]=s.getBytes("ISO-8859-1");
        s=new String(tempB);
        return s;
       }
      catch(Exception e)
       {
        return s;
       }  
    }
    public String getJSString(int role){//得到用户角色
        String returnString=new String("");
        switch(role){
            case 1:
            	returnString="系统管理员";break;
            case 2:
                returnString="教务管理员";break;
            case 3:
                returnString="教师";break;
            case 4:
                returnString="学生";break;
            default:
                returnString="未知";break;
        }
        return returnString;
    }
}
