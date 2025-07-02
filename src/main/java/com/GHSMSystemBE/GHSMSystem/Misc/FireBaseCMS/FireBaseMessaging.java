//package com.GHSMSystemBE.GHSMSystem.Misc.FireBaseCMS;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import com.google.firebase.messaging.FirebaseMessaging;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//@Configuration
//public class FireBaseMessaging {
//
//    @Autowired
//    private FirebaseProperties firebaseProperties;
//
//
//    @Bean
//    FirebaseMessaging FireBaseMessaging(FirebaseApp firebaseApp)
//    {
//        return FirebaseMessaging.getInstance(firebaseApp);
//    }
//
//    @Bean
//    FirebaseApp fireBaseApp(GoogleCredentials credentials)
//    {
//        FirebaseOptions options = FirebaseOptions.builder().setCredentials(credentials).build();
//        return FirebaseApp.initializeApp(options);
//    }
//
//    @Bean
//    GoogleCredentials googleCredentials() throws IOException {
//        if (firebaseProperties.getServiceAccount() != null) {
//            try (InputStream is = firebaseProperties.getServiceAccount().getInputStream()) {
//                return GoogleCredentials.fromStream(is);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        else {
//            // Use standard credentials chain. Useful when running inside GKE
//            return GoogleCredentials.getApplicationDefault();
//        }
//    }
//
//}
