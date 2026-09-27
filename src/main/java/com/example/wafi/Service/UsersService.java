package com.example.wafi.Service;

import com.example.wafi.Model.IssueReports;
import com.example.wafi.Model.Subscriptions;
import com.example.wafi.Model.Users;
import com.example.wafi.Repository.IssueReportsRepository;
import com.example.wafi.Repository.SubscriptionsRepository;
import com.example.wafi.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final SubscriptionsRepository subscriptionsRepository;
    private final IssueReportsRepository issueReportsRepository;

    public List<Users> get(){
        return usersRepository.findAll();
    }

    public Boolean add(Users user){
        if (usersRepository.existsByEmail(user.getEmail()))
            return false;

        usersRepository.save(user);
        return true;
    }

    public Boolean update(Integer userId, Users user){
        Users oldUser = usersRepository.findUsersById(userId);

        if (oldUser == null)
            return false;

        oldUser.setEmail(user.getEmail());
        oldUser.setName(user.getName());
        oldUser.setPhoneNumber(user.getPhoneNumber());
        usersRepository.save(oldUser);
        return true;
    }

    public Boolean delete(Integer userId){
        Users user = usersRepository.findUsersById(userId);
        if (user == null)
            return false;

        usersRepository.delete(user);
        return true;
    }

    public Integer getSubscriptionCount(Integer userId) {
        return subscriptionsRepository.findSubscriptionsByUserId(userId).size();
    }

    public List<IssueReports> getUserIssueReports(Integer userId) {
        return issueReportsRepository.findIssueReportsByUserId(userId);
    }

    public List<Users> getVipUsers() {
        return usersRepository.findVipUsers();
    }

    public List<Users> getRegularUsers() {
        return usersRepository.findRegularUsers();
    }

    public List<Users> getAtRiskUsers() {
        return usersRepository.findAtRiskUsers();
    }

    public List<Users> getInactiveUsers() {
        return usersRepository.findInactiveUsers();
    }
}