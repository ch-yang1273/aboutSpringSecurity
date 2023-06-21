package study.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.security.dto.MemberResponse;
import study.security.dto.ResourceResponse;
import study.security.dto.RoleResponse;
import study.security.service.AuthorizationResourceService;
import study.security.service.MemberRoleService;
import study.security.service.MemberService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {

    private final MemberService memberService;
    private final MemberRoleService memberRoleService;
    private final AuthorizationResourceService authorizationResourceService;

    @GetMapping
    public String adminHome() {
        return "admin/admin-home";
    }

    @GetMapping("/users")
    public String getUserTable(Model model) {
        List<MemberResponse> members = memberService.getMembers();
        model.addAttribute("members", members);

        log.info("Members size={}", members.size());
        return "admin/user-table";
    }

    @GetMapping("/role")
    public String getRoleTable(Model model) {
        List<RoleResponse> roles = memberRoleService.getRoles();
        model.addAttribute("roles", roles);

        log.info("Roles size={}", roles.size());
        return "/admin/role-table";
    }

    @GetMapping("/role/add")
    public String getRoleRegistrationForm() {
        return "admin/add-role";
    }

    @GetMapping("/resource")
    public String getResourceTable(Model model) {
        List<ResourceResponse> resources = authorizationResourceService.getResource();
        model.addAttribute("resources", resources);

        log.info("Resources size={}", resources.size());
        return "admin/resource-table";
    }

    @GetMapping("/resource/add")
    public String getResourceRegistrationForm(Model model) {
        List<RoleResponse> roles = memberRoleService.getRoles();
        model.addAttribute("roles", roles);

        return "admin/add-resource";
    }
}
