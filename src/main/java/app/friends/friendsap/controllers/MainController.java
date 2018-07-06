package app.friends.friendsap.controllers;

import app.friends.friendsap.CloudinaryConfig;
import app.friends.friendsap.models.*;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class MainController {

@Autowired
private  FriendsRepo friends;

@Autowired
private AppUserRepository users;

@Autowired
private RoleRepository roles;

@Autowired
private CloudinaryConfig cloudc;


    @RequestMapping ("/")
        public String showRegistration (Model model) {
        model.addAttribute("user", new AppUser());

        return "register";
    }

    @PostMapping ("/")
    public String saveUser (@ModelAttribute("user") AppUser user){
    user.addRole(roles.findByRole("USER"));
    users.save(user);

    return "redirect:/addfriend";
    }

    @RequestMapping ("/addfriend")
    public String showAddFriend (Model model) {
        model.addAttribute("friend", new Friend());
        return "addfriend";
    }

    @PostMapping ("/savefriend")
    public String saveFriend (@Valid@ModelAttribute("friend") Friend friend,
                              MultipartHttpServletRequest request,
                              BindingResult result, Principal principal) throws IOException{
        MultipartFile f = request.getFile("file");

        if(f.isEmpty()) {
            return "addfriend";
        }

        Map uploadResult = cloudc.upload(f.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
        String uploadURL = (String) uploadResult.get ("url");
        String uploadedName = (String) uploadResult.get("public_id");
        String transformedImage = cloudc.createUrl(uploadedName);
        friend.setImg(transformedImage);

        if(result.hasErrors()){
            return "addfriend";
        }

        friend.setCreatedBy(principal.getName());
        friends.save(friend);
        return "redirect:/listfriends";
    }

    @RequestMapping ("/listfriends")
    public String showFriendsList (@ModelAttribute("user") AppUser user, Model model, Principal principal) {
        model.addAttribute("friends",friends.findAllByCreatedByOrderByRank(principal.getName()));
        return "listfriends";
    }
}
