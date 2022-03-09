
package ru.demskv.webapplicationproject.assignment;

import java.time.LocalDateTime;
import java.util.Date;
import ru.demskv.webapplicationproject.employee.Employee;


public class AssignmentSlim {
private static final long serialVersionUID = 1L;
    private Integer id;
    private String topic;
    private Date executeby;
    private Integer controlattr;
    private Integer executeattr;
    private Employee author;
    private LocalDateTime lastupdated;
}
