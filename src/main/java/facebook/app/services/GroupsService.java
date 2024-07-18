package facebook.app.services;

import facebook.app.dao.GroupsDAO;
import facebook.app.entities.Groups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GroupsService {
    private final GroupsDAO groupsDAO = new GroupsDAO();
    public List<Groups> getAllGroups() {
        return groupsDAO.getAllGroups();
    }

    public int generateUniqueGroupId() {
        List<Groups> groupsList = groupsDAO.getAllGroups();
        if (groupsList.isEmpty()) {
            return 1; // Start from 1 if no groups are present
        } else {
            // Find the maximum group ID and increment it
            return groupsList.stream()
                    .mapToInt(Groups::getGroupId)
                    .max()
                    .getAsInt() + 1;
        }
    }

    public void createGroup(Groups groups) {
        groupsDAO.addGroup(groups);
    }

    public void addMemberToGroup(int groupId, int friendId) {
        Optional<Groups> groupOpt = groupsDAO.getGroupById(groupId);
        if (groupOpt.isPresent()) {
            Groups group = groupOpt.get();
            String updatedUserIds = group.getUserId() + "," + friendId;
            Groups updatedGroup = new Groups(group.getGroupId(), updatedUserIds, group.getGroupName(), group.getGroupDescription());
            groupsDAO.addGroup(updatedGroup); // This method needs to be able to handle updates
        } else {
            throw new IllegalArgumentException("Group not found with ID: " + groupId);
        }
    }

    public void removeMemberFromGroup(int groupId, int friendId) {
        Optional<Groups> groupOpt = groupsDAO.getGroupById(groupId);
        if (groupOpt.isPresent()) {
            Groups group = groupOpt.get();
            List<String> userIdsList = new ArrayList<>(Arrays.asList(group.getUserId().split(",")));

            // Remove the specified friend ID and rejoin the list
            userIdsList.remove(String.valueOf(friendId));
            String updatedUserIds = String.join(",", userIdsList);

            Groups updatedGroup = new Groups(group.getGroupId(), updatedUserIds, group.getGroupName(), group.getGroupDescription());
            groupsDAO.addGroup(updatedGroup);
        } else {
            throw new IllegalArgumentException("Group not found with ID: " + groupId);
        }
    }

    public Optional<Groups> getGroupById(int groupId) {
        return groupsDAO.getGroupById(groupId);
    }
}
