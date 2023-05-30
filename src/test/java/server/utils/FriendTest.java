package server.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FriendTest {

    @Test
    void insertFriend() {
        assert Friend.insertFriend(1,1,"备注")==0;
        assert Friend.insertFriend(1,1,"备注")==-1;
    }

    @Test
    void deleteFriend() {
        assert Friend.deleteFriend(1,1)==0;
        assert Friend.deleteFriend(1,1)==-1;
    }

    @Test
    void findAllFriends() {
        Friend.insertFriend(1,1,"朋友1");
        Friend[] friends=Friend.findAllFriends(1);
        assert friends[0].friend_id==1;
        assert friends[1]==null;
    }

    @Test
    void changeNickname() {
        assert Friend.changeNickname(1,1,"新名称")==0;
    }
}