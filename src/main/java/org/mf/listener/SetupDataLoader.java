package org.mf.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

//    @Autowired
//    private UserService userService;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        
        Long userCount = 0l;	//userService.count();
        
        if (alreadySetup = userCount > 0) {
            return;
        }

		loadData();
		
        alreadySetup = true;
    }
	
	private void loadData() {
		
	}

//    @Transactional
//    private final UserProfileEntity createRoleIfNotFound(final UserProfileType type) {
//    	UserProfileEntity userProfile = userProfileService.findByType(type.getUserProfileType());
//        if (userProfile == null) {
//        	userProfile = new UserProfileEntity(type);
//        	userProfileService.save(userProfile);
//        	System.out.println("creato " + userProfile.toString());
//        }
//        return userProfile;
//    }

}
