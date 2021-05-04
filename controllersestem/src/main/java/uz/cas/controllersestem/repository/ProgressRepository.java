package uz.cas.controllersestem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.cas.controllersestem.entity.Progress;
import uz.cas.controllersestem.entity.Project;
import uz.cas.controllersestem.entity.Users;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProgressRepository extends JpaRepository<Progress, UUID> {

    List<Progress> findByStatusAndProject(boolean status, Optional<Project> project);

    List<Progress> findByStatusAndProjectAndUsers(boolean status, Project project, Users users);

    List<Progress> findByStatus(boolean status);

}
