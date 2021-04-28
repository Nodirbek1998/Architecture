package uz.cas.controllersestem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.cas.controllersestem.payload.ReqComment;
import uz.cas.controllersestem.payload.ReqLogin;
import uz.cas.controllersestem.payload.ReqProject;
import uz.cas.controllersestem.payload.ReqUsername;
import uz.cas.controllersestem.service.ProjectService;

import javax.validation.Valid;

@Controller
@RequestMapping("/uz/cas/project")
public class ProjectController {


    @Autowired
    private ProjectService projectService;

    @PostMapping
    public HttpEntity<?> addProject(@Valid  @RequestBody ReqProject reqProject){
        projectService.addProject(reqProject);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editProject(@PathVariable Integer id, @RequestBody ReqProject reqProject){
        return ResponseEntity.ok(projectService.editProject(id, reqProject));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProject(@PathVariable Integer id){
        return ResponseEntity.ok(projectService.deleteProject(id));
    }
    @GetMapping("/{id}")
    public HttpEntity<?> getProject(@PathVariable Integer id){
        return ResponseEntity.ok(projectService.getProject(id));
    }
    @GetMapping("/all")
    public HttpEntity<?> ProjectComplete(){
        return ResponseEntity.ok(projectService.complete());
    }

    @PostMapping("/user")
    public HttpEntity<?> getProjectUser(@RequestBody ReqUsername username){
        return ResponseEntity.ok(projectService.getUsernameProject(username));
    }
    @PostMapping("/{id}")
    public HttpEntity<?> addDocument(@PathVariable Integer id, @Valid  @RequestBody ReqComment reqComment){
        ResponseEntity<?> responseEntity = projectService.addDocument(reqComment, id);
        return ResponseEntity.ok(responseEntity);
    }
    @GetMapping("/active/{id}")
    public HttpEntity<?> projectActive(@PathVariable Integer id){
        ResponseEntity<?> responseEntity = projectService.activeProject(id);
        return ResponseEntity.ok(responseEntity);
    }
}
