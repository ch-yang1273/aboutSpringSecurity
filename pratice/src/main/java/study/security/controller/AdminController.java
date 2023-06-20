package study.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.security.dto.MemberResponse;
import study.security.dto.ResourceResponse;
import study.security.dto.RoleResponse;
import study.security.service.AdminService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public String adminHome() {
        return "admin/admin-home";
    }

    @GetMapping("/users")
    public String getUserManagementTable(Model model) {
        List<MemberResponse> members = adminService.getMembers();
        model.addAttribute("members", members);

        log.info("Members size={}", members.size());
        return "admin/user-management";
    }

    @GetMapping("/authorization")
    public String getAuthorizationManagementTable(Model model) {
        List<RoleResponse> roles = adminService.getRoles();
        model.addAttribute("roles", roles);

        log.info("Roles size={}", roles.size());
        return "admin/authorization-management";
    }

    @GetMapping("/resource")
    public String getResource(Model model) {
        List<ResourceResponse> resources = adminService.getResource();
        model.addAttribute("resources", resources);

        log.info("Resources size={}", resources.size());
        return "admin/resource-management";
    }
}
