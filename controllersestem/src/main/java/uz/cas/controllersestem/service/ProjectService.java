package uz.cas.controllersestem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.cas.controllersestem.entity.Project;
import uz.cas.controllersestem.entity.Users;
import uz.cas.controllersestem.entity.enums.ProjectStatus;
import uz.cas.controllersestem.payload.ReqComment;
import uz.cas.controllersestem.payload.ReqLogin;
import uz.cas.controllersestem.payload.ReqProject;
import uz.cas.controllersestem.payload.ReqUsername;
import uz.cas.controllersestem.repository.ProjectRepository;
import uz.cas.controllersestem.repository.UsersRepository;

import java.util.*;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UsersRepository usersRepository;


    public ResponseEntity<?> addProject(ReqProject reqProject){
        Project project = new Project();
        project.setProjectManager(usersRepository.findById(reqProject.getProjectManager()).get());
        project.setProjectName(reqProject.getProjectName());
        project.setProjectPercent(0);
        project.setUsersList(usersRepository.findAllById(reqProject.getUsersList()));
        project.setProjectFinished(reqProject.getProjectFinished());
        project.setProjectCreated(reqProject.getProjectCreated());
        project.setProjectStatus(ProjectStatus.active);
        projectRepository.save(project);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> editProject(Integer id, ReqProject reqProject){
        Optional<Project> byId = projectRepository.findById(id);
        if (byId.isPresent()){
            Project project = byId.get();
            project.setProjectCreated(reqProject.getProjectCreated());
            project.setProjectFinished(reqProject.getProjectFinished());
            project.setProjectName(reqProject.getProjectName());
            project.setProjectManager(usersRepository.findById(reqProject.getProjectManager()).get());
            project.setUsersList(usersRepository.findAllById(reqProject.getUsersList()));
            projectRepository.save(project);
            return ResponseEntity.status(200).body("Project o'zgartirildi");
        }
        return ResponseEntity.status(400).body("bunday id li project topilmadi");
    }

    public ResponseEntity<?> deleteProject(Integer id){
        projectRepository.deleteById(id);
        return ResponseEntity.status(200).body("malumot o'chirildi");
    }

    public ResponseEntity<?> getProject(Integer id){
        Optional<Project> byId = projectRepository.findById(id);
        if (byId.isPresent()){
            return ResponseEntity.ok(byId.get());
        }
        return ResponseEntity.ok("Bunday project topilmadi");
    }
    public ResponseEntity<?> complete(){
        return ResponseEntity.ok( projectRepository.findAll());
    }

    public ResponseEntity<?> getUsernameProject(ReqUsername username){
        Optional<Users> byUsername = usersRepository.findByUsername(username.getUsername());
        if (byUsername.isPresent()){
            Users users = byUsername.get();
            if (users.getUsername().equals("aziz")){
                List<Project> byProjectStatus = projectRepository.findByProjectStatusOrProjectStatus(ProjectStatus.active, ProjectStatus.inProgress);
                if (byProjectStatus.size() > 0){
                    return ResponseEntity.ok(byProjectStatus);
                }
                return ResponseEntity.ok("malumot topilmadi");
            }else {
                List<Project> byProjectStatus = projectRepository.findByProjectStatus(ProjectStatus.active);
                if (byProjectStatus.size() > 0){
                    List<Project> userProject = new ArrayList<>();
                    for (Project project : byProjectStatus) {
                        if (project.getProjectManager().getUsername() == username.getUsername()){
                            userProject.add(project);
                        }else {
                            for (Users user : project.getUsersList()) {
                                if (user.getUsername().equals(username.getUsername())){
                                    userProject.add(project);
                                }
                            }
                        }
                    }
                    return ResponseEntity.ok(userProject);
                }
            }
        }

        return ResponseEntity.ok("malumot topilmadi");
    }

    public ResponseEntity<?> addDocument(ReqComment reqComment, Integer id){
        Optional<Project> byId = projectRepository.findById(id);
        if (byId.isPresent()) {
            Project project = byId.get();
            project.setDocument(reqComment.getComment());
            projectRepository.save(project);
            return ResponseEntity.ok("Hujjatlar saqlandi");
        }
        return ResponseEntity.ok("Kechirasiz bunday project topilmadi");
    }
    public ResponseEntity<?> activeProject(Integer id){
        Optional<Project> byId = projectRepository.findById(id);
        if (byId.isPresent()){
            Project project = byId.get();
            project.setProjectMake(true);
            projectRepository.save(project);
        }
        return ResponseEntity.ok("Bunday project yo'q");
    }

    public ResponseEntity<?> activeProject(){
        List<Project> byProjectStatus = projectRepository.findByProjectStatus(ProjectStatus.active);
        return ResponseEntity.ok(byProjectStatus);
    }
    public ResponseEntity<?> inProgressProject(){
        List<Project> byProjectStatus = projectRepository.findByProjectStatus(ProjectStatus.inProgress);
        return ResponseEntity.ok(byProjectStatus);
    }
    public ResponseEntity<?> finishedProject(){
        List<Project> byProjectStatus = projectRepository.findByProjectStatus(ProjectStatus.finished);
        return ResponseEntity.ok(byProjectStatus);
    }
}

