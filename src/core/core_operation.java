package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import db.dbconn;

/**
 * 封装对学生成绩表的操作
 * @author Administrator
 */
public class core_operation {
    /**
     * 功能:往学生成绩表增加一条记录
     * 输入参数::lession_id为课程ID,student_id为学生ID
     * 输出:返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败或数据库操作失败，
     * 返回3表此学生成绩信息已存在，返回4表输入参数lession_id或student_id为空;
     */
    public int core_add_one(int lession_id,long student_id){
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 2;//连接失败
        //--------输入参数不正确---------
        if(lession_id==0||student_id==0)
           return 4;
        //--------插入记录，先判断是否已存在------
        sqlString="select * from core where lession_id=? and student_id=?";
        try{   
           PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
           preSQLSelect.setInt(1,lession_id);
           preSQLSelect.setLong(2,student_id);
           rs=preSQLSelect.executeQuery();
           if(rs.next()) return 3;//已存在此教师授课记录
           sqlString="insert into core(lession_id,student_id) values(?,?)";
           PreparedStatement preSQLInsert=dbconn.prepareStatement(sqlString);
           preSQLInsert.setInt(1,lession_id);
           preSQLInsert.setLong(2,student_id);  
           preSQLInsert.executeUpdate();
           return 1;
         }catch(Exception e){
           System.out.print(e);
           return 2;
         }             
    }
    /**
     * 功能:查询出所有的学生成绩信息
     * 输入参数：无
     * 输出:所有学生成绩的记录集,如果没有记录或操作失败返回null
     */
    public ResultSet core_select_all(){
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        try{
           //-------查询出数据------------
           sqlString="select distinct core.core_id as core_id," +
           		"core.student_id as student_id," +
           		"core.lession_id as lession_id," +
           		"student.student_name as student_name," +
           		"lession.lession_name as lession_name"+
           		" from core,student,lession " +
           		"where lession.lession_id=core.lession_id" +
           		" and student.student_id=core.student_id"; 
           sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           rs=sql.executeQuery(sqlString);
           return rs;
        }catch(Exception e){
           System.out.print(e);
           return null;
        }  
    }
    /**
     * 功能:根据条件查询出学生成绩的信息
     * 输入参数：lession_id为课程ID号，student_id为学生ID号
     * 输出:查询出需要的学生成绩的记录集,如果没有记录或操作失败返回null
     */
    public ResultSet core_select_part(int lession_id,long student_id){
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        try{
           //-------查询出数据------------
            sqlString="select distinct core.core_id as core_id," +
       			"core.student_id as student_id," +
       			"core.lession_id as lession_id," +
       			"student.student_name as student_name," +
       			"lession.lession_name as lession_name"+
       			" from core,student,lession " +
       			"where lession.lession_id=core.lession_id" +
       			" and student.student_id=core.student_id"; 
           if(lession_id!=0)
               sqlString=sqlString+" and lession.lession_id="+lession_id+" and " +
               		"core.lession_id="+lession_id;
           if(student_id!=0)
               sqlString=sqlString+" and student.student_id="+student_id+" and " +
               		"core.student_id="+student_id;   
           sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           rs=sql.executeQuery(sqlString);
           return rs;
        }catch(Exception e){
           System.out.print(e);
           return null;
        }  
    }
    /**
     * 功能：删除一个学生成绩表中的信息
     * 输入参数：学生成绩表中的ID号
     * 输出：返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败，
     * 返回4表输入参数core_id为0，即输入参数不正确
     */
    public int core_delete(long core_id){
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 2;//连接失败
        if(core_id==0) return 4;//输入参数不正确
        //---------删除数据--------------
        sqlString="delete from core where core_id=?";
        try{
            PreparedStatement preSQLDelete=dbconn.prepareStatement(sqlString);
            preSQLDelete.setLong(1,core_id);
            preSQLDelete.executeUpdate();
            return 1;
          }catch(Exception e){
            System.out.print(e);
            return 2;
          }   
    }
    /**
     * 功能:查询出某一条学生成绩表中的信息
     * 输入参数:学生成绩表中的ID号
     * 输出:此条学生成绩中的记录,如果没有记录或操作失败返回null
     */
    public ResultSet core_select_one(long core_id){
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        if(core_id==0) return null;//输入参数不正确
        try{
           //-------查询出数据------------
           sqlString="select * from core where core_id=?"; 
           PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
           preSQLSelect.setLong(1,core_id);
           rs=preSQLSelect.executeQuery();
           return rs;
        }catch(Exception e){
           System.out.print(e);
           return null;
        }  
    }
    /**
     * 功能:更新学生成绩表的一条记录
     * 输入参数:core_id为学生成绩序号，lession_id为课程ID,student_id为学生ID
     * 输出:返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败或数据库操作失败，
     * 返回3表记录已存在，返回4表输入参数为空或不正确;
     */
    public int core_update(long core_id,int lession_id,long student_id){
        String sqlString=null;//SQL语句字符串
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        ResultSet rs=null;//结果记录集
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 2;//连接失败
        if(core_id==0||lession_id==0||student_id==0){//输入参数不正确
            return 4;
        }
        //--------更新记录，先判断是否已存在------
        sqlString="select * from core where lession_id=? and student_id=? and core_id<>"+core_id;
        try{
            PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
            preSQLSelect.setInt(1,lession_id);
            preSQLSelect.setLong(2,student_id);
            rs=preSQLSelect.executeQuery();
            if(rs.next()) return 3;//已存在此学生成绩记录
            //--------更新记录-----------
            sqlString="update core set lession_id=?,student_id=? where core_id=?";
            PreparedStatement preSQLUpdate=dbconn.prepareStatement(sqlString);
            preSQLUpdate.setInt(1,lession_id);
            preSQLUpdate.setLong(2,student_id);
            preSQLUpdate.setLong(3,core_id);
            preSQLUpdate.executeUpdate();
            return 1;
        }catch(Exception e){
            System.out.print(e);
            return 2;
        }     
    }  
    /**
     * 功能:根据学生ID及课程ID得到学生成绩
     * 输入参数:student_id学生ID号,lession_id为课程ID号
     * 输出参数:成绩字符串,查找不到或出错返回“”
     */
    public String getStudent_lession_core(long student_id,int lession_id){
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        ResultSet rs=null;//结果记录集
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return "";//连接失败
        if(student_id==0||lession_id==0) return "";//输入参数不正确
        sqlString="select core from core where lession_id="+lession_id+" and" +
        		" student_id="+student_id;
        try{
            sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=sql.executeQuery(sqlString);
            if(rs.next()) return(rs.getString("core"));
            else return "";
        }catch(Exception e){
            System.out.print(e);
            return "";
        }      	
    }
    /**
     * 功能:批量保存学生成绩
     * 输入：refName为要修改的学生ID号数组，refValue为学生ID号数组数组对应的成绩值，
     *      lession_id为要修改成绩的课程ID号
     * 输出：修改成功与否的信息提示字符串
     */
    public String saveStudent_core(String refName[],String refValue[],int lession_id){
        String returnString=new String("");
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return returnString;//连接失败
        if(refName.length==0||refValue.length==0||lession_id==0)//没有学生成绩数据
            return returnString;
        PreparedStatement preSQLUpdate=null;//更新SQL语句对象
        for(int i=0;i<refName.length;i++){
            if(refName[i]!=null&&refValue[i]!=null)
              try{
                float core=Float.parseFloat(refValue[i]);
                if(core>=0&&core<=100){//输入成绩数据合法
                    sqlString="select * from core where lession_id=" +
            		""+lession_id+" and student_id="+refName[i];
                    sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    rs=sql.executeQuery(sqlString);
                    if(rs.next()){
                        sqlString="update core set core=? where lession_id=" +
                        	""+lession_id+" and student_id="+refName[i];
                        sqlString=sqlString+" and close_status=0";
                        preSQLUpdate=dbconn.prepareStatement(sqlString);
                        preSQLUpdate.setFloat(1,Float.parseFloat(refValue[i]));
                        int updateCount=preSQLUpdate.executeUpdate();
                        if(updateCount==1)
                            returnString=returnString+"更新序号为"+refName[i]+"的课程成绩成功!<br>";
                        else
                            returnString=returnString+"更新序号为"+refName[i]+"的课程成绩失败!原因:状态不正常!<br>";
                    }else{
                        sqlString="insert into core(lession_id,student_id,core) values(" +
                			""+lession_id+","+refName[i]+","+refValue[i]+")";
                        preSQLUpdate=dbconn.prepareStatement(sqlString);
                        preSQLUpdate.executeUpdate();
                        returnString=returnString+"插入序号为"+refName[i]+"的课程成绩成功!<br>";
                    }
                    System.out.println(sqlString);
                }
                else{
                    returnString=returnString+"插入或更新序号为"+refName[i]+"的课程成绩失败,原因:输入数据非法!<br>";
                }
              }catch(Exception e){
                System.out.println(e);
                System.out.println(sqlString);
                returnString=returnString+"更新序号为"+refName[i]+"的课程成绩失败!<br>";
              }    
        }
        return returnString;
    }
    /**
     * 功能：学生用学生成绩查询功能
     * 输入：class_id为学生所在班级ID号，student_name为学生名字
     * 输出：此学生的成绩记录集
     * 说明：只能查询出一个学生的成绩
     */
    public ResultSet student_core_view(int class_id,String student_name){
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        if(student_name==null||student_name.length()==0)
            return null;
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        try{
           //-------查询出数据------------
           sqlString="select student.student_name as student_name," +
           		"lession.lession_name as lession_name,core.core as core" +
           		" from student,core,lession,class" +
           		" where core.student_id=student.student_id and" +
           		" core.lession_id=lession.lession_id"; 
           if(class_id!=0)
               sqlString=sqlString+" and class.class_id="+class_id+" and " +
               		"student.class_id="+class_id;
           if(student_name!=null&&student_name.trim().length()!=0)
               sqlString=sqlString+" and student.student_name='" +
               		""+student_name+"'";    
           sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           //System.out.println(sqlString);
           rs=sql.executeQuery(sqlString);
           return rs;
        }catch(Exception e){
           e.printStackTrace();
           return null;
        }  
    }
    /**
     * 功能：得到班级某功课的所有成绩
     * @param class_id
     * @param lession_id
     * @return
     */
    public ResultSet getClassLessionCore(int class_id,int lession_id){
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        sqlString="select * from core";
        if(class_id==0||lession_id==0){
            if(!(lession_id==0&&class_id==0)){
                if(class_id==0)
                    sqlString=sqlString+" where lession_id="+lession_id;
                if(lession_id==0)
                    sqlString="select core.core as core from core,student " +
                    		" where student.student_id=core.student_id" +
                    		" and student.class_id="+class_id;
            }        
        }else{
            sqlString="select core.core as core from core,student " +
    			" where student.student_id=core.student_id" +
    			" and student.class_id="+class_id +"" +
    			" and core.lession_id="+lession_id;
        }
        try{  
            sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs=sql.executeQuery(sqlString);
            return rs;
        }catch(Exception e){
            System.out.print(e);
            return null;
         } 
        
    }
}
