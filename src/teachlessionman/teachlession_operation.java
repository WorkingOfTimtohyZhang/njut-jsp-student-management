package teachlessionman;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import db.dbconn;
/**
 * 封装对教师授课表的所有操作
 * @author 邓子云
 */
public class teachlession_operation {
    /**
     * 功能:往教师授课表增加一条记录
     * 输入参数:lession_id为课程ID,teacher_id为教师ID
     * 输出:返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败或数据库操作失败，
     * 返回3表此教师授课信息已存在，返回4表输入参数lession_id或teacher_id为空;
     */
    public int teachlession_add_one(int lession_id,int teacher_id){
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 2;//连接失败
        //--------输入参数不正确---------
        if(lession_id==0||teacher_id==0)
           return 4;
        //--------插入记录，先判断是否已存在------
        sqlString="select * from teachlession where lession_id=? and teacher_id=?";
        try{   
           PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
           preSQLSelect.setInt(1,lession_id);
           preSQLSelect.setInt(2,teacher_id);
           rs=preSQLSelect.executeQuery();
           if(rs.next()) return 3;//已存在此教师授课记录
           sqlString="insert into teachlession(lession_id,teacher_id) values(?,?)";
           PreparedStatement preSQLInsert=dbconn.prepareStatement(sqlString);
           preSQLInsert.setInt(1,lession_id);
           preSQLInsert.setInt(2,teacher_id);  
           preSQLInsert.executeUpdate();
           return 1;
         }catch(Exception e){
           System.out.print(e);
           return 2;
         }             
    }
    /**
     * 功能:查询出所有的教师授课信息
     * 输入参数：无
     * 输出:所有教师授课的记录集,如果没有记录或操作失败返回null
     */
    public ResultSet teachlession_select_all(){
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        try{
           //-------查询出数据------------
           sqlString="select distinct teachlession.teachlession_id as teachlession_id," +
           		"teachlession.teacher_id as teacher_id," +
           		"teachlession.lession_id as lession_id," +
           		"teacher.teacher_name as teacher_name," +
           		"lession.lession_name as lession_name"+
           		" from teachlession,teacher,lession " +
           		"where lession.lession_id=teachlession.lession_id" +
           		" and teacher.teacher_id=teachlession.teacher_id"; 
           sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           rs=sql.executeQuery(sqlString);
           return rs;
        }catch(Exception e){
           System.out.print(e);
           return null;
        }  
    }
    /**
     * 功能:根据条件查询出教师授课的信息
     * 输入参数：lession_id为课程ID号，teacher_id为教师ID号
     * 输出:查询需要的教师授课的记录集,如果没有记录或操作失败返回null
     */
    public ResultSet teachlession_select_part(int lession_id,int teacher_id){
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        try{
           //-------查询出数据------------
            sqlString="select distinct teachlession.teachlession_id as teachlession_id," +
       			"teachlession.teacher_id as teacher_id," +
       			"teachlession.lession_id as lession_id," +
       			"teacher.teacher_name as teacher_name," +
       			"lession.lession_name as lession_name"+
       			" from teachlession,teacher,lession " +
       			"where lession.lession_id=teachlession.lession_id" +
       			" and teacher.teacher_id=teachlession.teacher_id"; 
           if(lession_id!=0)
               sqlString=sqlString+" and lession.lession_id="+lession_id+" and " +
               		"teachlession.lession_id="+lession_id;
           if(teacher_id!=0)
               sqlString=sqlString+" and teacher.teacher_id="+teacher_id+" and " +
               		"teachlession.teacher_id="+teacher_id;   
           sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           rs=sql.executeQuery(sqlString);
           return rs;
        }catch(Exception e){
           System.out.print(e);
           return null;
        }  
    }
    /**
     * 功能：删除一个教师授课表中的信息
     * 输入参数：教师授课表中的ID号
     * 输出：返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败，
     * 返回4表输入参数teachlession_id为0，即输入参数不正确
     */
    public int teachlession_delete(long teachlession_id){
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 2;//连接失败
        if(teachlession_id==0) return 4;//输入参数不正确
        //---------删除数据--------------
        sqlString="delete from teachlession where teachlession_id=?";
        try{
            PreparedStatement preSQLDelete=dbconn.prepareStatement(sqlString);
            preSQLDelete.setLong(1,teachlession_id);
            preSQLDelete.executeUpdate();
            return 1;
          }catch(Exception e){
            System.out.print(e);
            return 2;
          }   
    }
    /**
     * 功能:查询出某一条教师授课表中的信息
     * 输入参数:教师授课表中的ID号
     * 输出:此条教师授课表中的记录,如果没有记录或操作失败返回null
     */
    public ResultSet teachlession_select_one(long teachlession_id){
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        if(teachlession_id==0) return null;//输入参数不正确
        try{
           //-------查询出数据------------
           sqlString="select * from teachlession where teachlession_id=?"; 
           PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
           preSQLSelect.setLong(1,teachlession_id);
           rs=preSQLSelect.executeQuery();
           return rs;
        }catch(Exception e){
           System.out.print(e);
           return null;
        }  
    }
    /**
     * 功能:更新教师授课表的一条记录
     * 输入参数:teachlession_id为教师授课序号，lession_id为课程ID,teacher_id为老师ID
     * 输出:返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败或数据库操作失败，
     * 返回3表记录已存在，返回4表输入参数为空或不正确;
     */
    public int teachlession_update(long teachlession_id,int lession_id,int teacher_id){
        String sqlString=null;//SQL语句字符串
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        ResultSet rs=null;//结果记录集
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 2;//连接失败
        if(teachlession_id==0||lession_id==0||teacher_id==0){//输入参数不正确
            return 4;
        }
        //--------更新记录，先判断是否已存在------
        sqlString="select * from teachlession where lession_id=? and teacher_id=? and teachlession_id<>"+teachlession_id;
        try{
            PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
            preSQLSelect.setInt(1,lession_id);
            preSQLSelect.setInt(2,teacher_id);
            rs=preSQLSelect.executeQuery();
            if(rs.next()) return 3;//已存在此教师授课记录
            //--------更新记录-----------
            sqlString="update teachlession set lession_id=?,teacher_id=? where teachlession_id=?";
            PreparedStatement preSQLUpdate=dbconn.prepareStatement(sqlString);
            preSQLUpdate.setInt(1,lession_id);
            preSQLUpdate.setInt(2,teacher_id);
            preSQLUpdate.setLong(3,teachlession_id);
            preSQLUpdate.executeUpdate();
            return 1;
        }catch(Exception e){
            System.out.print(e);
            return 2;
        }     
    }  
}
