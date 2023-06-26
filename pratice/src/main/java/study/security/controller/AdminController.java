package study.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.security.dto.MemberResp;
import study.security.dto.ResourceResp;
import study.security.dto.RoleResp;
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
        List<MemberResp> members = memberService.getMembers();
        model.addAttribute("members", members);

        log.info("Members size={}", members.size());
        return "admin/user-table";
    }

    @GetMapping("/role")
    public String getRoleTable(Model model) {
        List<RoleResp> roles = memberRoleService.getRoles();
        model.addAttribute("roles", roles);

        log.info("Roles size={}", roles.size());
        return "/admin/role-table";
    }

    @GetMapping("/role/add")
    public String getRoleRegistrationForm() {
        return "admin/add-role";
    }

    @GetMapping("/role/hierarchy")
    public String getRoleHierarchyForm(Model model, @RequestParam String child) {
        List<RoleResp> roles = memberRoleService.getRoles();
        model.addAttribute("child", child);
        model.addAttribute("roles", roles);

        log.info("getRoleHierarchyForm");
        return "admin/add-hierarchy";
    }

    @GetMapping("/resource")
    public String getResourceTable(Model model) {
        List<ResourceResp> resources = authorizationResourceService.getResource();
        model.addAttribute("resources", resources);

        log.info("Resources size={}", resources.size());
        return "admin/resource-table";
    }

    @GetMapping("/resource/add")
    public String getResourceRegistrationForm(Model model) {
        List<RoleResp> roles = memberRoleService.getRoles();
        model.addAttribute("roles", roles);

        return "admin/add-resource";
    }
}
