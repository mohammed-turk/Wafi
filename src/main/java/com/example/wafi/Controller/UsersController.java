package com.example.wafi.Controller;

import com.example.wafi.API.APIResponse;
import com.example.wafi.Model.IssueReports;
import com.example.wafi.Model.Users;
import com.example.wafi.Service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @GetMapping("/get")
    public ResponseEntity<?> get(){
        List<Users> users = usersService.get();
        if (users.isEmpty())
            return ResponseEntity.status(200).body("no user exist");

        return ResponseEntity.status(200).body(users);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid Users user, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());


        boolean emailExist =usersService.add(user);
        if (!emailExist)
            return ResponseEntity.status(400).body(new APIResponse("email already occupied0"));
        return ResponseEntity.status(200).body(new APIResponse("user added successfully"));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> update(@PathVariable Integer userId, @RequestBody @Valid Users user, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        boolean isFine = usersService.update(userId, user);

        if (isFine)
            return ResponseEntity.status(200).body(new APIResponse("user updated successfully"));

        return ResponseEntity.status(400).body(new APIResponse("no such a user exist"));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> delete(@PathVariable Integer userId){
        boolean isFine = usersService.delete(userId);

        if (isFine)
            return ResponseEntity.status(200).body(new APIResponse("user deleted successfully"));

        return ResponseEntity.status(400).body(new APIResponse("no such a user exist"));

    }

    @GetMapping("/subscriptions-count/{userId}")
    public ResponseEntity<?> getSubscriptionCount(@PathVariable Integer userId){
        return ResponseEntity.status(200).body(usersService.getSubscriptionCount(userId));
    }

    @GetMapping("/issue-reports/{userId}")
    public ResponseEntity<?> getIssueReports(@PathVariable Integer userId){
        List<IssueReports> reports = usersService.getUserIssueReports(userId);
        if (reports.isEmpty())
            return ResponseEntity.status(200).body(new APIResponse("no issue reports for this user"));
        return ResponseEntity.status(200).body(reports);
    }

    @GetMapping("/vip")
    public ResponseEntity<?> getVipUsers(){
        List<Users> users = usersService.getVipUsers();
        if (users.isEmpty())
            return ResponseEntity.status(200).body(new APIResponse("no VIP users"));
        return ResponseEntity.status(200).body(users);
    }

    @GetMapping("/regular")
    public ResponseEntity<?> getRegularUsers(){
        List<Users> users = usersService.getRegularUsers();
        if (users.isEmpty())
            return ResponseEntity.status(200).body(new APIResponse("no regular users"));
        return ResponseEntity.status(200).body(users);
    }

    @GetMapping("/at-risk")
    public ResponseEntity<?> getAtRiskUsers(){
        List<Users> users = usersService.getAtRiskUsers();
        if (users.isEmpty())
            return ResponseEntity.status(200).body(new APIResponse("no at-risk users"));
        return ResponseEntity.status(200).body(users);
    }

    @GetMapping("/inactive")
    public ResponseEntity<?> getInactiveUsers(){
        List<Users> users = usersService.getInactiveUsers();
        if (users.isEmpty())
            return ResponseEntity.status(200).body(new APIResponse("no inactive users"));
        return ResponseEntity.status(200).body(users);
    }
}
