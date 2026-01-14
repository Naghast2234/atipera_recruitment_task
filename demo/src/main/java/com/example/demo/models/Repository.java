package com.example.demo;

import java.util.List;

public class Repository {
    public String name;
    public boolean fork;
    public Owner owner;
    public String branches_url;

    public List<Branch> branches;
}
