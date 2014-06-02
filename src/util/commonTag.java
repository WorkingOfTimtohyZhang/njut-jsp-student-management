package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import db.dbconn;

public class commonTag {
    /**
     * 功能：得到班级下拉框字符串
     * 输入参数：班级ID号
     * 输出：班级下拉框字符串
     */
    public String getClassTag(int class_id){
        String returnString=new String("");
        returnString="<select name='class_id'>";
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn!=null)//连接成功
            //-------查询出数据------------
            try{
                sqlString="select * from class"; 
                sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs=sql.executeQuery(sqlString); 
                returnString=returnString+"<option value='0'>==所有==</option>";
                if(class_id==0){//所有班级
                    while(rs.next()){
                        returnString=returnString+"<option value='" +
                        		rs.getLong("class_id")+"'>" +
                        		rs.getString("class_name")+"</option>";
                    }
            	}else{//选定了某一个班级
            	    while(rs.next()){
                        if(rs.getLong("class_id")==(long)class_id){
                            returnString=returnString+"<option value='" +
                    		rs.getLong("class_id")+"' selected>" +
                    		rs.getString("class_name")+"</option>";
                        }else{
                            returnString=returnString+"<option value='" +
                    		rs.getLong("class_id")+"'>" +
                    		rs.getString("class_name")+"</option>";
                        }
            	    }
            	}
            }catch(Exception e){
                System.out.print(e);
            }
        returnString=returnString+"</select>";
        return returnString;
    }
    /**
     * 功能：得到课程下拉框字符串
     * 输入参数：课程ID号
     * 输出：课程下拉框字符串
     */
    public String getLessionTag(int lession_id){
        String returnString=new String("");
        returnString="<select name='lession_id'>";
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn!=null)//连接成功
            //-------查询出数据------------
            try{
                sqlString="select * from lession"; 
                sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs=sql.executeQuery(sqlString); 
                returnString=returnString+"<option value='0'>==所有==</option>";
                if(lession_id==0){//所有课程
                    while(rs.next()){
                        returnString=returnString+"<option value='" +
                        		rs.getLong("lession_id")+"'>" +
                        		rs.getString("lession_name")+"</option>";
                    }
            	}else{//选定了某一个课程
            	    while(rs.next()){
                        if(rs.getLong("lession_id")==(long)lession_id){
                            returnString=returnString+"<option value='" +
                    		rs.getLong("lession_id")+"' selected>" +
                    		rs.getString("lession_name")+"</option>";
                        }else{
                            returnString=returnString+"<option value='" +
                    		rs.getLong("lession_id")+"'>" +
                    		rs.getString("lession_name")+"</option>";
                        }
            	    }
            	}
            }catch(Exception e){
                System.out.print(e);
            }
        returnString=returnString+"</select>";
        return returnString;
    }
    /**
     * 功能：得到教师下拉框字符串
     * 输入参数：教师ID号
     * 输出：教师下拉框字符串
     */
    public String getTeacherTag(int teacher_id){
        String returnString=new String("");
        returnString="<select name='teacher_id'>";
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn!=null)//连接成功
            //-------查询出数据------------
            try{
                sqlString="select * from teacher"; 
                sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs=sql.executeQuery(sqlString); 
                returnString=returnString+"<option value='0'>==所有==</option>";
                if(teacher_id==0){//所有课程
                    while(rs.next()){
                        returnString=returnString+"<option value='" +
                        		rs.getLong("teacher_id")+"'>" +
                        		rs.getString("teacher_name")+"</option>";
                    }
            	}else{//选定了某一个课程
            	    while(rs.next()){
                        if(rs.getLong("teacher_id")==(long)teacher_id){
                            returnString=returnString+"<option value='" +
                    		rs.getLong("teacher_id")+"' selected>" +
                    		rs.getString("teacher_name")+"</option>";
                        }else{
                            returnString=returnString+"<option value='" +
                    		rs.getLong("teacher_id")+"'>" +
                    		rs.getString("teacher_name")+"</option>";
                        }
            	    }
            	}
            }catch(Exception e){
                System.out.print(e);
            }
        returnString=returnString+"</select>";
        return returnString;
    }    
    /**
     * 功能：得到角色下拉框字符串
     * 输入参数：角色ID号
     * 输出：角色下拉框字符串
     */
    public String getRoleTag(){
        String returnString=new String("");
        returnString="<select name='role_id'>";
        returnString=returnString+"<option value='1'>系统管理员</option>";
        returnString=returnString+"<option value='2'>教务管理员</option>";
        returnString=returnString+"</select>";
        return returnString;
    }      
}
