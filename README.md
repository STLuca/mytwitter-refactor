# mytwitter-refactor

Example api usage

Create a user
curl -i -H "Content-Type:application/json"
	-d '{ "username" : "bob", 
        "password" : "password", 
        "profilePic" : "https://amp.businessinsider.com/images/5899ffcf6e09a897008b5c04-750-750.jpg" }'
	http://localhost:8080/api/users
  
login
curl -i -H "Content-Type:application/x-www-form-urlencoded"
  -c cookies
  -d username=bob -d password=mypassword
  localhost:8080/login

Create a tweet
curl -i -H "Content-Type:application/json" 
  -b cookies
	-d '{ 	
        "message" : "abcd", 
        "user" : "https://spring-boot-twitter.cfapps.io/api/users/2", 
        "repliedTo" : "https://spring-boot-twitter.cfapps.io/api/tweets/4", 
        "tags" : [ "abc", "def" ],
        "mentions" : [ "https://spring-boot-twitter.cfapps.io/api/users/3" ]
      }' 
http://localhost:8080/api/tweets 

Like a tweet
curl -i -H "Content-Type:application/json"
  -b cookies
	-d '{
        "user" : "https://spring-boot-twitter.cfapps.io/api/users/2",
        "tweet" : "https://spring-boot-twitter.cfapps.io/api/tweets/3"
      }'
http://localhost:8080/api/likes 

Follow a user
curl -i -H "Content-Type:application/json"
  -b cookies
	-d '{
        "followee" : "https://spring-boot-twitter.cfapps.io/api/users/2",
        "follower" : "https://spring-boot-twitter.cfapps.io/api/tweets/3"
      }'
http://localhost:8080/api/follows

Deleting a resource
curl -b cookies -X DELETE https://spring-boot-twitter.cfapps.io/api/tweets/3
