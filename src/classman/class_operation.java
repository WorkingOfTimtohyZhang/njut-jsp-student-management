
package classman;
import db.dbconn;
import util.stringUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
/**
 * 封装对班级表的所有操作
 * @author 邓子云
 */
public class class_operation{
   /**
    * 功能:往班级表增加一条记录
    * 输入参数:class_name为班级名称
    * 输出:返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败或数据库操作失败，
    * 返回3表此班级已存在，返回4表输入参数class_name为空;
    */
   public int class_add_one(String class_name){
       dbconn dbconnOBject=new dbconn();//数据库连接对象
       String sqlString=null;//SQL语句字符串
       ResultSet rs=null;//结果记录集
       Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
       if(dbconn==null) return 2;//连接失败
       //--------输入参数不正确---------
       if(class_name==null||class_name.trim().length()==0)
          return 4;
       //--------输入参数编码转换-------
       stringUtil stringCode=new stringUtil();
       class_name=stringCode.codeToString(class_name.trim());
       //--------插入记录，先判断是否已存在------
       sqlString="select * from class where class_name=?";
       try{
          PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
          preSQLSelect.setString(1,class_name);
          rs=preSQLSelect.executeQuery();
          if(rs.next()) return 3;//已存在此班级
          sqlString="insert into class(class_name) values(?)";
          PreparedStatement preSQLInsert=dbconn.prepareStatement(sqlString);
          preSQLInsert.setString(1,class_name);
          preSQLInsert.executeUpdate();
          return 1;
        }catch(Exception e){
          System.out.print(e);
          return 2;
        }             
   }
   /**
    * 功能:查询出所有的班级
    * 输入参数：无
    * 输出:所有班级的记录集,如果没有记录或操作失败返回null
    */
   public ResultSet class_select_all(){
       String sqlString=null;//SQL语句字符串
       Statement sql=null;//SQL语句对象
       ResultSet rs=null;//结果记录集
       dbconn dbconnOBject=new dbconn();//数据库连接对象
       Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
       if(dbconn==null) return null;//连接失败
       try{
          //-------查询出数据------------
          sqlString="select * from class"; 
          sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          rs=sql.executeQuery(sqlString);
          return rs;
       }catch(Exception e){
          System.out.print(e);
          return null;
       }  
   }
   /**
    * 功能：删除一个班级的信息
    * 输入参数：班级的ID号
    * 输出：返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败，
    * 返回4表输入参数class_id为0，即输入参数不正确
    */
   public int class_delete(int class_id){
       String sqlString=null;//SQL语句字符串
       ResultSet rs=null;//结果记录集
       dbconn dbconnOBject=new dbconn();//数据库连接对象
       Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
       if(dbconn==null) return 2;//连接失败
       if(class_id==0) return 4;//输入参数不正确
       //---------删除数据--------------
       sqlString="delete from class where class_id=?";
       try{
           PreparedStatement preSQLDelete=dbconn.prepareStatement(sqlString);
           preSQLDelete.setInt(1,class_id);
           preSQLDelete.executeUpdate();
           return 1;
         }catch(Exception e){
           System.out.print(e);
           return 2;
         }   
   }
   /**
    * 功能:查询出某一班级的信息
    * 输入参数:班级的ID
    * 输出:此条班级记录,如果没有记录或操作失败返回null
    */
   public ResultSet class_select_one(int class_id){
       String sqlString=null;//SQL语句字符串
       ResultSet rs=null;//结果记录集
       dbconn dbconnOBject=new dbconn();//数据库连接对象
       Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
       if(dbconn==null) return null;//连接失败
       if(class_id==0) return null;//输入参数不正确
       try{
          //-------查询出数据------------
          sqlString="select * from class where class_id=?"; 
          PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
          preSQLSelect.setInt(1,class_id);
          rs=preSQLSelect.executeQuery();
          return rs;
       }catch(Exception e){
          System.out.print(e);
          return null;
       }  
   }
   /**
    * 功能:更新班级表的一条记录
    * 输入参数:class_id为班级号，class_name为班级名称
    * 输出:返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败或数据库操作失败，
    * 返回3表记录已存在，返回4表输入参数为空或不正确;
    */
   public int class_update(int class_id,String class_name){
       String sqlString=null;//SQL语句字符串
       ResultSet rs=null;//结果记录集
       dbconn dbconnOBject=new dbconn();//数据库连接对象
       Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
       if(dbconn==null) return 2;//连接失败
       if(class_id==0||class_name.trim().length()==0) return 4;//输入参数不正确
       //--------输入参数编码转换-------
       stringUtil stringCode=new stringUtil();
       class_name=stringCode.codeToString(class_name.trim());
       //--------插入记录，先判断是否已存在------
       sqlString="select * from class where class_name=? and class_id<>"+class_id;
       try{
           PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
           preSQLSelect.setString(1,class_name);
           rs=preSQLSelect.executeQuery();
           if(rs.next()) return 3;//已存在此班级
           //--------更新记录-----------
           sqlString="update class set class_name=? where class_id=?";
           PreparedStatement preSQLUpdate=dbconn.prepareStatement(sqlString);
           preSQLUpdate.setString(1,class_name);
           preSQLUpdate.setInt(2,class_id);
           preSQLUpdate.executeUpdate();
           return 1;
       }catch(Exception e){
           System.out.print(e);
           return 2;
       }     
   }  
}
