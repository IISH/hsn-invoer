package org.iish.hsn.invoer.service;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.iish.hsn.invoer.domain.invoer.Invoer;
import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.mil.Career;
import org.iish.hsn.invoer.domain.invoer.mil.Milition;
import org.iish.hsn.invoer.domain.invoer.mil.Verdict;
import org.iish.hsn.invoer.repository.invoer.mil.CareerRepository;
import org.iish.hsn.invoer.repository.invoer.mil.MilitionRepository;
import org.iish.hsn.invoer.repository.invoer.mil.VerdictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

/**
 * Service for performing administrative actions
 */
@Service
public class AdminService {
    @Autowired private MilitionRepository militionRepository;
    @Autowired private VerdictRepository  verdictRepository;
    @Autowired private CareerRepository   careerRepository;

    private static final List<Field> MILITION_FIELDS =
            FieldUtils.getFieldsListWithAnnotation(Milition.class, Column.class);
    private static final List<Field> VERDICT_FIELDS =
            FieldUtils.getFieldsListWithAnnotation(Verdict.class, Column.class);
    private static final List<Field> CAREER_FIELDS =
            FieldUtils.getFieldsListWithAnnotation(Career.class, Column.class);

    static {
        try {
            Class.forName("org.h2.Driver");
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadMilitionDb(MultipartFile dbFile) {
        try {
            Path filePath = Paths.get(System.getProperty("java.io.tmpdir"), dbFile.getName() + ".mv.db");
            dbFile.transferTo(filePath.toFile());

            String path = filePath.toString();
            path = path.substring(0, path.length() - 6);

            Timestamp timestamp = new Timestamp(new java.util.Date().getTime());

            Connection connection = DriverManager.getConnection("jdbc:h2:" + path + ";MODE=MySQL");
            insertMilitions(connection, timestamp);
            insertVerdicts(connection, timestamp);
            insertCareers(connection, timestamp);

            connection.close();
            Files.delete(filePath);
        }
        catch (IOException | SQLException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void insertMilitions(Connection connection, Timestamp timestamp) throws SQLException, IllegalAccessException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from m1");
        while (rs.next()) {
            Milition milition = new Milition();
            milition.setImportDate(timestamp);

            updateProperties(rs, milition, MILITION_FIELDS);
            militionRepository.save(milition);
        }
    }

    private void insertVerdicts(Connection connection, Timestamp timestamp) throws SQLException, IllegalAccessException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from m2");
        while (rs.next()) {
            Verdict verdict = new Verdict();
            verdict.setImportDate(timestamp);

            updateProperties(rs, verdict, VERDICT_FIELDS);
            verdictRepository.save(verdict);
        }
    }

    private void insertCareers(Connection connection, Timestamp timestamp) throws SQLException, IllegalAccessException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from m3");
        while (rs.next()) {
            Career career = new Career();
            career.setImportDate(timestamp);

            updateProperties(rs, career, CAREER_FIELDS);
            careerRepository.save(career);
        }
    }

    private static void updateProperties(ResultSet rs, Invoer object, List<Field> fields)
            throws SQLException, IllegalAccessException {
        for (Field field : fields) {
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);

            if (field.getType() == String.class)
                field.set(object, rs.getString(column.name()));
            else if (field.getType() == int.class)
                field.setInt(object, rs.getInt(column.name()));
        }

        object.setWorkOrder(new WorkOrder(rs.getString("ONDRZKO"), rs.getString("OPDRNRI")));
    }
}
