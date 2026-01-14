package com.example.demo;

import java.util.List;

public class ReposListResponse {
    public String repo_name;
    public String owner;
    public String branch_name;
    public String last_commit_sha;

    public ReposListResponse(String repo_name, String owner, String branch_name, String last_commit_sha) {
        this.repo_name = repo_name;
        this.owner = owner;
        this.branch_name = branch_name;
        this.last_commit_sha = last_commit_sha;
    }
}
