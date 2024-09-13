package CourrierApplication.CourrierServices;


import CourrierApplication.CourrierControllerApi.SigninResponsebody;
import CourrierApplication.CourrierModel.Courrier;
import CourrierApplication.CourrierModel.CourrierProfile;
import CourrierApplication.JwtSecurityConfig.JwtService;
import feignorderconnection.Feignorderconnection;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CourrierServices {


    private CourrierRepo courrierRepo;

    private CourrierProfileRepo courrierProfileRepo;

    private JwtService jwtService;

    private Feignorderconnection orderconnection;

    // todo  save new courrier using email

    // todo if the email already exist return conflict


    public ResponseEntity<?> SaveNewCourrier(Courrier courrier){

        try{

            Optional<Courrier> existingCourrier = courrierRepo.findByEmailaddress(courrier.getEmailaddress());

            if (existingCourrier.isPresent()){

                return ResponseEntity.status(HttpStatus.CONFLICT).body("EMAIL ALREADY IN USE");
            }

            else {

               Courrier savedcourrier =  courrierRepo.saveAndFlush(courrier);


               CourrierProfile courrierProfile = new CourrierProfile();

                courrierProfile.setCourrierProfileid(savedcourrier.getCourrierid());
                courrierProfile.setCourrier(savedcourrier);

                courrierProfileRepo.save(courrierProfile);
                // at the same time create the wallet associated with the courrier using Openfeign

                return ResponseEntity.status(HttpStatus.CREATED).body("Account Created");



            }
        }

        catch (Exception e){

            System.out.println(e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CANT PROCESS THE REQUEST NOW ");
        }


    }



    public ResponseEntity<?> UpdateProfile(Long profileid, CourrierProfile updatedCourrierProfile){


        try {
            Optional<Courrier> exitingcourrier = courrierRepo.findById(profileid);

            if (exitingcourrier.isPresent()){

                CourrierProfile courrierProfile = exitingcourrier.get().getCourrierProfile();

                // make the updates,

                if (updatedCourrierProfile.getProfileImage() != null){

                    courrierProfile.setProfileImage(updatedCourrierProfile.getProfileImage());
                }

                if (updatedCourrierProfile.getPhonenumber() != null){

                    courrierProfile.setPhonenumber(updatedCourrierProfile.getPhonenumber());
                }

                if (updatedCourrierProfile.getBankAccountNumber() != null){

                    courrierProfile.setBankAccountNumber(updatedCourrierProfile.getBankAccountNumber());
                }

                if (updatedCourrierProfile.getFullName() != null){

                    courrierProfile.setFullName(updatedCourrierProfile.getFullName());
                }

                courrierProfile.setLastModification(LocalDateTime.now());

                courrierProfileRepo.save(courrierProfile);

                return ResponseEntity.status(HttpStatus.ACCEPTED).body("PROFILE UPDATED");

            }
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("USER NOT FOUND");


        }


        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CANT PROCESS THE REQUEST ...");
        }
    }

    public ResponseEntity<?> displayProfile(Long courierid){

        Optional<Courrier> existingcourrier = courrierRepo.findById(courierid);
        if(existingcourrier.isPresent()){

            Courrier courierprofile = existingcourrier.get();

            return  ResponseEntity.status(HttpStatus.ACCEPTED).body(courierprofile);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");


    }


    public ResponseEntity<?> Signin(String emailaddress, String psw){

        try{

            Optional<Courrier> existingcourrier = courrierRepo.findByEmailaddress(emailaddress);

            if (existingcourrier.isPresent()){

                Courrier validcourrier = existingcourrier.get();

                if (validcourrier.getPassword().equals(psw)){

                    SigninResponsebody signinResponsebody  = new SigninResponsebody();
                    signinResponsebody.setToken(jwtService.generateToken(validcourrier.getEmailaddress(), validcourrier.getCourrierid()));
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(signinResponsebody);
                }

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("WRONG PASSWORD");

            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("USER NOT FOUND");
        }

        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


        }


        // orders not yet assigned to any courrier
   public ResponseEntity<?> fetchAllReadyOrders(){

        return  orderconnection.courrierfetchAllUnAssignedOrders();
   }

   public ResponseEntity<?> updateOrderStatus(Long orderid, Long courrierid, String status ){

        return orderconnection.upadateOrdestatus(orderid,courrierid, status);
   }

   public ResponseEntity<?> AllAcceptedOrders(Long courrierid){
        return orderconnection.courrierfetchAllAcceptedorders(courrierid);
   }

   public ResponseEntity<?> AllPickedupOrders(Long courrierid){

        return orderconnection.courrierfetchAllpickeduporders(courrierid);
   }


   public ResponseEntity<?> allfinishedOrders(Long courrierid){

        return orderconnection.courrierallfinishedOrders(courrierid);


   }


   public ResponseEntity<?> allOrders(Long courrierid){

        return orderconnection.courrierallfinishedOrders(courrierid);
   }


   public ResponseEntity<?> displayAllorderAssignedtoacourier(Long courierid){

       return orderconnection.courrierfetchallorders(courierid);
   }
}
