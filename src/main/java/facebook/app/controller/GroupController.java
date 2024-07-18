package facebook.app.controller;

import facebook.app.entities.Groups;
import facebook.app.exceptions.UserIOException;
import facebook.app.services.GroupsService;
import facebook.app.services.UserService;

public class GroupController {
    private final GroupsService groupsService = new GroupsService();
    private final UserService userService = new UserService();

    public GroupController() {

    }

    public void createGroup(String groupName, String groupDescription) throws UserIOException {
        int groupId = groupsService.generateUniqueGroupId();
        int userId = (int) userService.getCurrentUserId();

        Groups newGroup = new Groups(groupId, String.valueOf(userId), groupName, groupDescription);
        groupsService.createGroup(newGroup);
    }
}
