package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet(name = "getPlaceInfoByID", urlPatterns = "getplaceinfo")
public class getPlaceInfoByID extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //int id = Integer.valueOf(request.getParameter("id"));
        PrintWriter pw = response.getWriter();
        pw.print("this is getPlace info by ID method");
    }
}
