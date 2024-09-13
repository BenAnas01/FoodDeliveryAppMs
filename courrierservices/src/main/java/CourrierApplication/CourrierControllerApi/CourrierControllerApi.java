package CourrierApplication.CourrierControllerApi;


import CourrierApplication.CourrierModel.Courrier;
import CourrierApplication.CourrierModel.CourrierProfile;
import CourrierApplication.CourrierServices.CourrierServices;
import CourrierApplication.S3Services.S3Services;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/courrier")
@AllArgsConstructor
public class CourrierControllerApi {


    private CourrierServices courrierServices;
    private S3Services s3Services;

    @PostMapping("/public/signup")

    public ResponseEntity<?> Register(@RequestBody CourrierSignupRequest courrierSignupRequest){


         Courrier courrier  = new Courrier();

         courrier.setEmailaddress(courrierSignupRequest.getEmailaddress());
         courrier.setPassword(courrierSignupRequest.getPassword());
         courrier.setCreatedAt(LocalDateTime.now());
         courrierServices.SaveNewCourrier(courrier);
        return  courrierServices.Signin(courrier.getEmailaddress(),courrier.getPassword());

    }

    // sign in public

    @PostMapping("/public/signin")
    public ResponseEntity<?> singin(@RequestBody SigninBodyRequest signinBodyRequest){

        // check and return JWT

        return  courrierServices.Signin(signinBodyRequest.getEmailaddress(),signinBodyRequest.getPsw());

    }

    @GetMapping("/private/profile")
    public ResponseEntity<?> courierprofile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        Courrier courrier = (Courrier) principal;

        return courrierServices.displayProfile(courrier.getCourrierid());
    }

    // todo Update the profile  set the name , phone number ,bank account , profile image

    @PutMapping("/private/profile/update")
    public ResponseEntity<?> UpdateProfile(@ModelAttribute SetProfileRequesBody setProfileRequesBody) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        Courrier courrier = (Courrier) principal;

        CourrierProfile courrierProfile = new CourrierProfile();

        if (setProfileRequesBody.getProfileImage() != null){

            String filenameKey = setProfileRequesBody.getProfileImage().getOriginalFilename() + "_" + System.currentTimeMillis();
            File fileconevrted = convertMultipartFileToFile(setProfileRequesBody.getProfileImage());
            String imagepath = s3Services.uploadFile("bucketscustomerdeliveryapp", filenameKey, fileconevrted).toString();
            courrierProfile.setProfileImage(imagepath);


        }

        if (setProfileRequesBody.getFullName() != null){

            courrierProfile.setFullName(setProfileRequesBody.getFullName());
        }

        if (setProfileRequesBody.getPhonenumber() != null){

            courrierProfile.setPhonenumber(setProfileRequesBody.getPhonenumber());
        }

        if(setProfileRequesBody.getBankAccountNumber() != null){

            courrierProfile.setBankAccountNumber(setProfileRequesBody.getBankAccountNumber());
        }

        courrierProfile.setLastModification(LocalDateTime.now());

        return courrierServices.UpdateProfile(courrier.getCourrierid(),courrierProfile);
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }


    // using feign create http request to order entity


    //get ready order for pick and

    @GetMapping("/private/order/ready")
    public ResponseEntity<?> fetchallReadyOrders(){

        try {

            return courrierServices.fetchAllReadyOrders();


        }
        catch (FeignException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO ORDER READY AVAILABLE");
        }
    }


    @PutMapping("/private/order/update")
    public ResponseEntity<?> updateOrder(@RequestParam Long orderid, @RequestParam String status){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        Courrier courrier = (Courrier) principal;

        return courrierServices.updateOrderStatus(orderid, courrier.getCourrierid(), status);
    }

    @GetMapping("/private/order/accepted")
    public ResponseEntity<?> AllacceptedOrders(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        Courrier courrier = (Courrier) principal;

        return courrierServices.AllAcceptedOrders(courrier.getCourrierid());
    }


    @GetMapping("/private/order/pickedup")
    public ResponseEntity<?> allpickedup(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Courrier courrier = (Courrier) principal;

        return courrierServices.AllPickedupOrders(courrier.getCourrierid());
    }

    @GetMapping("/private/order/finished")
    public ResponseEntity<?> allfinishedorders(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        Courrier courrier = (Courrier) principal;

        return courrierServices.allfinishedOrders(courrier.getCourrierid());
    }

    @GetMapping("/private/order/all")
    public ResponseEntity<?> fetchallorders(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        Courrier courrier = (Courrier) principal;
        System.out.println(courrier.getCourrierid());

        return courrierServices.displayAllorderAssignedtoacourier(courrier.getCourrierid());


    }



    // request withdraw



}
