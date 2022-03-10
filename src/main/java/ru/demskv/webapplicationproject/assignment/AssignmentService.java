
package ru.demskv.webapplicationproject.assignment;

import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class AssignmentService {

        @EJB
        AssignmentDAO assignmentDAO;
        
        public List<Assignment> findAll() {
            return assignmentDAO.findAll(0, 100, "id", true);
        }
         
         public Long countAll() {
            return assignmentDAO.countAll();
        }
        
        public List<Assignment> findAll(int from, int limit) {
            if(limit<=0) limit=1;
            return assignmentDAO.findAll(from, limit, "id", true);
        }
        
        public List<Assignment> findAll(int from, int limit, String columnName, boolean desc) {
            if(limit<=0) limit=1;
            return assignmentDAO.findAll(from, limit, columnName, desc);
        }
        
        public Optional<Assignment> findById(int id) {
            return assignmentDAO.findById(id);
        }
        
        public Optional<Assignment> create(Assignment assignment){
            return assignmentDAO.create(assignment);
        }
        
        public Assignment update(Assignment assignment){
            return assignmentDAO.update(assignment);
        }
        
        public int deleteById(int id){
            return assignmentDAO.deleteById(id);
        }
}
