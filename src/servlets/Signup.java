package servlets;
import com.google.gson.Gson;
import org.json.JSONObject;
import utils.API;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Signup", urlPatterns = "/signup")
public class Signup extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String nick = request.getParameter("nickname");
        String pass = request.getParameter("password");
        String email = request.getParameter("email");

        JSONObject json = new JSONObject();
        JSONObject serverResponse = new JSONObject();
        PrintWriter out = response.getWriter();

        try {
            json.put("nickname", nick);
            json.put("password", pass);
            json.put("email", email);
            //serverResponse = API.signup(json);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        String stringResp = gson.toJson(serverResponse);
        out.println(stringResp);
        out.close();
    }
}
