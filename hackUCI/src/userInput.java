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

/**
 * Servlet implementation class profServlet
 */
@WebServlet(name = "userInput", urlPatterns = "/api/userInput")
public class userInput extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userInput() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    // Create a dataSource which registered in web.xml
    @Resource(name = "jdbc/icsteacher")
    private DataSource dataSource;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json"); // Response mime type
        
        
        System.out.println("server");
        // Output stream to STDOUT
    	String firstName = request.getParameter("fn");
        String lastName = request.getParameter("ln");
        String courseName = request.getParameter("cn");
        String professorName = request.getParameter("pf");
        String gw = request.getParameter("gw");
        String currentGrade = request.getParameter("cg");
        String finalTime = request.getParameter("fd");
        String pfstyle = request.getParameter("pfStyle");
        String topic = request.getParameter("topic");
        String gradeWant2 = request.getParameter("gradeWant");        
        
        System.out.println(firstName);
        System.out.println(lastName);
    	System.out.println(courseName);
    	System.out.println(professorName);
    	System.out.println(gw);
    	System.out.println(currentGrade);
    	System.out.println(finalTime);
    	System.out.println(pfstyle);
    	System.out.println(topic);
    	System.out.println(gradeWant2);
    	
    	HttpSession session = request.getSession();
        
        
        try { 	
        	JsonObject responseJsonObject = new JsonObject();
        	// check preference rule
        	if(Integer.parseInt(pfstyle) + Integer.parseInt(topic) + Integer.parseInt(gradeWant2) != 6 || Integer.parseInt(pfstyle) == Integer.parseInt(topic)) {
                responseJsonObject.addProperty("status", "fail");
                responseJsonObject.addProperty("message", "Priority Order Fail");
                response.getWriter().write(responseJsonObject.toString());
                return;
            }	
        
            // Get a connection from dataSource
            Connection dbcon = dataSource.getConnection();
            // Declare our statement
            
            
            CallableStatement cStmt = dbcon.prepareCall("{call insertUserInput(?,?,?,?,?,?,?,?,?)}");
        	
        	// set param
        	cStmt.setString(1, firstName);
        	cStmt.setString(2, lastName);
        	cStmt.setString(3, professorName);
        	cStmt.setString(4, gw);
        	cStmt.setString(5, currentGrade);
        	cStmt.setString(6, finalTime);
        	cStmt.setString(7, pfstyle);
        	cStmt.setString(8, topic);
        	cStmt.setString(9, gradeWant2);
        	
        	System.out.println(cStmt);

        	// execute callablestatement
        	boolean hadResults = cStmt.execute();
        	
        	
            cStmt.close();
            dbcon.close();
            
            responseJsonObject.addProperty("status", "success");
            responseJsonObject.addProperty("message", "insert success");
            
            response.getWriter().write(responseJsonObject.toString());
            
            
            // set response status to 200 (OK)
            response.setStatus(200);            
        } catch (Exception e) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("errorMessage", e.getMessage());

			response.setStatus(500);
        }

    }


}
