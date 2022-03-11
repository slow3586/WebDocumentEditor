package ru.demskv.webapplicationproject.assignment;

import jakarta.ejb.Local;
import jakarta.ejb.Remote;
import java.util.List;
import java.util.Optional;

@Local
@Remote
public interface AssignmentDAOLocal {
    public Long countAll();
    
    public List<Assignment> findAll(int from, int limit, String orderBy, boolean desc, Integer filterId);
    
    public Optional<Assignment> findById(int id);
    
    public void create(Assignment assignment);
    
    public void update(Assignment assignment);
    
    public void deleteById(int id);
}
