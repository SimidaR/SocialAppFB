package facebook.app.services;

import facebook.app.controller.ProfileController;
import facebook.app.dao.ProfileDAO;
import facebook.app.entities.Profile;
import facebook.app.entities.User;
import facebook.app.exceptions.InvalidEmailFormatException;
import facebook.app.exceptions.UserIOException;

import java.util.List;

public class ProfileService {
    private final ProfileDAO profileDAO = new ProfileDAO();
    private final ProfileController profileController = new ProfileController();

    public List<Profile> getAllProfile() {
        return profileDAO.readProfile();
    }

    public int generateUniqueProfileId() {
        return getAllProfile()
                .stream()
                .mapToInt(Profile::getId) //.mapToInt(p -> p.getId())
                .max()
                .orElse(0) + 1;
    }

    public Profile getCurrentProfileId(int userId) {
        List<Profile> profileList = getAllProfile();
        return profileList.stream()
                .filter(profile -> profile.getId() == userId)
                .findFirst()
                .orElse(null);
    }

    public Profile getLastProfileId() {
        List<Profile> profileList = getAllProfile();
        return  profileList.get(profileList.size() - 1);
    }

    public Profile displayCurrentProfile(int userId, Profile profile) {
        for (Profile p : profileDAO.readProfile()) {
            if (p.getId() == userId) {
                profile = p;
            }
        }
        return profile;
    }

    public void createProfile(int id, String name, String email, int age, String location) throws InvalidEmailFormatException {
        profileController.createProfile(id, name, email, age, location);
    }

    public void editProfile(int id, String name, String email, int age, String location) {
        profileController.editProfile(id, name, email, age, location);
    }

}
