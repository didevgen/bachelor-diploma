package ua.nure.providence.controllers.test;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.providence.daos.AccountDAO;
import ua.nure.providence.daos.CardHolderDAO;
import ua.nure.providence.daos.CategoryDAO;
import ua.nure.providence.daos.RoomDAO;
import ua.nure.providence.models.business.CardHolder;
import ua.nure.providence.models.business.Room;
import ua.nure.providence.models.hierarchy.StructuralCategory;
import ua.nure.providence.services.UserService;
import ua.nure.providence.stub.*;
import ua.nure.providence.utils.auth.LoginToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryDAO dao;

    @Autowired
    private CardHolderDAO cardHolderDAO;

    @Transactional
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<Wrapper> test() throws Exception {
        LoginToken auth = (LoginToken) SecurityContextHolder.getContext().getAuthentication();
        Wrapper university = new Gson().fromJson(sendGet("http://cist.nure.ua/ias/app/tt/P_API_FACULTIES_JSON"), Wrapper.class);
        university.getUniversity().getFaculties().forEach(faculty -> {
            StructuralCategory category = new StructuralCategory();
            category.setName(faculty.getFull_name());
            category.setAccount(auth.getAuthenticatedUser().getAccount());
            dao.insert(category);
            FacultyWrapper facItem = new Gson().fromJson(sendGet("http://cist.nure.ua/ias/app/tt/P_API_DEPARTMENTS_JSON?p_id_faculty="+faculty.getId()), FacultyWrapper.class);
            facItem.getFaculty().getDepartments().forEach(department -> {
                StructuralCategory depCategory = new StructuralCategory();
                depCategory.setName(department.getFull_name());
                depCategory.setParent(category);
                depCategory.setAccount(auth.getAuthenticatedUser().getAccount());
                dao.insert(depCategory);
                DepartmentWrapper dep = new Gson().fromJson(sendGet("http://cist.nure.ua/ias/app/tt/P_API_TEACHERS_JSON?p_id_department="+department.getId()), DepartmentWrapper.class);
                dep.getDepartment().getTeachers().forEach(teacher -> {
                    CardHolder cardHolder = new CardHolder();
                    if (!teacher.getFull_name().isEmpty()) {
                        cardHolder.setFullName(teacher.getFull_name());
                    } else {
                        cardHolder.setFullName(teacher.getShort_name());
                    }
                    cardHolder.getCategories().add(depCategory);
                    cardHolderDAO.insert(cardHolder);
                    depCategory.getCardHolders().add(cardHolder);
                });
            });
        });
        return ResponseEntity.ok(university);
    }


    private String sendGet(String url) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "windows-1251"));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
