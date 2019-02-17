import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Servlet implementation class profServlet
 */
@WebServlet(name = "profServlet", urlPatterns = "/api/prof")
public class profServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public profServlet() {
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
        PrintWriter out = response.getWriter();
        	
        try {
        	JsonArray jsonArray = new JsonArray();
        	
            // Get a connection from dataSource
            Connection dbcon = dataSource.getConnection();

            // Declare our statement
            Statement statement = dbcon.createStatement();
            
            // Query database to get top 20 movies list.
            String query = "select tFname, tLname from profRatings ORDER BY tFname;";
            
            // Perform the query
            ResultSet rs = statement.executeQuery(query);
                   	
            while (rs.next()) {
            	String tFname = rs.getString("tFname");
            	String tLname = rs.getString("tLname");
            	
            	JsonObject jsonObject = new JsonObject();
            	jsonObject.addProperty("tFname", tFname);
            	jsonObject.addProperty("tLname", tLname);
                jsonArray.add(jsonObject);
            }      
            
            // write JSON string to output
            out.write(jsonArray.toString());
            // set response status to 200 (OK)
            response.setStatus(200);            
        	
            rs.close();
            statement.close();
            dbcon.close();
        } catch (Exception e) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("errorMessage", e.getMessage());
			out.write(jsonObject.toString());

			response.setStatus(500);
        }

        out.close();
    }


}
