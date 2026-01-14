# How to start it up

Honestly, just run
```
./gradlew build
```

Followed by 
```
./gradlew run
```

And it should be good to go. Running in your console.


# How to use
Send GET requests to somewhere here:
```
127.0.0.1:8080/ghuser/{Username}
```

Obviously, 127.0.0.1:8080 is localhost. Replace it with the server address if you're hosting it somewhere else.

# Expected responses

If you input a non-existing user, you'll get a 404 response, with structure of something like this:

```
{
    "status": 404,
    "message": "User not found"
}
```

Similarly, if you input an empty username, you'll get something like this:

```
{
    "status": 404,
    "message": "Empty username provided"
}
```

If you input a username that does exist but has no repositories (that are NOT forks) the response will be like this:

```
[]
```

And if you input a username that does have public repositories that are NOT forks on it, the response should be something like this:

```
[
    {
        "repo_name": "Name of the repository",
        "owner": "Login of the repository's owner",
        [
            {
                "branch_name": "Name of a branch",
                "commit_sha": "Last commit SHA"
            },

            {
                "branch_name": "Name of a branch",
                "commit_sha": "Last commit SHA"
            }
        ]
    },

    {
        "repo_name": "Name of the repository",
        "owner": "Login of the repository's owner",
        [
            {
                "branch_name": "Name of a branch",
                "commit_sha": "Last commit SHA"
            },
            
            {
                "branch_name": "Name of a branch",
                "commit_sha": "Last commit SHA"
            }
        ]
    }
]
```

Something like this.