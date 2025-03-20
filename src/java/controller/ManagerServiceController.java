package controller;

import dal.ServiceDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
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

        int categoryID = 0; // Khai báo categoryID bên ngoài switch để tránh xung đột

        try {
            switch (service.toLowerCase()) {
                case "listservice":
                    int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
                    int pageSize = 5; // Số dịch vụ mỗi trang
                    String search = request.getParameter("search") != null ? request.getParameter("search") : "";
                    categoryID = request.getParameter("categoryID") != null ? Integer.parseInt(request.getParameter("categoryID")) : 0;

                    // Gọi hàm searchServices để lấy danh sách dịch vụ với tìm kiếm và phân trang
                    List<Service> list = db.searchServices1(search, categoryID, page, pageSize);

                    // Tính tổng số trang
                    int totalServices = db.getTotalServicesForSearch(search, categoryID);
                    int totalPages = (int) Math.ceil((double) totalServices / pageSize);

                    if (page < 1) page = 1;
                    if (page > totalPages) page = totalPages;

                    // Lấy danh sách danh mục để hiển thị trong bộ lọc
                    List<ServiceCategory> categories = db.getServiceCategories();

                    request.setAttribute("list", list);
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

                case "viewdetail":
                    int viewID = Integer.parseInt(request.getParameter("serviceID"));
                    Service viewService = db.getServiceByID(viewID);
                    List<ServiceCategory> viewCategories = db.getServiceCategories();
                    request.setAttribute("service", viewService);
                    request.setAttribute("categories", viewCategories);
                    request.getRequestDispatcher("/manager/view/editservice.jsp").forward(request, response);
                    break;

                case "view":
                    int viewServiceID = Integer.parseInt(request.getParameter("serviceID"));
                    Service serviceToView = db.getServiceByID(viewServiceID);
                    request.setAttribute("service", serviceToView);
                    request.getRequestDispatcher("/manager/view/viewservice.jsp").forward(request, response);
                    break;

                case "addrequest":
                    List<ServiceCategory> addCategories = db.getServiceCategories();
                    request.setAttribute("categories", addCategories);
                    request.getRequestDispatcher("/manager/view/addservice.jsp").forward(request, response);
                    break;

                case "adddone":
                    String serviceNameAdd = request.getParameter("serviceName");
                    String serviceDetailAdd = request.getParameter("serviceDetail");
                    int categoryIDAdd = Integer.parseInt(request.getParameter("categoryID"));
                    float servicePriceAdd = Float.parseFloat(request.getParameter("servicePrice"));
                    float salePriceAdd = Float.parseFloat(request.getParameter("salePrice"));
                    String imageURLAdd = request.getParameter("imageURL");

                    ServiceCategory categoryAdd = db.getServiceCategoryByID(categoryIDAdd);
                    Service newService = new Service(0, serviceNameAdd, serviceDetailAdd, categoryAdd, 
                                                    servicePriceAdd, salePriceAdd, imageURLAdd, true);

                    db.addService(newService);
                    response.sendRedirect(request.getContextPath() + "/manager/listservice?message=Service added successfully!");
                    break;

                case "savechange":
                    int serviceID = Integer.parseInt(request.getParameter("serviceID"));
                    String serviceName = request.getParameter("serviceName");
                    String serviceDetail = request.getParameter("serviceDetail");
                    categoryID = Integer.parseInt(request.getParameter("categoryID"));
                    float servicePrice = Float.parseFloat(request.getParameter("servicePrice"));
                    float salePrice = Float.parseFloat(request.getParameter("salePrice"));
                    String imageURL = request.getParameter("imageURL");
                    String statusParam = request.getParameter("status");
                    boolean status = "true".equals(statusParam);

                    Service updatedService = db.getServiceByID(serviceID);
                    updatedService.setServiceName(serviceName);
                    updatedService.setServiceDetail(serviceDetail);
                    updatedService.setCategory(db.getServiceCategoryByID(categoryID));
                    updatedService.setServicePrice(servicePrice);
                    updatedService.setSalePrice(salePrice);
                    updatedService.setImageURL(imageURL);
                    updatedService.setStatus(status);

                    db.updateService(updatedService.getServiceName(), updatedService.getServiceDetail(), 
                                    updatedService.getCategory().getCategoryID(), updatedService.getServicePrice(), 
                                    updatedService.getSalePrice(), updatedService.getImageURL(), updatedService.getServiceID());

                    response.sendRedirect(request.getContextPath() + "/manager/listservice?message=Service updated successfully!");
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
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for managing services in the system";
    }
}