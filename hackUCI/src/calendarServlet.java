import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.*; 
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javafx.util.Pair; 

/**
 * Servlet implementation class profServlet
 */
@WebServlet(name = "calendarServlet", urlPatterns = "/api/calendar")
public class calendarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public calendarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    // Create a dataSource which registered in web.xml
    @Resource(name = "jdbc/icsteacher")
    private DataSource dataSource;
  
    // raw date data of start date, start time and end time
    private String[] sd = new String[10];
    private String[] st = new String[20];
    private String[] et = new String[20];
    
    // time range 
    private int [] sRange = new int[20];
    private ArrayList<Integer> order = new ArrayList<Integer>();
    private ArrayList<Date> finalDate = new ArrayList<Date>(); 
    private ArrayList<Double> importList = new ArrayList<Double>();
    private ArrayList<String> profList = new ArrayList<String>();
    private int totalStudyTime;
    
    
    private String processData() {
    	// sort	
    	ArrayList<Pair<Long, Integer>> orderRaw = new ArrayList<Pair<Long, Integer>>();
    	for(int i = 0; i < finalDate.size(); ++i) {
    		orderRaw.add(new Pair <Long, Integer> (finalDate.get(i).getTime(), i));
    	}
    	
    	for(int i = 0; i < orderRaw.size(); ++i) {
    		for(int j = i-1; j >= 0; --j) {
    			if(orderRaw.get(j+1).getKey() < orderRaw.get(j).getKey()) {
    				Collections.swap(orderRaw, j+1, j);
    			}
    			else break;
    		}
    	}
    	for(int i = 0; i < orderRaw.size(); ++i) order.add(orderRaw.get(i).getValue());
    	
    	// total
    	
    	// sRange
    	for(int i = 0; i < 20; ++i){
    		if(st[i] == null || et[i] == null) {
    			sRange[i] = 0;
    			continue;
    		}
    		int hr = Integer.parseInt(st[i].substring(0, st[i].indexOf(':')));
    		int min = Integer.parseInt(st[i].substring(st[i].indexOf(':')+1));
    		int hr2 = Integer.parseInt(et[i].substring(0, et[i].indexOf(':')));
    		int min2 = Integer.parseInt(et[i].substring(et[i].indexOf(':')+1));
   		
    		int range = (hr2-hr)*60 + (min2-min);
    		sRange[i] = range;
    	}
    	String wholeStr = "";
    	
    	// schedule
    	for(int i = 0; i < 10; ++i) {
    		//System.out.println(i);
    		boolean exceed = false;
    		int r = sRange[i];
    		String echoStr = "At " + sd[i] + ", ";
    		if(r <= 0) {
    			exceed = true;
    			r = sRange[i+10];
    		}
    		if(r <= 0 && exceed == true) continue;
    		
    		
    		int p = 0;
    		for(int j = 0; j < order.size(); ++j) {
    			int tmpR = importList.get(order.get(j)).intValue();
    			if(p+tmpR > r && exceed == false) {
    				System.out.println("2 slots");
    				echoStr += "study Prof." + profList.get(order.get(j)) + "'s course from " +  st[i] + "+" + Integer.toString(p) + " to " + st[i] + "+" + Integer.toString(r) + ", ";
    				tmpR = tmpR - (r-p);
    				r = sRange[i+10];
    				p = 0;
    				exceed = true;
    			}
    			if(p+tmpR > r && exceed == true) {
    				echoStr += "Unable to finish " + profList.get(order.get(j)) + "'s course, ";
    				continue;
    			}
    			 
        		System.out.println(p+tmpR);
        		if(exceed == false) echoStr += "study Prof." + profList.get(order.get(j)) + "'s course from " +  st[i] + "+" + Integer.toString(p) + " to " + st[i] + "+" + Integer.toString(p+tmpR) + ", ";
        		else echoStr += "study Prof." + profList.get(order.get(j)) + "'s course from " +  st[i+10] + "+" + Integer.toString(p) + " to " + st[i+10] + "+" + Integer.toString(p+tmpR) + ", ";
        		p += tmpR;   				
    		}
    		
    		System.out.println(echoStr);
    		wholeStr += echoStr;
    	}
    	return wholeStr;
    	// 
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json"); // Response mime type
        
      
        
        PrintWriter out = response.getWriter();

        // Output stream to STDOUT
    	String firstName = request.getParameter("fn");
        String lastName = request.getParameter("ln");
        
        for(int i = 0; i < 10; ++i) {
        	if(request.getParameter("sd" + Integer.toString(i+1)).length() > 0) sd[i] = request.getParameter("sd" + Integer.toString(i+1));
        	if(request.getParameter("st" + Integer.toString(i+1)).length() > 0) st[i] = request.getParameter("st" + Integer.toString(i+1));
        	if(request.getParameter("st" + Integer.toString(i+11)).length() > 0) st[i+10] = request.getParameter("st" + Integer.toString(i+11));
        	if(request.getParameter("et" + Integer.toString(i+1)).length() > 0) et[i] = request.getParameter("et" + Integer.toString(i+1));
        	if(request.getParameter("et" + Integer.toString(i+11)).length() > 0) et[i+10] = request.getParameter("et" + Integer.toString(i+11));    	
        	
//        	st[i+10] = request.getParameter("st" + Integer.toString(i+11));
//        	et[i] = request.getParameter("et" + Integer.toString(i+1));
//        	et[i+10] = request.getParameter("et" + Integer.toString(i+11));
        }      

        try { 	
        	JsonArray jsonArray = new JsonArray();
        	
            // Get a connection from dataSource
            Connection dbcon = dataSource.getConnection();

            dbcon.setAutoCommit(false);

            String query = "select * from student_in_course sc, students s, profRatings pf  WHERE sc.profId = pf.id and sc.studentId = s.id and s.firstName = ? and s.lastName = ?";
            PreparedStatement statement = dbcon.prepareStatement(query);
            
            
            statement.setString(1, firstName);
            statement.setString(2, lastName);

    		
    		
    		ResultSet rs = statement.executeQuery();
    		
    		dbcon.commit();
    		
    		 
//    		
            while (rs.next()) {
            	int gradeWant = rs.getInt("gradeWant");
            	int currentGrade = rs.getInt("currentGrade");
            	
            	String tFname = rs.getString("tFname");
            	String tLname = rs.getString("tLname");
            	 
            	int profStyle = rs.getInt("profStyle");
            	int topic = rs.getInt("topic");
            	int gradeW = rs.getInt("topic");
            	
            	
            	// how hard we should study 
            	float overall_Rate = rs.getFloat("overall_Rate");
            	float difficulty = rs.getFloat("difficulty");
            	
            	String t = rs.getString("finalTime");           
            	t = t.substring(0, t.indexOf('.'));
            	
            	String format = "yyyy-MM-dd HH:mm:ss";
            	SimpleDateFormat sdf = new SimpleDateFormat(format);
            	finalDate.add(sdf.parse(t));
    			System.out.println(finalDate.get(finalDate.size() - 1));         	
            	
            	float x = (5 - difficulty)/5;
            	float y = overall_Rate/5;
            	float z = 0;
            	if(gradeWant - currentGrade < 0) z = (2*gradeWant - currentGrade)/gradeWant;
            	else z = 1;
            	
            	double xW = ((profStyle == 1) ? 0.5 : ((profStyle == 2) ? 0.33 : 0.166));
            	double yW = ((topic == 1) ? 0.5 : ((topic == 2) ? 0.33 : 0.166));
            	double zW = ((gradeW == 1) ? 0.5 : ((gradeW == 2) ? 0.33 : 0.166));
            	
            	double importF = x*xW + y*yW + z*zW;
            	totalStudyTime += (int)importF;
            	importList.add(importF);
            	profList.add(tFname + " " + tLname);
            	

    		}
            
            for(int i = 0; i < importList.size(); ++i) {
            	double tmp = importList.get(i);
            	if(importList.size() > 1) tmp = tmp*75 +30;
            	else tmp = tmp*60 +30;
            	tmp = Math.round(tmp/5) * 5;
            	importList.set(i, tmp);
            	System.out.println(tmp);
            }
            
            
            String wholeStr = processData();
            importList.clear();
            order.clear();
            finalDate.clear();
            profList.clear();
            
            rs.close();
            statement.close();
            dbcon.close();     
            // write JSON string to output
            JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("output", wholeStr);
            out.write(jsonObject.toString());
            // set response status to 200 (OK)
            response.setStatus(200);  
        } catch (Exception e) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("errorMessage", e.getMessage());

			response.setStatus(500);
        }
        out.close();
    }


}
