package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class ReposListResponse {
    public String repo_name;
    public String owner;
    public List<BranchResponse> branches;
    // public String branch_name;
    // public String last_commit_sha;

    public ReposListResponse(String repo_name, String owner) {
        this.repo_name = repo_name;
        this.owner = owner;
        this.branches = new ArrayList<BranchResponse>();
        // this.branch_name = branch_name;
        // this.last_commit_sha = last_commit_sha;
    }
}
