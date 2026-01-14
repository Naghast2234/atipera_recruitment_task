package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.ArrayList;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private Controller testController = new Controller();

	@Test
	void contextLoads() {
	}

	@Test
	public void getNonexistantUserRepos() {
		ResponseEntity resp = testController.getGHUser("AKFNSDNGDF"); // That account does not exist. HOPEFULLY.

		assertEquals(resp.getStatusCode().value(), 404);
	}

	@Test
	public void getEmptyUserStringRepos() {
		ResponseEntity resp = testController.getGHUser(""); // This does not exist either!

		assertEquals(resp.getStatusCode().value(), 404);
	}

	@Test
	public void getExistingUserRepos() {
		ResponseEntity resp = testController.getGHUser("Naghast2234"); // That's my github account. I have a few public repositories in there, non-forks, so should be good.

		assertEquals(resp.getStatusCode().value(), 200);
	}

	@Test
	public void getUserWithNoNonforkRepos() {
		ResponseEntity resp = testController.getGHUser("1"); // Found by sheer luck honestly, this account only has 1 repo, and it's a fork, so it shouldn't show up.

		assertEquals(resp.getStatusCode().value(), 200);
		assertEquals(resp.getBody(), new ArrayList<ReposListResponse>()); // This, SHOULD be an empty list here.
	}

}
