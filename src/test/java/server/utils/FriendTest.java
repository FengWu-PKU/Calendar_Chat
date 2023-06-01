package server.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FriendTest {

    @Test
    void insertFriend() {
    }

    @Test
    void deleteFriend() {
        assert Friend.deleteFriend(1,1)==0;
        assert Friend.deleteFriend(1,1)==-1;
    }

    @Test
    void findAllFriends() {
    }

    @Test
    void changeNickname() {
        assert Friend.changeNickname(1,1,"新名称")==0;
    }
}