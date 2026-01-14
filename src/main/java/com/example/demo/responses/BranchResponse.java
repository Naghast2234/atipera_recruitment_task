package com.example.demo;

import java.util.List;

public class BranchResponse {
    public String branch_name;
    public String last_commit_sha;

    public BranchResponse(String branch_name, String last_commit_sha) {
        this.branch_name = branch_name;
        this.last_commit_sha = last_commit_sha;
    }
}
