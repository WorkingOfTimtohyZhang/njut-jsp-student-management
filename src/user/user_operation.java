package user;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import util.stringUtil;
import db.dbconn;
/**
 * @author 邓子云
 * 用户类
 */
public class user_operation {
    /**
     * 功能:判断自动生成的角色是否已经生成用户
     * 输入:foreign_id为教师表或学生表中的ID号,sysuser_role为用户角色
     * 输出:返回0表未生成,返回1表已生成,返回3表未知
     * 说明:只要有sysuser表中可查到记录,即表示已经生成
     */
    public int isAutoGenOK(long foreign_id,int sysuser_role){
        if(foreign_id==0||sysuser_role==0||sysuser_role==1||sysuser_role==2)
        //用户角色为1表系统管理员,为2表教务人员,这两种角色手工生成,所以不为自动生成
            return 3;
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 3;//连接失败
        sqlString="select * from sysuser where foreign_id=? and sysuser_role=?";
        try{
            PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
            preSQLSelect.setLong(1,foreign_id);
            preSQLSelect.setInt(2,sysuser_role);
            rs=preSQLSelect.executeQuery();
            if(rs.next()) return 1;
            else return 0;
          }catch(Exception e){
            System.out.print(e);
            return 3;
          }   
    }
    public int isAdminGenOK(long foreign_id,int sysuser_role){
        if(foreign_id==0||sysuser_role==0)
            return 3;
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 3;//连接失败
        sqlString="select * from sysuser where foreign_id=? and sysuser_role=?";
        try{
            PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
            preSQLSelect.setLong(1,foreign_id);
            preSQLSelect.setInt(2,sysuser_role);
            rs=preSQLSelect.executeQuery();
            if(rs.next()) return 1;
            else return 0;
          }catch(Exception e){
            System.out.print(e);
            return 3;
          }   
    }
    /**
     * 功能：生成老师用户
     */
    public String genTeacherUser(String teacher_id[]){
        String returnString=new String("");
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        ResultSet rs1=null;//另一结果记录集
        Statement sql=null;//SQL语句对象
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return returnString;//连接失败
        try{
            sqlString="select * from teacher";
            System.out.println(teacher_id.length);
            int j=0;//加长SQL语句的次数
            if(teacher_id.length!=0){//生成部分教师用户
                for(int i=0;i<teacher_id.length;i++)
                    if(teacher_id[i]!=null&&teacher_id[i].length()!=0&&!teacher_id[i].equalsIgnoreCase("null")){
                        if(j==0)
                            {sqlString=sqlString+" where teacher_id="+teacher_id[i];j++;}
                        else
                            sqlString=sqlString+" or teacher_id="+teacher_id[i];
                    }
                
            }
            sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs=sql.executeQuery(sqlString);
            while(rs.next()){
                sqlString="select * from sysuser where foreign_id=" +
                		rs.getLong("teacher_id")+" and sysuser_role=3";
                sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs1=sql.executeQuery(sqlString);
                if(!rs1.next()){//不存在插入，存在则不做事
                    sqlString="insert into sysuser(sysuser_name,sysuser_password,sysuser_role" +
                    		",foreign_id) values(?,?,3,?)";
                    PreparedStatement preSQLUpdate=dbconn.prepareStatement(sqlString);
                    preSQLUpdate.setString(1,rs.getString("teacher_name"));
                    preSQLUpdate.setString(2,"111111");
                    preSQLUpdate.setLong(3,rs.getLong("teacher_id"));
                    preSQLUpdate.executeUpdate();
                    returnString=returnString+"生成教师"+rs.getString("teacher_name")+"用户成功！<BR>";
                }else
                    returnString=returnString+"教师"+rs.getString("teacher_name")+"用户已经生成，不必再生成！<BR>";
                rs1.close();
            }
        }catch(Exception e){
            System.out.print(e);
        }
        return returnString;
    }
    
