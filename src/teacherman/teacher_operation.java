package teacherman;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import util.stringUtil;
import db.dbconn;
/**
 * 封装对教师表的所有操作
 * @author 邓子云
 */
public class teacher_operation {
    /**
     * 功能:往教师表增加一条记录
     * 输入参数:teacher_name为教师姓名
     * 输出:返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败或数据库操作失败，
     * 返回3表此班级已存在，返回4表输入参数class_name为空;
     */
    public int teacher_add_one(String teacher_name){
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 2;//连接失败
        //--------输入参数不正确---------
        if(teacher_name==null||teacher_name.trim().length()==0)
           return 4;
        //--------输入参数编码转换-------
        stringUtil stringCode=new stringUtil();
        teacher_name=stringCode.codeToString(teacher_name.trim());
        //--------插入记录，先判断是否已存在------
        sqlString="select * from teacher where teacher_name=?";
        try{
           PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
           preSQLSelect.setString(1,teacher_name);
           rs=preSQLSelect.executeQuery();
           if(rs.next()) return 3;//已存在此教师
           sqlString="insert into teacher(teacher_name) values(?)";
           PreparedStatement preSQLInsert=dbconn.prepareStatement(sqlString);
           preSQLInsert.setString(1,teacher_name);
           preSQLInsert.executeUpdate();
           return 1;
         }catch(Exception e){
           System.out.print(e);
           return 2;
         }             
    }
    /**
     * 功能:查询出所有的教师
     * 输入参数：无
     * 输出:所有教师的记录集,如果没有记录或操作失败返回null
     */
    public ResultSet teacher_select_all(){
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        try{
           //-------查询出数据------------
           sqlString="select * from teacher"; 
           sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           rs=sql.executeQuery(sqlString);
           return rs;
        }catch(Exception e){
           System.out.print(e);
           return null;
        }  
    }
    /**
     * 功能：删除一个教师的信息
     * 输入参数：教师的ID号
     * 输出：返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败，
     * 返回4表输入参数teacher_id为0，即输入参数不正确
     */
    public int teacher_delete(int teacher_id){
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 2;//连接失败
        if(teacher_id==0) return 4;//输入参数不正确
        //---------删除数据--------------
        sqlString="delete from teacher where teacher_id=?";
        try{
            PreparedStatement preSQLDelete=dbconn.prepareStatement(sqlString);
            preSQLDelete.setInt(1,teacher_id);
            preSQLDelete.executeUpdate();
            return 1;
          }catch(Exception e){
            System.out.print(e);
            return 2;
          }   
    }
    /**
     * 功能:查询出某一教师的信息
     * 输入参数:教师的ID
     * 输出:此条教师记录,如果没有记录或操作失败返回null
     */
    public ResultSet teacher_select_one(int teacher_id){
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        if(teacher_id==0) return null;//输入参数不正确
        try{
           //-------查询出数据------------
           sqlString="select * from teacher where teacher_id=?"; 
           PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
           preSQLSelect.setInt(1,teacher_id);
           rs=preSQLSelect.executeQuery();
           return rs;
        }catch(Exception e){
           System.out.print(e);
           return null;
        }  
    }
    /**
     * 功能:更新教师表的一条记录
     * 输入参数:teacher_id为教师号，teacher_name为教师名称
     * 输出:返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败或数据库操作失败，
     * 返回3表记录已存在，返回4表输入参数为空或不正确;
     */
    public int teacher_update(int teacher_id,String teacher_name){
        String sqlString=null;//SQL语句字符串
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        ResultSet rs=null;//结果记录集
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 2;//连接失败
        if(teacher_id==0||teacher_name.trim().length()==0) return 4;//输入参数不正确
        //--------输入参数编码转换-------
        stringUtil stringCode=new stringUtil();
        teacher_name=stringCode.codeToString(teacher_name.trim());
        //--------更新记录，先判断是否已存在------
        sqlString="select * from teacher where teacher_name=? and teacher_id<>"+teacher_id;
        try{
            PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
            preSQLSelect.setString(1,teacher_name);
            rs=preSQLSelect.executeQuery();
            if(rs.next()) return 3;//已存在此教师
            //--------更新记录-----------
            sqlString="update teacher set teacher_name=? where teacher_id=?";
            PreparedStatement preSQLUpdate=dbconn.prepareStatement(sqlString);
            preSQLUpdate.setString(1,teacher_name);
            preSQLUpdate.setInt(2,teacher_id);
            preSQLUpdate.executeUpdate();
            return 1;
        }catch(Exception e){
            System.out.print(e);
            return 2;
        }     
    }  
    /**
     * 功能:根据姓名查询出部分老师的信息
     * 输入参数：老师姓名
     * 输出:部分教师的记录集,如果没有记录或操作失败返回null
     * 说明:支持模糊查询
     */
    public ResultSet teacher_select_part_by_name(String teacher_name){
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        if(teacher_name==null||teacher_name.trim().length()==0)
            return teacher_select_all();
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        try{
           //-------查询出数据------------
           sqlString="select * from teacher where teacher_name like '%"+teacher_name+"%'"; 
           sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           rs=sql.executeQuery(sqlString);
           return rs;
        }catch(Exception e){
           System.out.print(e);
           return null;
        }  
    }
}
