package service.employee;

import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Orders;
import model.Role;
import model.User;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import com.itextpdf.text.*;


import java.io.FileOutputStream;
import java.util.List;


public class EmployeeServiceImpl implements EmployeeService {
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public EmployeeServiceImpl(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    public List<User> findAll() {
        return userRepository.findAllEmployee();
    }

    @Override
    public boolean save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Role findRole(String role) {
        return rightsRolesRepository.findRoleByTitle(role);
    }

    @Override
    public boolean delete(User user) {
        return userRepository.delete(user);
    }

    @Override
    public void generatePdfReport(String employeeUsername, List<Orders> orders, int totalOrders, int totalBooksSold, double totalPrice) throws Exception{
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(employeeUsername + "_report.pdf"));

        document.open();
        document.add(new Paragraph("Employee Sales Report: " + employeeUsername));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Orders from the last month: "));

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(50);
        table.setSpacingBefore(5);
        table.setSpacingAfter(5);

        table.addCell("Title");
        table.addCell("Author");
        table.addCell("Stock");
        table.addCell("Price");

        for (Orders order : orders) {
            table.addCell(order.getTitle());
            table.addCell(order.getAuthor());
            table.addCell(String.valueOf(order.getStock()));
            table.addCell(String.format("%.2f", order.getPrice()));
        }

        document.add(table);

        document.add(new Paragraph("Total Orders: " + totalOrders));
        document.add(new Paragraph("Total Books Sold: " + totalBooksSold));
        document.add(new Paragraph("Total Price: $" + String.format("%.2f", totalPrice)));

        document.close();
    }


}
