/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.servlets;

import hieubd.account.AccountDAO;
import hieubd.account.AccountDTO;
import hieubd.account.AccountErr;
import hieubd.utils.MyContants;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author CND
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
@MultipartConfig
public class RegisterServlet extends HttpServlet {

    private final int DEFAULT_BUFFER_SIZE = 8192;
    private final String REGISTER_PAGE = "register.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String txtUsername = request.getParameter("txtUsername");
        String txtPassword = request.getParameter("txtPassword");
        String txtFullname = request.getParameter("txtFullname");
        String txtRepassword = request.getParameter("txtRe-password");
        String txtEmail = request.getParameter("txtEmail");
        String txtPhone = request.getParameter("txtPhone");
        Part filePart = request.getPart("photo");
        String photoFile = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String url = REGISTER_PAGE;
        try {
            AccountDAO dao = new AccountDAO();
            AccountErr accountErr = new AccountErr();
            boolean valid = checkInput(accountErr, txtUsername, txtPassword, txtRepassword, txtFullname, txtEmail, txtPhone, photoFile, filePart, dao);

            int lastIndex = photoFile.lastIndexOf(".");
            String extension = "";
            if (lastIndex > -1) {
                extension = photoFile.substring(lastIndex);
                if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".png")) {
                    if (valid) {
                        writeImage(request, txtUsername, filePart, extension);
                    }
                } else {
                    accountErr.setPhotoErr("Photo invalid");
                    valid = false;
                }
            } else {
                accountErr.setPhotoErr("Photo invalid");
                valid = false;
            }

            if (valid) {
                txtPassword = toHexString(getSHA(txtPassword));
                AccountDTO user = new AccountDTO(txtUsername, txtPassword, txtFullname, txtEmail, txtPhone, txtUsername + extension, MyContants.STATUS_ACTIVE, MyContants.ROLE_SUB);
                dao.addNewUser(user);
            } else {
                url = REGISTER_PAGE;
            }

            request.setAttribute("AccountErr", accountErr);
        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass() + ": " + e.getMessage());
            log(txtEmail);
        } catch (SQLException e) {
            log("Error SQLException at " + this.getClass() + ": " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            log("Error NoSuchAlgorithmException at " + this.getClass() + ": " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
            out.close();
        }
    }

    private byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    private boolean checkInput(AccountErr accountErr,
            String txtUsername,
            String txtPassword,
            String txtRepassword,
            String txtFullname,
            String txtEmail,
            String txtPhone,
            String photoFile,
            Part filePart,
            AccountDAO dao) throws NamingException, SQLException {
        boolean valid = true;

        if (txtUsername.length() < 6 || txtUsername.length() > 30) {
            accountErr.setUsernameErr("Username have to from 6 to 30 character");
            valid = false;
        }
        if (dao.checkUsernameExisted(txtUsername)) {
            accountErr.setUsernameErr("Username was existed");
            valid = false;
        }
        if (txtPassword.length() < 6) {
            accountErr.setPasswordErr("Password have to at least 6 character");
            valid = false;
        }
        if (!txtRepassword.equals(txtPassword)) {
            accountErr.setRePasswordErr("Re-Password not match with password");
            valid = false;
        }
        if(txtFullname.isEmpty()){
            accountErr.setFullnameErr("Name can not empty");
        }
        if(txtFullname.length() > 50){
            accountErr.setFullnameErr("Name can not over 50 character");
        }
        String regexEmail = "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$";
        if (txtEmail.length() < 16 || txtEmail.length() > 40) {
            accountErr.setEmailErr("Email have to from 16 - 40 character");
            if (!txtEmail.matches(regexEmail)) {
                accountErr.setEmailErr("Email invalid");
            }
            valid = false;
        }
        if (txtPhone.length() != 10) {
            accountErr.setPhoneErr("Phone invalid");
            valid = false;
        }
        return valid;
    }

    private void writeImage(HttpServletRequest request, String imageName, Part filePart, String extension) throws IOException, ServletException {
        InputStream fileContent = filePart.getInputStream();
        String path = request.getServletContext().getRealPath("/");
        FileOutputStream fos = new FileOutputStream(path + "/img/" + imageName + extension, false);

        try {
            int read;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            while ((read = fileContent.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (fileContent != null) {
                fileContent.close();
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
