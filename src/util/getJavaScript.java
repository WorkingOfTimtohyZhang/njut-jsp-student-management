package util;
import java.sql.ResultSet;
public class getJavaScript {
    public String getCoreCheckJavaScript(ResultSet rs){
        String returnString=new String("");
        try{
            rs.last();
            int rowCount=rs.getRow();
            rs.beforeFirst();
            if(rowCount!=0){
                returnString="<script language='JavaScript'><!--function check_core_data() {";
                while(rs.next()){ 
                    returnString=returnString+"var txt = document.student_core." +
                    		""+rs.getLong("student_id")+".value;";
                    returnString=returnString+"if(txt.search('^\\d+(\\.\\d+)*$')!=0) {";
                    returnString=returnString+"alert('请输入一个数字!');";
                    //returnString=returnString+"document.student_core."+rs.getLong("student_id")+".setfocus();";
                    returnString=returnString+"return false;}";
                }
                returnString=returnString+"}--></script>";
            }
        }catch(Exception e){
            System.out.println(e);
        }  
        return returnString;
    }
}
