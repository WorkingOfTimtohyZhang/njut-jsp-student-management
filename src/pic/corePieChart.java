package pic;

import classman.class_operation;
import core.core_operation;
import lessionman.lession_operation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.DefaultPieDataset;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

public class corePieChart extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //-------接收lession_id和class_id参数------------
        int lession_id = 0;
        int class_id = 0;
        ResultSet rs = null;
        try {
            try {
                lession_id = Integer.parseInt(request.getParameter("lession_id"));
                class_id = Integer.parseInt(request.getParameter("class_id"));
            } catch (Exception e) {
            }
            core_operation coreOp = new core_operation();
            rs = coreOp.getClassLessionCore(class_id, lession_id);
            //------生成图形中要显示的数据集------
            String coreName[] = new String[]{"", "", "", "", ""};
            int coreValueCount[] = {0, 0, 0, 0, 0};
            getDataFen(rs, coreName, coreValueCount);
            DefaultPieDataset pieDataset = new DefaultPieDataset();
            for (int i = 0; i < coreName.length; i++)
                pieDataset.setValue(coreName[i], coreValueCount[i]);
            String titleString = getTilteString(class_id, lession_id);
            //------创建图形------
            JFreeChart chart = ChartFactory.createPieChart(titleString, pieDataset, true, true, false);

            /**
             * important 源码在这里必须设置中文字体，不然会出现框框
             */
            Font font=new Font("宋体", Font.BOLD, 15);
            chart.getTitle().setFont(new Font("隶书", Font.ITALIC, 15));//设置标题
            chart.getLegend().setItemFont(font);
            PiePlot plot=(PiePlot)chart.getPlot();
            plot.setLabelFont(font);
            LegendTitle legend = chart.getLegend(0);
            legend.setItemFont(font);// 为图例说明设置字体


            //------显示图形------
            OutputStream out = response.getOutputStream();
            response.setContentType("image/jpg");
            ChartUtilities.writeChartAsJPEG(out, chart, 500, 300);
        } catch (Exception e) {
            System.out.println("生成图形失败.");
            System.out.println(e.getMessage());
            System.out.println(e.toString());
            System.out.println(e.getStackTrace());
        }
    }

    //---0:60分以下--1:60-70--2:70-80--3:80-90--4:90-100
    public void getDataFen(ResultSet rs, String[] coreName, int[] coreValueCount) {
        try {
            rs.beforeFirst();
            coreName[0] = "60分以下";
            coreName[1] = "60-69分";
            coreName[2] = "70-79分";
            coreName[3] = "80-89分";
            coreName[4] = "90-100分";
            float core = 0;
            while (rs.next()) {
                core = rs.getFloat("core");
                if (core < 60) coreValueCount[0]++;
                if (core >= 60 && core < 70) coreValueCount[1]++;
                if (core >= 70 && core < 80) coreValueCount[2]++;
                if (core >= 80 && core < 90) coreValueCount[3]++;
                if (core >= 90 && core <= 100) coreValueCount[4]++;
            }
            rs.beforeFirst();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //--得到饼图标题字符串-----------
    public String getTilteString(int class_id, int lession_id) {
        String returnString = new String("");
        class_operation classOp = new class_operation();
        lession_operation lessionOp = new lession_operation();
        ResultSet rs = null;
        try {
            if (class_id == 0 || lession_id == 0) {
                if (!(lession_id == 0 && class_id == 0)) {
                    if (class_id == 0) {
                        rs = lessionOp.lession_select_one(lession_id);
                        if (rs.next())
                            returnString = "所有班级的" + rs.getString("lession_name") + "的成绩统计饼状图";
                    }
                    if (lession_id == 0) {
                        rs = classOp.class_select_one(class_id);
                        if (rs.next())
                            returnString = rs.getString("class_name") + "班所有课程的成绩统计饼状图";
                    }
                } else
                    returnString = "所有学生所有课程成绩统计饼状图";
            } else {
                rs = classOp.class_select_one(class_id);
                if (rs.next())
                    returnString = rs.getString("class_name") + "班";
                rs = lessionOp.lession_select_one(lession_id);
                if (rs.next())
                    returnString = returnString + rs.getString("lession_name") + "的成绩统计饼状图";
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return returnString;
    }
}
