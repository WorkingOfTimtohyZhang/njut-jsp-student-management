
package util;
import java.sql.ResultSet;
public class rsFenYe {
    ResultSet rs=null;//要分页处理的记录集
    int currentPage=1;//当前页码
    int pageSize=10;//每页的记录条数，默认为10
    /**
     * 得到分页字串，并把记录指针移动到当前页的第一条记录的前面位置
     * 输入参数：返回的JSP页面,refName数组为超链接中继currentPage之后的？参数名，refValue为对应的值
     * 输出参数：字符串”共2页，当前第1页  下一页 末页”
     */
    public String earn_fenyi_string(String returnJSP,String[] refName,String[] refValue){
        String returnString=new String("");
        if(rs==null) return(returnString);
        int pageCount=0;//总页数
        int rowCount=0;//rs的总记录条数
        if(pageSize<=0) //每页记录条数不正确
            return(returnString);
        try{
            //------得到总记录条数------
            rs.last();
            rowCount=rs.getRow();
            rs.beforeFirst();
            //------记录指针定位--------
            int RecordPosition=(currentPage-1)*pageSize;
            if(RecordPosition==0) rs.beforeFirst();
            else rs.absolute(RecordPosition);
        }catch(Exception e){
            System.out.println(e);
            return(returnString);
        }
        //---------得到总页数----------
        if(rowCount%pageSize==0) pageCount=rowCount/pageSize;
        else pageCount=rowCount/pageSize+1;
        //---------得到返回字符串--------
        returnString="共"+pageCount+"页，当前第"+currentPage+"页&nbsp;&nbsp;";
        if(currentPage!=1&&pageCount!=0){//不是首页且总页数不为0
            returnString=returnString+"<a href='"+returnJSP+"?currentPage=1";
            for(int i=0;i<refName.length;i++)
                returnString=returnString+"&"+refName[i]+"="+refValue[i];
            returnString=returnString+"'>首页</a>&nbsp;";
            returnString=returnString+"<a href='"+returnJSP+"?currentPage="+(currentPage-1);
            for(int i=0;i<refName.length;i++)
                returnString=returnString+"&"+refName[i]+"="+refValue[i];
            returnString=returnString+"'>上一页</a>&nbsp;";
        }
        if(currentPage!=pageCount&&pageCount!=0){//不是末页且总页数不为0
            returnString=returnString+"<a href='"+returnJSP+"?currentPage="+(currentPage+1);
            for(int i=0;i<refName.length;i++)
                returnString=returnString+"&"+refName[i]+"="+refValue[i];
            returnString=returnString+"'>下一页</a>&nbsp;";               
            returnString=returnString+"<a href='"+returnJSP+"?currentPage="+pageCount;
            for(int i=0;i<refName.length;i++)
                returnString=returnString+"&"+refName[i]+"="+refValue[i];
            returnString=returnString+"'>末页</a>&nbsp;";               
        }   
        return returnString;
    }  
    public ResultSet getRs() {
        return rs;
    }
    public void setRs(ResultSet rs) {
        this.rs = rs;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
