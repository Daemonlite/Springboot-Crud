package com.daemontech.starter;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareEngineerService {
    private final SoftwareEngineerReposititory softwareEngineerReposititory;

    public SoftwareEngineerService(SoftwareEngineerReposititory softwareEngineerReposititory) {
        this.softwareEngineerReposititory = softwareEngineerReposititory;
    }

    //implement a dto for the response generic and use
    // findAll().stream().map()
    public List<SoftwareEngineer> getAllSoftwareEngineers() {
        return softwareEngineerReposititory.findAll();
    }

    public void insertSoftwareEngineer(SoftwareEngineer softwareEngineer) {
        softwareEngineerReposititory.save(softwareEngineer);
    }


    public SoftwareEngineer getSoftwareEngineerById(Integer id) {
        return softwareEngineerReposititory.findById(id)
                .orElseThrow(() -> new IllegalStateException(id + " not found"));

    }

    public void updateSoftwareEngineer(Integer id, SoftwareEngineer softwareEngineer) {
        if (id == null || softwareEngineer == null) {
            throw new IllegalArgumentException("ID and software engineer object must not be null");
        }

        softwareEngineerReposititory.findById(id)
                .ifPresentOrElse(
                        existingEngineer -> {
                            // Update the existing entity with new values
                            existingEngineer.setName(softwareEngineer.getName());
                            existingEngineer.setTechStack(softwareEngineer.getTechStack());
                            // Update other fields as needed

                            softwareEngineerReposititory.save(existingEngineer);
                        },
                        () -> {
                            throw new IllegalStateException("Software engineer with ID " + id + " not found");
                        }
                );
    }

    public void deleteSoftwareEngineer(Integer id) {
        if(softwareEngineerReposititory.findById(id).isPresent()){
            softwareEngineerReposititory.deleteById(id);
        }else{
            throw new IllegalStateException(id + " not found");
        }
    }
}
