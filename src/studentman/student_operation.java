package studentman;

import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.Statement;

        import util.stringUtil;
        import db.dbconn;
/**
 * 封装对学生表的所有操作
 * @author 邓子云
 */
        public class student_operation {
            /**
             * 功能:往学生表增加一条记录
             * 输入参数:student_name为学生姓名
             * 输出:返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败或数据库操作失败，
             * 返回3表此班级已存在，返回4表输入参数class_name为空;
             */
            public int student_add_one(String student_name,int class_id){
                dbconn dbconnOBject=new dbconn();//数据库连接对象
                String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 2;//连接失败
        //--------输入参数不正确---------
        if(student_name==null||student_name.trim().length()==0||class_id==0)
           return 4;
        //--------输入参数编码转换-------
        stringUtil stringCode=new stringUtil();
        student_name=stringCode.codeToString(student_name.trim());
        //--------插入记录，先判断是否已存在------
        sqlString="select * from student where student_name=? and class_id=?";
        try{   
           PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
           preSQLSelect.setString(1,student_name);
           preSQLSelect.setInt(2,class_id);
           rs=preSQLSelect.executeQuery();
           if(rs.next()) return 3;//已存在此学生
           sqlString="insert into student(student_name,class_id) values(?,?)";
           PreparedStatement preSQLInsert=dbconn.prepareStatement(sqlString);
           preSQLInsert.setString(1,student_name);
           preSQLInsert.setInt(2,class_id);  
           preSQLInsert.executeUpdate();
           return 1;
         }catch(Exception e){
           System.out.print(e);
           return 2;
         }             
    }
    /**
     * 功能:查询出所有的学生信息
     * 输入参数：无
     * 输出:所有学生的记录集,如果没有记录或操作失败返回null
     */
    public ResultSet student_select_all(){
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        try{
           //-------查询出数据------------
           sqlString="select distinct student.student_id as student_id," +
           		"student.student_name as student_name," +
           		"student.class_id as class_id," +
           		"class.class_name as class_name from student,class " +
           		"where class.class_id=student.class_id"; 
           sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           rs=sql.executeQuery(sqlString);
           return rs;
        }catch(Exception e){
           System.out.print(e);
           return null;
        }  
    }
    /**
     * 功能:根据条件查询出学生的信息
     * 输入参数：class_id为班级ID号，student_name为学生姓名（支持模糊查询）
     * 输出:查询出的学生的记录集,如果没有记录或操作失败返回null
     */
    public ResultSet student_select_part(int class_id,String student_name){
        String sqlString=null;//SQL语句字符串
        Statement sql=null;//SQL语句对象
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        try{
           //-------查询出数据------------
           sqlString="select distinct student.student_id as student_id," +
          	   	"student.student_name as student_name," +
          	    "student.class_id as class_id," +
          	   	"class.class_name as class_name from student,class " +
          	   	"where class.class_id=student.class_id"; 
           if(class_id!=0)
               sqlString=sqlString+" and class.class_id="+class_id+" and " +
               		"student.class_id="+class_id;
           if(student_name!=null&&student_name.trim().length()!=0)
               sqlString=sqlString+" and student.student_name like '%" +
               		""+student_name+"%'";    
           sql=dbconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           rs=sql.executeQuery(sqlString);
           return rs;
        }catch(Exception e){
           System.out.print(e);
           return null;
        }  
    }
    /**
     * 功能：删除一个学生的信息
     * 输入参数：学生的ID号
     * 输出：返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败，
     * 返回4表输入参数student_id为0，即输入参数不正确
     */
    public int student_delete(int student_id){
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 2;//连接失败
        if(student_id==0) return 4;//输入参数不正确
        //---------删除数据--------------
        sqlString="delete from student where student_id=?";
        try{
            PreparedStatement preSQLDelete=dbconn.prepareStatement(sqlString);
            preSQLDelete.setInt(1,student_id);
            preSQLDelete.executeUpdate();
            return 1;
          }catch(Exception e){
            System.out.print(e);
            return 2;
          }   
    }
    /**
     * 功能:查询出某一学生的信息
     * 输入参数:学生的ID
     * 输出:此条学生记录,如果没有记录或操作失败返回null
     */
    public ResultSet student_select_one(int student_id){
        String sqlString=null;//SQL语句字符串
        ResultSet rs=null;//结果记录集
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return null;//连接失败
        if(student_id==0) return null;//输入参数不正确
        try{
           //-------查询出数据------------
           sqlString="select * from student where student_id=?"; 
           PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
           preSQLSelect.setInt(1,student_id);
           rs=preSQLSelect.executeQuery();
           return rs;
        }catch(Exception e){
           System.out.print(e);
           return null;
        }  
    }
    /**
     * 功能:更新学生表的一条记录
     * 输入参数:student_id为学生序号，student_name为学生姓名,class_id为所属班级ID号
     * 输出:返回1表成功，返回2表数据连接参数配置不正确或连接数据库失败或数据库操作失败，
     * 返回3表记录已存在，返回4表输入参数为空或不正确;
     */
    public int student_update(int student_id,String student_name,int class_id){
        String sqlString=null;//SQL语句字符串
        dbconn dbconnOBject=new dbconn();//数据库连接对象
        ResultSet rs=null;//结果记录集
        Connection dbconn=dbconnOBject.getDBConn();//得到数据库连接
        if(dbconn==null) return 2;//连接失败
        if(student_id==0||class_id==0||student_name.trim().length()==0){//输入参数不正确
            return 4;
        }
        //--------输入参数编码转换-------
        stringUtil stringCode=new stringUtil();
        student_name=stringCode.codeToString(student_name.trim());
        //--------更新记录，先判断是否已存在------
        sqlString="select * from student where student_name=? and class_id=? and student_id<>"+student_id;
        try{   
            PreparedStatement preSQLSelect=dbconn.prepareStatement(sqlString);
            preSQLSelect.setString(1,student_name);
            preSQLSelect.setInt(2,class_id);
            rs=preSQLSelect.executeQuery();
            if(rs.next()) return 3;//已存在此学生
            //--------更新记录-----------
            sqlString="update student set student_name=?,class_id=? where student_id=?";
            PreparedStatement preSQLUpdate=dbconn.prepareStatement(sqlString);
            preSQLUpdate.setString(1,student_name);
            preSQLUpdate.setInt(2,class_id);
            preSQLUpdate.setInt(3,student_id);
            preSQLUpdate.executeUpdate();
            return 1;
        }catch(Exception e){
            System.out.print(e);
            return 2;
        }     
    }  
}