    /**
     * 功能：生成学生用户
     */
    public String genStudentUser(String student_id[]){
        String returnString=new String("");
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        ResultSet rs1=null;//另一结果记录集
        Statement sql=null;//SQL语句对象
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return returnString;//连接失败
        try{
            sqlString="select * from student";
            System.out.println(student_id.length);
            int j=0;//加长SQL语句的次数
            if(student_id.length!=0){//生成部分教师用户
                for(int i=0;i<student_id.length;i++)
                    if(student_id[i]!=null&&student_id[i].length()!=0&&!student_id[i].equalsIgnoreCase("null")){
                        if(j==0)
                            {sqlString=sqlString+" where student_id="+student_id[i];j++;}
                        else
                            sqlString=sqlString+" or student_id="+student_id[i];
                    }
                
            }
            sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs=sql.executeQuery(sqlString);
            while(rs.next()){
                sqlString="select * from sysuser where foreign_id=" +
                		rs.getLong("student_id")+" and sysuser_role=4";
                sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs1=sql.executeQuery(sqlString);
                if(!rs1.next()){//不存在插入，存在则不做事
                    sqlString="insert into sysuser(sysuser_name,sysuser_password,sysuser_role" +
                    		",foreign_id) values(?,?,4,?)";
                    PreparedStatement preSQLUpdate=dbconn.prepareStatement(sqlString);
                    preSQLUpdate.setString(1,rs.getString("student_name"));
                    preSQLUpdate.setString(2,"111111");
                    preSQLUpdate.setLong(3,rs.getLong("student_id"));
                    preSQLUpdate.executeUpdate();
                    returnString=returnString+"生成学生"+rs.getString("student_name")+"用户成功！<BR>";
                }else
                    returnString=returnString+"学生"+rs.getString("student_name")+"用户已经生成，不必再生成！<BR>";
                rs1.close();
            }
        }catch(Exception e){
            System.out.print(e);
        }
        return returnString;
    }
    /**
     * 得到管理用户记录集,系统管理员和教务管理员是管理用户
     */
    public ResultSet getAdminUsers(){
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        try{
           //-------查询出数据------------
           sqlString="select * from sysuser where sysuser_role=1 or sysuser_role=2"; 
           sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           rs=sql.executeQuery(sqlString);
           return rs;
        }catch(Exception e){
           System.out.print(e);
           return null;
        } 
    }
    /**
     * 功能：生成管理用户
     * 输入:参数teacher_id为生成管理用户的教师ID,只有教师能成为管理用户,role_id为分配的角色
     */
    public String genAdminUser(int teacher_id,int role_id){
        String returnString=new String("");
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        ResultSet rs1=null;//另一结果记录集
        Statement sql=null;//SQL语句对象
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null||teacher_id==0||role_id==0) return returnString;//连接失败
        try{
            sqlString="select * from teacher where teacher_id="+teacher_id;
            sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs=sql.executeQuery(sqlString);
            while(rs.next()){
                sqlString="select * from sysuser where foreign_id=" +
                		rs.getLong("teacher_id")+" and sysuser_role="+role_id;
                sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs1=sql.executeQuery(sqlString);
                if(!rs1.next()){//不存在插入，存在则不做事
                    sqlString="insert into sysuser(sysuser_name,sysuser_password,sysuser_role" +
                    		",foreign_id) values(?,?,"+role_id+",?)";
                    System.out.println(sqlString);
                    PreparedStatement preSQLUpdate=dbconn.prepareStatement(sqlString);
                    preSQLUpdate.setString(1,rs.getString("teacher_name"));
                    preSQLUpdate.setString(2,"111111");
                    preSQLUpdate.setLong(3,rs.getLong("teacher_id"));
                    preSQLUpdate.executeUpdate();
                    returnString=returnString+"生成管理用户"+rs.getString("teacher_name")+"用户成功！<BR>";
                }else
                    returnString=returnString+rs.getString("teacher_name")+"管理用户已经生成，不必再生成！<BR>";
                rs1.close();
            }
        }catch(Exception e){
            System.out.print(e);
        }
        return returnString;
    }
    /**
     * 得到一条用户记录
     */
    public ResultSet getUserByPrimKey(int sysuser_id){
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null||sysuser_id==0) return null;//连接失败
        try{
           //-------查询出数据------------
           sqlString="select * from sysuser where sysuser_id="+sysuser_id; 
           sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           rs=sql.executeQuery(sqlString);
           return rs;
        }catch(Exception e){
           System.out.print(e);
           return null;
        } 
    }
    /**
     * 根据用户表的外键和角色得到用户ID号
     */
    public long getUserId(int foreign_id,int sysuser_role){
        if(foreign_id==0||sysuser_role==0) return 0;
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 0;//连接失败
        try{
           //-------查询出数据------------
           sqlString="select * from sysuser where foreign_id="+foreign_id+
           			  " and sysuser_role="+sysuser_role; 
           //System.out.println(sqlString);
           sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           rs=sql.executeQuery(sqlString);
           if(rs.next())
               return rs.getLong("sysuser_id");
           else
               return 0;
        }catch(Exception e){
           e.printStackTrace();
           return 0;
        }            
    }
    /**
     * 更新系统用户信息
     */
    public int update_sysuser(int user_id,String user_name,String user_password,int user_role){
        if(user_role==0||user_id==0) return 0;
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        //--------输入参数编码转换-------
        stringUtil stringCode=new stringUtil();
        user_name=stringCode.codeToString(user_name.trim());
        user_password=stringCode.codeToString(user_password.trim());
        if(dbconn==null) return 0;//连接失败
        try{
           //-------查询出数据------------
           sqlString="update sysuser set sysuser_name='" + user_name +
 		  			"',sysuser_password='"+user_password+"',sysuser_role="+
 		  			user_role+" where sysuser_id="+user_id;
           //System.out.println(sqlString);
           sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           return sql.executeUpdate(sqlString);         
        }catch(Exception e){
           e.printStackTrace();
           return 0;
        }
    }
    /**
     * 删除一条用户记录
     */
    public int deleteUserByPrimKey(int sysuser_id){
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null||sysuser_id==0) return 0;//连接失败
        try{
           //-------查询出数据------------
           sqlString="delete from sysuser where sysuser_id="+sysuser_id; 
           sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           return sql.executeUpdate(sqlString);
        }catch(Exception e){
           System.out.print(e);
           return 0;
        } 
    }
    /**
     * 得到一条用户记录
     */
    public ResultSet getUserOne(String sysuser_name,String sysuser_password,int sysuser_role){
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        //--------输入参数编码转换-------
        stringUtil stringCode=new stringUtil();
        sysuser_name=stringCode.codeToString(sysuser_name.trim());
        sysuser_password=stringCode.codeToString(sysuser_password.trim());
        try{
           //-------查询出数据------------
           sqlString="select * from sysuser where sysuser_name=? and sysuser_password=? and sysuser_role=?"; 
           PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
           preSQLSelect.setString(1,sysuser_name);
           preSQLSelect.setString(2,sysuser_password);
           preSQLSelect.setInt(3,sysuser_role);
           rs=preSQLSelect.executeQuery();
           System.out.println("select * from sysuser where sysuser_name='"+sysuser_name+"' and sysuser_password='"+ sysuser_password+"' and sysuser_role="+sysuser_role);
           return rs;
        }catch(Exception e){
           System.out.print(e);
           return null;
        } 
    }
}
