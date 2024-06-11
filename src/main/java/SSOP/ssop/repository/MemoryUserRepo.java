package SSOP.ssop.repository;

import SSOP.ssop.domain.User;

import java.util.*;

public class MemoryUserRepo implements UserRepo {

    private static Map<Long, User> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public User save(User user) {
        // user_id 세팅
        user.setUser_id(++sequence);
        // store에 저장
        store.put(user.getUser_id(), user);
        return user;
    }

    @Override
    public Optional<User> findByName(String user_name) {
        return store.values().stream()
                .filter(user -> user.getUser_name().equals(user_name))
                .findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }
}
