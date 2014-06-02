package lessionman;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import util.stringUtil;
import db.dbconn;
/**
 * 封装对课程表的所有操作
 * @author 邓子云
 */
public class lession_operation {
    /**
     * 功能:往课程表增加一条记录
     * 输入参数:lession_name为课程姓名
     * 输出:返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败或数据库操作失败，
     * 返回3表此班级已存在，返回4表输入参数class_name为空;
     */
    public int lession_add_one(String lession_name){
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 2;//连接失败
        //--------输入参数不正确---------
        if(lession_name==null||lession_name.trim().length()==0)
           return 4;
        //--------输入参数编码转换-------
        stringUtil stringCode=new stringUtil();
        lession_name=stringCode.codeToString(lession_name.trim());
        //--------插入记录，先判断是否已存在------
        sqlString="select * from lession where lession_name=?";
        try{
           PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
           preSQLSelect.setString(1,lession_name);
           rs=preSQLSelect.executeQuery();
           if(rs.next()) return 3;//已存在此课程
           sqlString="insert into lession(lession_name) values(?)";
           PreparedStatement preSQLInsert=dbconn.prepareStatement(sqlString);
           preSQLInsert.setString(1,lession_name);
           preSQLInsert.executeUpdate();
           return 1;
         }catch(Exception e){
           System.out.print(e);
           return 2;
         }             
    }
    /**
     * 功能:查询出所有的课程
     * 输入参数：无
     * 输出:所有课程的记录集,如果没有记录或操作失败返回null
     */
    public ResultSet lession_select_all(){
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        try{
           //-------查询出数据------------
           sqlString="select * from lession"; 
           sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           rs=sql.executeQuery(sqlString);
           return rs;
        }catch(Exception e){
           System.out.print(e);
           return null;
        }  
    }
    /**
     * 功能：删除一个课程的信息
     * 输入参数：课程的ID号
     * 输出：返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败，
     * 返回4表输入参数lession_id为0，即输入参数不正确
     */
    public int lession_delete(int lession_id){
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 2;//连接失败
        if(lession_id==0) return 4;//输入参数不正确
        //---------删除数据--------------
        sqlString="delete from lession where lession_id=?";
        try{
            PreparedStatement preSQLDelete=dbconn.prepareStatement(sqlString);
            preSQLDelete.setInt(1,lession_id);
            preSQLDelete.executeUpdate();
            return 1;
          }catch(Exception e){
            System.out.print(e);
            return 2;
          }   
    }
    /**
     * 功能:查询出某一课程的信息
     * 输入参数:课程的ID
     * 输出:此条课程记录,如果没有记录或操作失败返回null
     */
    public ResultSet lession_select_one(int lession_id){
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        if(lession_id==0) return null;//输入参数不正确
        try{
           //-------查询出数据------------
           sqlString="select * from lession where lession_id=?"; 
           PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
           preSQLSelect.setInt(1,lession_id);
           rs=preSQLSelect.executeQuery();
           return rs;
        }catch(Exception e){
           System.out.print(e);
           return null;
        }  
    }
    /**
     * 功能:更新课程表的一条记录
     * 输入参数:lession_id为课程号，lession_name为课程名称
     * 输出:返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败或数据库操作失败，
     * 返回3表记录已存在，返回4表输入参数为空或不正确;
     */
    public int lession_update(int lession_id,String lession_name){
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 2;//连接失败
        if(lession_id==0||lession_name.trim().length()==0) return 4;//输入参数不正确
        //--------输入参数编码转换-------
        stringUtil stringCode=new stringUtil();
        lession_name=stringCode.codeToString(lession_name.trim());
        //--------更新记录，先判断是否已存在------
        sqlString="select * from lession where lession_name=? and lession_id<>"+lession_id;
        try{
            PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
            preSQLSelect.setString(1,lession_name);
            rs=preSQLSelect.executeQuery();
            if(rs.next()) return 3;//已存在此课程
            //--------更新记录-----------
            sqlString="update lession set lession_name=? where lession_id=?";
            PreparedStatement preSQLUpdate=dbconn.prepareStatement(sqlString);
            preSQLUpdate.setString(1,lession_name);
            preSQLUpdate.setInt(2,lession_id);
            preSQLUpdate.executeUpdate();
            return 1;
        }catch(Exception e){
            System.out.print(e);
            return 2;
        }     
    }  
}
