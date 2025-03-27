package controller;

import dal.ServiceDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.ImgDetail;
import model.Service;
import model.ServiceCategory;

public class ManagerServiceController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Servlet ManagerServiceController</title></head>");
            out.println("<body><h1>Servlet ManagerServiceController at " + request.getContextPath() + "</h1></body></html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServiceDBContext db = new ServiceDBContext();
        String service = request.getParameter("service");

        if (service == null) {
            service = "listservice";
        }

        int categoryID = 0;

        try {
            switch (service.toLowerCase()) {
                case "listservice":
                    int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
                    int pageSize = 5;
                    String search = request.getParameter("search") != null ? request.getParameter("search") : "";
                    categoryID = request.getParameter("categoryID") != null ? Integer.parseInt(request.getParameter("categoryID")) : 0;

                    List<Service> list = db.searchServices1(search, categoryID, page, pageSize);
                    List<ImgDetail> listImg = new ArrayList<>();
                    for (Service s : list) {
                        List<ImgDetail> imgs = db.listImgByServiceId(s.getServiceID()); // Giả sử Service có getServiceID()
                        listImg.addAll(imgs); // Thêm tất cả ImgDetail của Service này vào listImg
                    }
                    int totalServices = db.getTotalServicesForSearch(search, categoryID);
                    int totalPages = (int) Math.ceil((double) totalServices / pageSize);

                    if (page < 1) {
                        page = 1;
                    }
                    if (page > totalPages) {
                        page = totalPages;
                    }

                    List<ServiceCategory> categories = db.getServiceCategories();

                    request.setAttribute("list", list);
                    request.setAttribute("listImg", listImg);
                    request.setAttribute("currentPage", page);
                    request.setAttribute("totalPages", totalPages);
                    request.setAttribute("search", search);
                    request.setAttribute("categoryID", categoryID);
                    request.setAttribute("categories", categories);
                    request.getRequestDispatcher("/manager/view/managerlistservice.jsp").forward(request, response);
                    break;

                case "editstatus":
                    int serviceIDStatus = Integer.parseInt(request.getParameter("serviceID"));
                    boolean newStatus = Boolean.parseBoolean(request.getParameter("editStatus"));
                    db.updateServiceStatus(serviceIDStatus, newStatus);
                    response.sendRedirect(request.getContextPath() + "/manager/listservice");
                    break;

                case "searchbyid":
                    int searchID = Integer.parseInt(request.getParameter("searchID"));
                    List<Service> searchResult = db.getListServiceByID(searchID);
                    request.setAttribute("searchID", searchID);
                    request.setAttribute("list", searchResult);
                    request.setAttribute("currentPage", 1);
                    request.setAttribute("totalPages", 1);
                    request.getRequestDispatcher("/manager/view/managerlistservice.jsp").forward(request, response);
                    break;

                case "viewDetail":
                    int viewID = Integer.parseInt(request.getParameter("serviceID"));
                    Service viewService = db.getServiceByID(viewID);
                    List<ImgDetail> detailImages = db.getDetailImages(viewID);
                    List<ServiceCategory> viewCategories = db.getServiceCategories();
                    request.setAttribute("service", viewService);
                    request.setAttribute("detailImages", detailImages);
                    request.setAttribute("categories", viewCategories);
                    request.getRequestDispatcher("/manager/view/editservice.jsp").forward(request, response);
                    break;
                case "view":
                    int viewServiceID = Integer.parseInt(request.getParameter("serviceID"));
                    Service serviceToView = db.getServiceByID(viewServiceID);
                    List<ImgDetail> viewDetailImages = db.getDetailImages(viewServiceID);
                    request.setAttribute("service", serviceToView);
                    request.setAttribute("detailImages", viewDetailImages);
                    request.getRequestDispatcher("/manager/view/viewservice.jsp").forward(request, response);
                    break;

                case "addrequest":
                    List<ServiceCategory> addCategories = db.getServiceCategories();
                    request.setAttribute("categories", addCategories);
                    request.getRequestDispatcher("/manager/view/addservice.jsp").forward(request, response);
                    break;

                case "delete":
                    int deleteServiceID = Integer.parseInt(request.getParameter("serviceID"));
                    db.deleteService(deleteServiceID);
                    response.sendRedirect(request.getContextPath() + "/manager/listservice?message=Service deleted successfully!");
                    break;

                default:
                    response.getWriter().println("Invalid service request!");
                    break;
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid input format: " + e.getMessage());
        } catch (Exception e) {
            response.getWriter().println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServiceDBContext db = new ServiceDBContext();
        String service = request.getParameter("service");

        if (service == null) {
            response.getWriter().println("Invalid service request!");
            return;
        }

        try {
            switch (service.toLowerCase()) {
                case "adddone":
                    String serviceNameAdd = request.getParameter("serviceName");
                    String detailTextAdd = request.getParameter("detailText");
                    int categoryIDAdd = Integer.parseInt(request.getParameter("categoryID"));
                    float servicePriceAdd = Float.parseFloat(request.getParameter("servicePrice"));
                    float salePriceAdd = request.getParameter("salePrice") != null && !request.getParameter("salePrice").isEmpty()
                            ? Float.parseFloat(request.getParameter("salePrice")) : 0;
                    String imageURLAdd = request.getParameter("imageURL");

                    List<String> detailImagesAdd = new ArrayList<>();
                    int i= 0;
                    while (request.getParameter("detailImage_" + i) != null && !request.getParameter("detailImage_" + i).isEmpty()) {
                        detailImagesAdd.add(request.getParameter("detailImage_" + i));
                        i++;
                    }
                    
                    ServiceCategory categoryAdd = db.getServiceCategoryByID(categoryIDAdd);
                    
                    Service newService = new Service(0, serviceNameAdd, detailTextAdd, categoryAdd,
                            servicePriceAdd, salePriceAdd, imageURLAdd, true);
                    db.addService(newService, detailImagesAdd);
                    response.sendRedirect(request.getContextPath() + "/manager/listservice?message=Service added successfully!");
                    break;

                case "savechange":
                    int serviceID = Integer.parseInt(request.getParameter("serviceID"));
                    String serviceName = request.getParameter("serviceName");
                    String detailText = request.getParameter("detailText");
                    int categoryID = Integer.parseInt(request.getParameter("categoryID"));
                    float servicePrice = Float.parseFloat(request.getParameter("servicePrice"));
                    float salePrice = request.getParameter("salePrice") != null && !request.getParameter("salePrice").isEmpty()
                            ? Float.parseFloat(request.getParameter("salePrice")) : 0;
                    String imageURL = request.getParameter("imageURL");

                    List<String> detailImagesUpdate = new ArrayList<>();
                    i = 0;
                    while (request.getParameter("detailImage_" + i) != null && !request.getParameter("detailImage_" + i).isEmpty()) {
                        detailImagesUpdate.add(request.getParameter("detailImage_" + i));
                        i++;
                    }

                    db.updateService(serviceName, detailText, categoryID, servicePrice, salePrice, imageURL, serviceID, detailImagesUpdate);
                    response.sendRedirect(request.getContextPath() + "/manager/listservice?message=Service updated successfully!");
                    break;

                default:
                    response.getWriter().println("Invalid service request!");
                    break;
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid input format: " + e.getMessage());
        } catch (Exception e) {
            response.getWriter().println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for managing services in the system";
    }
}
