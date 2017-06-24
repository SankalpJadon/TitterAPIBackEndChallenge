package challenge.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import challenge.pojos.Message;
import challenge.pojos.People;
import challenge.pojos.User;
import challenge.service.AuthenticationDetailsService;
import challenge.service.DijkstraService;
import challenge.service.FollowerService;
import challenge.service.MessageService;
import challenge.service.PersonService;
import challenge.service.UserService;

@RestController
public class RequestController {
	
	@Autowired private FollowerService followerService;
	@Autowired private MessageService messageService;
	@Autowired private DijkstraService dijkstraService;
	@Autowired private UserService userService;
	@Autowired private PersonService personService;
	@Autowired private AuthenticationDetailsService authenticateDetailsService;
	
	
	// Basic home page redirect if root is visited.
    @RequestMapping(value="/api", method = RequestMethod.GET)
    public ResponseEntity ApiLandingRedirect(){
    	String name = authenticateDetailsService.getUserName(); // Get the logged in username from the authenticateDetailsService
    	if(name==null){
    		return new ResponseEntity("User should be authorized ",HttpStatus.BAD_REQUEST);
    	}
    	return new ResponseEntity("Challenge API landing page",HttpStatus.OK);
    }
    
    // controller method to return the list of messages by the user or by the followers of the users. It also 
    // return filtered messages if the search keyword is specified in the request.
    @RequestMapping(value="/api/messages", method= RequestMethod.GET)
    public ResponseEntity getMessages(@RequestParam(required = false) String searchKey){
    	String userName = authenticateDetailsService.getUserName();	// Get the logged in username from the authenticateDetailsService
    	User user= userService.getUser(userName);   	
    	List<Message> list;
    	if(searchKey!=null)
    		list= messageService.getMessagesByUserIdAndSearchKey(user.getPerson_id(),searchKey);
    	else 
            list = messageService.getMessagesByUser(user.getPerson_id()); 	
    	return new ResponseEntity(list,HttpStatus.OK);
    }

	// controller method to return list of all the people who are following the user.
	@RequestMapping(value="/api/following",method=RequestMethod.GET)
	public ResponseEntity findFollowing(){	
    	String userName = authenticateDetailsService.getUserName();  // Get the logged in username from the authenticateDetailsService
    	User user= userService.getUser(userName); //Get the user object using userName
    	if(user==null) // Check if user exists
			return new ResponseEntity("User doesn't exist", HttpStatus.BAD_REQUEST);   	
    	int personId= (int) user.getPerson_id(); 
		List<People> following = followerService.findFollowing(personId); //get the list of people user is following using person id		
		if(following.isEmpty()||following==null) 
			return new ResponseEntity("No followers found for "+user.getUsername(),HttpStatus.BAD_REQUEST);		
		return new ResponseEntity(following,HttpStatus.OK);
	}
	
	// Controller method to get the list of people who are being followed by the user.
	@RequestMapping(value="/api/followers",method=RequestMethod.GET)
	public ResponseEntity findFollowers(){    	
		String userName = authenticateDetailsService.getUserName();  // Get the logged in username from the authenticateDetailsService
    	User user= userService.getUser(userName);
    	int personId= (int) user.getPerson_id();//Get the person Id from user object
		if(personId==-1) return new ResponseEntity("User doesn't exist",HttpStatus.BAD_REQUEST);
		List<People> followers =  followerService.findFollowers(personId); //get the list of people following the user using person id
		if(followers.isEmpty()||followers==null) return new ResponseEntity("No followers found for "+user.getUsername(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity(followers,HttpStatus.OK);
	}
	
	// Controller method to start following a particular user
	@RequestMapping(value = "/api/follow", method = RequestMethod.GET)
    public ResponseEntity follow(@RequestParam(value="follow_person_userName") String follow_person_userName) {
		String userName = authenticateDetailsService.getUserName();  // Get the logged in username from the authenticateDetailsService
    	User currentUser= userService.getUser(userName);
		User userToFollow= (User) userService.getUser(follow_person_userName);
        if (currentUser==null||userToFollow==null)
            return new ResponseEntity("Users don't exist",HttpStatus.BAD_REQUEST);
        if (currentUser.getPerson_id() == userToFollow.getPerson_id())	//Check if the user id and the follower id are different or not
            return new ResponseEntity("Bad request- Users have same ID", HttpStatus.BAD_REQUEST);
        boolean startFollowing = followerService.startFollowing(currentUser.getPerson_id(), userToFollow.getPerson_id());
        if (startFollowing)
            return new ResponseEntity(String.format("Now %s follows %s", currentUser.getUsername(), userToFollow.getUsername()),HttpStatus.OK);
        else
            return new ResponseEntity(String.format("%s already follows %s", currentUser.getUsername(), userToFollow.getUsername()), HttpStatus.BAD_REQUEST);

    }
	
	// Controller method to start unfollowing a particular user
	@RequestMapping(value="/api/unfollow",method= RequestMethod.GET)
	public ResponseEntity unfollow(@RequestParam(value="unfollow_person_username") String unfollow_person_username){
		String userName = authenticateDetailsService.getUserName();  // Get the logged in username from the authenticateDetailsService
    	User currentUser= userService.getUser(userName);
        User userToUnfollow= (User) userService.getUser(unfollow_person_username);
        if (currentUser==null||userToUnfollow==null)
            return new ResponseEntity("Bad request- Invalid users", HttpStatus.BAD_REQUEST);
        if (currentUser.getPerson_id() == userToUnfollow.getPerson_id())  // Check if the person to unfollow is not the same as the user
            return new ResponseEntity("Bad request- Both users have same IDs", HttpStatus.BAD_REQUEST);
        boolean startUnfollowing = followerService.unFollow(currentUser.getPerson_id(), userToUnfollow.getPerson_id());
        if (startUnfollowing)
            return new ResponseEntity(String.format("Now %s is not following %s", currentUser.getUsername(), userToUnfollow.getUsername()),HttpStatus.OK);
        else
            return new ResponseEntity(String.format("%s already does not follow %s", currentUser.getUsername(), userToUnfollow.getUsername()), HttpStatus.OK);
	}
	
	// Controller method to handle the mismatched request.
    @RequestMapping(value = "/**")
    public ResponseEntity handleNotFoundException(Exception e) {
        return new ResponseEntity("The requested endpoint does not exists.", HttpStatus.BAD_REQUEST);
    }
    
    // Part 2 of the challenge, option 1- Find the minimum number of hops between two users
    @RequestMapping(value="/api/minimum-hops", method= RequestMethod.GET)
    public ResponseEntity getHops(@RequestParam(value="destinationUserName") String destinationUserName){
		String userName = authenticateDetailsService.getUserName();  // Get the logged in username from the authenticateDetailsService
    	User currentUser= userService.getUser(userName);	//Get the current user
    	User destinationUser= userService.getUser(destinationUserName);	//Get the destination user
    	if(currentUser==null||destinationUser==null) 
    		return new ResponseEntity("One or both entered users are invalid",HttpStatus.BAD_REQUEST);
    	dijkstraService.populateGraph();	// Populate the graph with all users
    	int hops= dijkstraService.findHops(currentUser.getUsername(),destinationUser.getUsername()); // Find the number of hops using findHops() function in DijkstraService
    	if(hops==-1) 
    		return new ResponseEntity("No path exists between two users", HttpStatus.OK);
    	return new ResponseEntity("Number of user hops between "+currentUser.getUsername()+" and "+destinationUser.getUsername()+" is "+hops, HttpStatus.OK);
    }
}
